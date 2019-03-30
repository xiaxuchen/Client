package com.cxyz.commons.widget.imageview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bm.library.PhotoView;
import com.cxyz.commons.R;
import com.cxyz.commons.utils.BitmapUtil;
import com.cxyz.commons.utils.ImageLoaderManager;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.widget.loading.LoadingCreator;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Administrator on 2019/2/17.
 */

public class BigImageDialog extends Dialog {

    public BigImageDialog(@NonNull Context context) {
        this(context,R.style.dialog_big_image);
    }

    public BigImageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(true);
    }

    public static class Builder{
        private Context context;

        private BigImageDialog dialog;

        private PhotoView iv;

        private Drawable resource;

        private String resourceUrl;

        public Builder(Context context)
        {
            this.context = context;
        }

        public Builder setResourceUrl(String url)
        {
            this.resourceUrl = url;
            return this;
        }

        public Builder setResource(Bitmap resource) {
            setResource(BitmapUtil.bitmapToDrawable(context,resource));
            return this;
        }

        public Builder setResource(Drawable drawable)
        {
            this.resource = drawable;
            return this;
        }

        public Builder setResource(int resource)
        {
            setResource(BitmapUtil.getBitmapFromResource(context,resource,null,null));
            return this;
        }

        public BigImageDialog build()
        {
            dialog = new BigImageDialog(context);
            View v = View.inflate(context, R.layout.dialog_big_image_layout, null);
            dialog.setContentView(v);
            iv = v.findViewById(R.id.iv);
            iv.enable();
            if(resource != null)
                iv.setImageDrawable(resource);
            iv.setOnClickListener(view -> dialog.cancel());

            if(resourceUrl != null)
                ImageLoaderManager.getInstance(context).displayImage(iv, resourceUrl, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        iv.setVisibility(View.GONE);
                        LoadingCreator.showLoading(context);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        iv.setVisibility(View.VISIBLE);
                        LoadingCreator.stopLoading();
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        iv.setImageDrawable(BitmapUtil.bitmapToDrawable(context,loadedImage));
                        iv.setVisibility(View.VISIBLE);
                        LoadingCreator.stopLoading();
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        iv.setVisibility(View.VISIBLE);
                        LoadingCreator.stopLoading();
                    }
                });
            return dialog;
        }
    }

}