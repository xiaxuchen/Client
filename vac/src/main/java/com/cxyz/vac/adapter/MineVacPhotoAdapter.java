package com.cxyz.vac.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.telecom.Call;

import com.cxyz.commons.Adapter.AdapterBase;
import com.cxyz.commons.Adapter.ViewHolder;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataListener;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.imageview.CancelableImageView;
import com.cxyz.commons.widget.imageview.dialog.BigImageDialog;
import com.cxyz.commons.widget.loading.LoadingCreator;
import com.cxyz.logiccommons.domain.CheckResult;
import com.cxyz.logiccommons.domain.Photo;
import com.cxyz.vac.R;
import com.cxyz.vac.constant.NetWorkConstant;
import com.cxyz.vac.constant.RequestCenter;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;

import java.util.List;

/**
 * Created by Administrator on 2019/2/21.
 */

public class MineVacPhotoAdapter extends AdapterBase<MineVacPhotoAdapter.PhotoDto>{

    private static final String FILE_PROTOCOL = "file://";

    private Integer vacId;

    public MineVacPhotoAdapter(Context mContext, List<PhotoDto> list,Integer vacId) {
        super(mContext, list, R.layout.item_vac_photos_layout,R.layout.item_vac_photo_add_layout);
        this.vacId = vacId;
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
    public PhotoDto getItem(int position) {
        if(position == getCount()-1)
            return null;
        return super.getItem(position);
    }

    @Override
    public void convertView(ViewHolder holder, PhotoDto item, int position) {
        super.convertView(holder, item, position);
        if(position == getCount()-1)
        {
            holder.getConvertView().setOnClickListener(view -> {
                Intent intent = new Intent(getContext(), ImagePickActivity.class);
                intent.putExtra(ImagePickActivity.IS_NEED_CAMERA,true);
                intent.putExtra(ImagePickActivity.IS_NEED_IMAGE_PAGER,true);
                intent.putExtra(ImagePickActivity.IS_TAKEN_AUTO_SELECTED,true);
                ((Activity)getContext()).startActivityForResult(intent, vacId);
            });
            return;
        }
        if(item == null)
            return;
        CancelableImageView civ_photo = holder.getView(R.id.civ_photo);
        String path = item.getPhoto().getUri();
        if(item.getType() == PhotoDto.LOCAL)
            path = path.startsWith(FILE_PROTOCOL)?path:FILE_PROTOCOL.concat(path);
        else if(item.getType() == PhotoDto.INTERNET)
        {
            path = NetWorkConstant.VAC_PHOTO.concat(path);
            if(path.lastIndexOf('.')!=-1)
            {
                path = new StringBuilder(path.substring(0,path.lastIndexOf('.')))
                        .append('/').append(path.substring(path.lastIndexOf('.')+1)).toString();
            }
        }
        holder.setImageUrl(R.id.civ_photo,path);
        String finalPath = path;
        civ_photo.setOnClickListener(view -> {
            BigImageDialog.Builder builder = new BigImageDialog.Builder(getContext());
            builder.setResourceUrl(finalPath).build().show();
        });
        civ_photo.setOnCancelClickListener(iv -> {
            final okhttp3.Call call = RequestCenter.deletePhoto(item.getPhoto().getId(),
                    new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    CheckResult cr = (CheckResult) responseObj;
                    if(cr.isSuccess())
                    {
                        deleteItem(item);
                        notifyDataSetChanged();
                        LoadingCreator.stopLoading();
                    }
                    else
                        onFailure("删除失败");
                }

                @Override
                public void onFailure(Object error) {
                    ToastUtil.showShort("删除失败");
                    LoadingCreator.stopLoading();
                }
            });
            LoadingCreator.showLoading(getContext(), dialogInterface -> {
                if(!call.isCanceled())
                    call.cancel();
            });
        });
    }

    /**
     * 用来区别字符串代表的是path还是url，去获取图片显示
     */
    public static class PhotoDto{

        public static final int LOCAL = 0;

        public static final int INTERNET = 1;

        private Photo photo;

        private int type;

        public PhotoDto() {
        }

        public PhotoDto(Photo photo, int type)
        {
            this.photo = photo;
            this.type = type;
        }

        public Photo getPhoto() {
            return photo;
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "PhotoDto{" +
                    "photo=" + photo +
                    ", type=" + type +
                    '}';
        }
    }
}
