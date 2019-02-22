package com.cxyz.vac.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.cxyz.commons.utils.BitmapUtil;
import com.cxyz.commons.utils.ImageLoaderManager;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.imageview.CancelableImageView;
import com.cxyz.commons.widget.imageview.MatrixImageView;
import com.cxyz.commons.widget.imageview.dialog.BigImageDialog;
import com.cxyz.logiccommons.constant.Constant;
import com.cxyz.vac.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.vincent.filepicker.activity.ImagePickActivity;

/**
 * Created by Administrator on 2019/2/14.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);

        CancelableImageView iv = findViewById(R.id.iv);
        iv.setOnCancelClickListener(iv1 -> {
            ToastUtil.showShort("caon");
        });
//        ImageLoaderManager.getInstance(this).displayImage(iv, Constant.ROOT_URL+"/resource/photos?urlPath=4/7/ic_launcher.png");
        iv.setOnClickListener(view -> {
            BigImageDialog.Builder builder = new BigImageDialog.Builder(this);
            builder.setResource(R.mipmap.ic_launcher);
            BigImageDialog dialog = builder.build();
            dialog.show();
        });
    }
}
