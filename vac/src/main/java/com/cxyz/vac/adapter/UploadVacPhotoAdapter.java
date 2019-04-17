package com.cxyz.vac.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.cxyz.commons.Adapter.AdapterBase;
import com.cxyz.commons.Adapter.ViewHolder;
import com.cxyz.commons.utils.BitmapUtil;
import com.cxyz.commons.utils.ImageLoaderManager;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.widget.imageview.CancelableImageView;
import com.cxyz.commons.widget.imageview.dialog.BigImageDialog;
import com.cxyz.commons.widget.imageview.listener.OnCancelClickListener;
import com.cxyz.vac.R;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;

import java.util.List;

/**
 * Created by Administrator on 2019/2/20.
 */

public class UploadVacPhotoAdapter extends AdapterBase<String>{

    private static final String FILE_PROTOCOL = "file://";

    public UploadVacPhotoAdapter(Context mContext, List<String> list) {
        super(mContext, list,R.layout.item_vac_photos_layout,R.layout.item_vac_photo_add_layout);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getCount()-1)
            return 1;
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return super.getCount()+1;
    }

    @Override
    public String getItem(int position) {
        if(position == getCount()-1)
            return null;
        return super.getItem(position);
    }

    @Override
    public void convertView(ViewHolder holder, String item, int position) {
        super.convertView(holder, item, position);
        if(position == getCount()-1)
        {
            holder.getConvertView().setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), ImagePickActivity.class);
                intent.putExtra(ImagePickActivity.IS_NEED_CAMERA,true);
                intent.putExtra(ImagePickActivity.IS_NEED_IMAGE_PAGER,true);
                intent.putExtra(ImagePickActivity.IS_TAKEN_AUTO_SELECTED,true);
                ((Activity)getContext()).startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
            });
            return;
        }
        CancelableImageView civ_photo = holder.getView(R.id.civ_photo);
        String path = item.startsWith(FILE_PROTOCOL)?item:FILE_PROTOCOL.concat(item);
        ImageLoaderManager.getInstance(getContext()).displayImage(civ_photo,path);
        civ_photo.setOnClickListener(view -> {
            BigImageDialog.Builder builder = new BigImageDialog.Builder(getContext());
            builder.setResourceUrl(path).build().show();
        });
        civ_photo.setOnCancelClickListener(iv -> {
            deleteItem(item);
            notifyDataSetChanged();
        });
    }

}
