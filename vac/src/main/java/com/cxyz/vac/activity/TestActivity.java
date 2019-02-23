package com.cxyz.vac.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.cxyz.commons.utils.BitmapUtil;
import com.cxyz.commons.utils.ImageLoaderManager;
import com.cxyz.commons.utils.LogUtil;
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
import com.vincent.filepicker.filter.entity.ImageFile;

import java.util.List;

/**
 * Created by Administrator on 2019/2/14.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);
        Intent intent = new Intent(this, ImagePickActivity.class);
        intent.putExtra(ImagePickActivity.IS_NEED_CAMERA,true);
        intent.putExtra(ImagePickActivity.IS_NEED_IMAGE_PAGER,true);
        intent.putExtra(ImagePickActivity.IS_TAKEN_AUTO_SELECTED,true);
        this.startActivityForResult(intent, 100);
//        CancelableImageView iv = findViewById(R.id.iv);
//        iv.setOnCancelClickListener(iv1 -> {
//            ToastUtil.showShort("caon");
//        });
////        ImageLoaderManager.getInstance(this).displayImage(iv, Constant.ROOT_URL+"/resource/photos?urlPath=4/7/ic_launcher.png");
//        iv.setOnClickListener(view -> {
//            BigImageDialog.Builder builder = new BigImageDialog.Builder(this);
//            builder.setResource(R.mipmap.ic_launcher);
//            BigImageDialog dialog = builder.build();
//            dialog.show();
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(requestCode);
        if(resultCode == RESULT_OK)
        {
            List<ImageFile> selected = data.getParcelableArrayListExtra(com.vincent.filepicker.Constant.RESULT_PICK_IMAGE);
            LogUtil.e(selected);
            for (ImageFile file:selected)
            {
                String path = file.getPath();
                LogUtil.e(path);
            }
        }
    }
}
