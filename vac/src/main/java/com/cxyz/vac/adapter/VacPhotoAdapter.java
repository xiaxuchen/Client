package com.cxyz.vac.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.cxyz.commons.Adapter.AdapterBase;
import com.cxyz.commons.Adapter.ViewHolder;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataListener;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.imageview.CancelableImageView;
import com.cxyz.commons.widget.imageview.dialog.BigImageDialog;
import com.cxyz.commons.widget.loading.LoadingCreator;
import com.cxyz.logiccommons.domain.CheckResult;
import com.cxyz.logiccommons.domain.Photo;
import com.cxyz.vac.R;
import com.cxyz.vac.constant.NetWorkConstant;
import com.cxyz.vac.constant.RequestCenter;
import com.vincent.filepicker.activity.ImagePickActivity;

import java.util.List;

/**
 * Created by Administrator on 2019/2/21.
 */

public class VacPhotoAdapter extends AdapterBase<Photo>{

    private boolean hide = true;

    private static final String HIDE_TEXT = "收起";

    private static final String SHOW_TEXT = "更多";

    private static final int NUM_COLUMNS = 3;


    public VacPhotoAdapter(Context mContext, List<Photo> list) {
        super(mContext, list,R.layout.item_vac_photo_layout,R.layout.item_vac_photo_hide_layout);
    }

    @Override
    public int getItemViewType(int position) {
        if(super.getCount()>NUM_COLUMNS)
        {
            if(position == getCount()-1)
            {
                return 1;
            }else {
                return 0;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        if(super.getCount() >NUM_COLUMNS)
        {
            if(hide)
                return 3;
           return super.getCount()+1;
        }
        return super.getCount();
    }

    @Override
    public Photo getItem(int position) {
        if(super.getCount() >NUM_COLUMNS && position == getCount()-1)
        {
            return null;
        }
        return super.getItem(position);
    }

    @Override
    public void convertView(ViewHolder holder, Photo item, int position) {
        super.convertView(holder, item, position);
        if(item == null)
        {
            handleNull(holder, position);
            return;
        }
        handleData(holder,item,position);

    }

    //正常处理逻辑
    private void handleData(ViewHolder holder, Photo item, int position) {
        ImageView iv_photo = holder.getView(R.id.iv_photo);
        String path = item.getUri();
        path = NetWorkConstant.VAC_PHOTO.concat(path);
        if(path.lastIndexOf('.')!=-1)
        {
            path = new StringBuilder(path.substring(0,path.lastIndexOf('.')))
                    .append('/').append(path.substring(path.lastIndexOf('.')+1)).toString();
        }
        holder.setImageUrl(R.id.iv_photo,path,R.mipmap.common_logo);
        String finalPath = path;
        iv_photo.setOnClickListener(view -> {
            BigImageDialog.Builder builder = new BigImageDialog.Builder(getContext());
            builder.setResourceUrl(finalPath).build().show();
        });
    }

    //当数据为空时处理逻辑
    private void handleNull(ViewHolder holder, int position) {
            if(hide)
            {
                holder.setText(R.id.tv_hide,SHOW_TEXT);
            }
            else
                holder.setText(R.id.tv_hide,HIDE_TEXT);
            holder.setOnClickListener(R.id.tv_hide,view -> {
                hide = !hide;
                notifyDataSetChanged();
            });
    }

}
