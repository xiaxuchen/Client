package com.cxyz.vac.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cxyz.commons.Adapter.AdapterBase;
import com.cxyz.commons.Adapter.ViewHolder;
import com.cxyz.commons.utils.BitmapUtil;
import com.cxyz.commons.utils.DateUtil;
import com.cxyz.commons.utils.ImageLoaderManager;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.widget.imageview.CancelableImageView;
import com.cxyz.commons.widget.imageview.listener.OnCancelClickListener;
import com.cxyz.logiccommons.domain.Photo;
import com.cxyz.logiccommons.typevalue.AuditState;
import com.cxyz.logiccommons.typevalue.VacType;
import com.cxyz.logiccommons.typevalue.VacateType;
import com.cxyz.vac.R;
import com.cxyz.vac.dto.VacateDto;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/12/31.
 */

public class VacateAdapter extends AdapterBase<VacateDto> implements OnCancelClickListener{

    public VacateAdapter(Context mContext, List<VacateDto> list, int... mItemLayoutId) {
        super(mContext, list, mItemLayoutId);
    }

    @Override
    public void convertView(ViewHolder holder, VacateDto item) {
        super.convertView(holder, item);
        holder.setText(R.id.tv_start, DateUtil.dateToString(new Date(item.getStart().getTime()),
                DateUtil.DatePattern.ONLY_MINUTE));
        holder.setText(R.id.tv_end,DateUtil.dateToString(new Date(item.getEnd().getTime()),
                DateUtil.DatePattern.ONLY_MINUTE));
        holder.setText(R.id.tv_sponsor_time,DateUtil.dateToString(new Date(item.getSponsorTime().getTime()),
                DateUtil.DatePattern.ONLY_MINUTE));
        if(item.getLen() != null)
            holder.setText(R.id.tv_len,item.getLen()+"天");
        else
        {
            holder.setVisible(R.id.tv_len_hint,View.GONE);
            holder.setVisible(R.id.tv_len, View.GONE);
        }
        holder.setText(R.id.tv_vac_type,item.getType()== VacType.VAC_THING?"事假":"病假");

        TextView tv_reason = holder.getView(R.id.tv_reason);
        if(item.getDes() != null && !item.getDes().isEmpty())
            tv_reason.setText(item.getDes());
        else
            tv_reason.setText("无");
        int state = item.getState();
        Button btn_audited = holder.getView(R.id.btn_audited);
        if(state == AuditState.ONLY_VACATE)
        {
            holder.setVisible(R.id.tv_type,View.VISIBLE);
            btn_audited.setVisibility(View.GONE);
        }else {
            if( state == AuditState.WAIT_AUDIT)
            {
                btn_audited.setText("待审核");
            }else if(state == AuditState.FAIL)
            {
                btn_audited.setText("已拒绝");
            }
            else if(state == AuditState.SUCCESS) {
                btn_audited.setText("已同意");
            }
            btn_audited.setVisibility(View.VISIBLE);
            holder.setVisible(R.id.tv_type,View.GONE);
        }
        ListView lv_audits = holder.getView(R.id.lv_audits);
        if(item.getAudits().size() == 0)
            holder.setVisible(R.id.tv_audits_hint,View.GONE);
        else
            holder.setVisible(R.id.tv_audits_hint,View.VISIBLE);
        lv_audits.setAdapter(new AuditedAdapter(mContext,item.getAudits(),R.layout.item_vacate_audit_layout));
        holder.setOnClickListener(R.id.tv_audits_hint,view ->{
            if(lv_audits.getVisibility() == View.VISIBLE)
            {
                lv_audits.setVisibility(View.GONE);
                holder.setText(R.id.tv_audits_hint,"审核情况 {vac-down}");
            }else {
                lv_audits.setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_audits_hint,"审核情况 {vac-up}");
            }
        } );
        //准备图片的数据
        List<MineVacPhotoAdapter.PhotoDto> photoDtos = new ArrayList<>();
        for(Photo photo : item.getPhotos())
            photoDtos.add(new MineVacPhotoAdapter.PhotoDto(photo, MineVacPhotoAdapter.PhotoDto.INTERNET));
        //设置适配器
        GridView gv_imgs = holder.getView(R.id.gv_imgs);
        gv_imgs.setAdapter(new MineVacPhotoAdapter(getContext(),photoDtos,item.getId()));
    }

    @Override
    public void onCancelClick(CancelableImageView iv) {
        LinearLayout ll_imgs = (LinearLayout) iv.getParent();
        ll_imgs.removeView(iv);
    }
}
