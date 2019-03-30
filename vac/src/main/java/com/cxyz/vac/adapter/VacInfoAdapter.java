package com.cxyz.vac.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Size;
import android.view.View;
import android.widget.GridView;

import com.cxyz.commons.Adapter.AdapterBase;
import com.cxyz.commons.Adapter.ViewHolder;
import com.cxyz.commons.utils.ImageLoaderManager;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.widget.imageview.CircleImage;
import com.cxyz.logiccommons.domain.Vacate;
import com.cxyz.vac.R;
import com.cxyz.vac.constant.NetWorkConstant;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2019/2/25.
 */

public class VacInfoAdapter extends AdapterBase<Vacate> {

    private HashMap<Integer,VacPhotoAdapter> adapterMap = new HashMap<>();

    private final int size = ScreenUtil.dp2px(getContext(),(ScreenUtil.px2dp(getContext(),ScreenUtil.getScreenWidth(getContext()))-8*4)/3);

    public VacInfoAdapter(Context mContext, List list) {
        super(mContext, list, R.layout.item_vac_info_photos_layout);
    }

    @Override
    public void convertView(ViewHolder holder, Vacate item, int position) {
        super.convertView(holder, item, position);
        holder.setText(R.id.tv_sponsor_name,item.getSponsor().getName());
        //用户头像
//        if(item.getSponsor().getPhone() != null)
//        {
//            CircleImage civ_photo = holder.getView(R.id.civ_photo);
//            ImageLoaderManager.getInstance(getContext()).displayImage(civ_photo,null,new ImageLoaderManager.ImageLoadingListenerWrapper(){
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                    civ_photo.setBitmap(loadedImage);
//                }
//            });
//        }
        GridView gv_imgs = holder.getView(R.id.gv_imgs);
        gv_imgs.setColumnWidth(size);
        VacPhotoAdapter adapter = adapterMap.get(position);
        if( adapter == null)
            adapter = new VacPhotoAdapter(getContext(),item.getPhotos());
        gv_imgs.setAdapter(adapter);
    }
}
