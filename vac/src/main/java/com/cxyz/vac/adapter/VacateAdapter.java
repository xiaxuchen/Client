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
import com.cxyz.logiccommons.domain.Vacate;
import com.cxyz.logiccommons.typevalue.AuditState;
import com.cxyz.logiccommons.typevalue.VacType;
import com.cxyz.logiccommons.typevalue.VacateType;
import com.cxyz.vac.R;
import com.cxyz.vac.dto.VacateDto;
import com.joanzapata.iconify.widget.IconTextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/12/31.
 */

public class VacateAdapter extends AdapterBase<VacateDto> implements OnCancelClickListener{

    public VacateAdapter(Context mContext, List<VacateDto> list) {
        super(mContext, list, R.layout.item_vacate_layout,R.layout.item_vacate_only_photo_layout);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getTimeType() == Vacate.WEEK?1:0;
    }

    @Override
    public void convertView(ViewHolder holder, VacateDto item) {
        super.convertView(holder, item);
        handleCommon(holder,item);
        if(item.getTimeType() == Vacate.WEEK)
        {
            handleWeek(holder,item);
        }else {
            handleTimeLen(holder,item);
        }
    }

    private void handleCommon(ViewHolder holder, VacateDto item) {
        int state = item.getState();
        if(state == AuditState.ONLY_VACATE)
            holder.setVisible(R.id.tv_type,View.VISIBLE);
        else
            holder.setVisible(R.id.tv_type,View.GONE);
        //设置更新时间
        holder.setText(R.id.tv_sponsor_time,getDateToMinute(item.getSponsorTime().getTime()));
        //设置时长，若为空则隐藏
        if(item.getLen() != null)
            holder.setText(R.id.tv_len,item.getLen()+"天");
        else
        {
            holder.setVisible(R.id.tv_len_hint,View.GONE);
            holder.setVisible(R.id.tv_len, View.GONE);
        }

        //设置请假类型
        String type = null;
        switch (item.getType())
        {
            case VacateType.VACATE_THING:type = "事假";break;
            case VacateType.VACATE_ILLNESS:type = "病假";break;
            case VacateType.VACATE_ON_CALL:type = "值班";
        }
        holder.setText(R.id.tv_vac_type,type);

        //设置请假原因
        TextView tv_reason = holder.getView(R.id.tv_reason);
        if(item.getDes() != null && !item.getDes().isEmpty())
            tv_reason.setText(item.getDes());
        else
            tv_reason.setText("无");

        //准备图片的数据 TODO INTERNET 可能不需要
        List<MineVacPhotoAdapter.PhotoDto> photoDtos = new ArrayList<>();
        for(Photo photo : item.getPhotos())
            photoDtos.add(new MineVacPhotoAdapter.PhotoDto(photo, MineVacPhotoAdapter.PhotoDto.INTERNET));
        //设置适配器
        GridView gv_imgs = holder.getView(R.id.gv_imgs);
        gv_imgs.setAdapter(new MineVacPhotoAdapter(getContext(),photoDtos,item.getId()));
    }

    private void handleTimeLen(ViewHolder holder, VacateDto item) {
        //设置时间
        holder.setText(R.id.tv_start,getDateToMinute(item.getStart().getTime()));
        holder.setText(R.id.tv_end,getDateToMinute(item.getEnd().getTime()));
        //设置审核结果
        Button btn_audited = holder.getView(R.id.btn_audited);
        int state = item.getState();
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
    }

    //处理请假条
    private void handleWeek(ViewHolder holder, VacateDto item) {
        //设置时间
        holder.setText(R.id.tv_time,getTime(item.getStart().getTime())+"-"+getTime(item.getEnd().getTime()));
        final List<Date> dates = item.getDates();
        Collections.sort(dates, (o1, o2) -> o1.getTime()>o2.getTime()?1:-1);//排序
        //设置开始到结束时间
        holder.setText(R.id.tv_start,getDateToDay(dates.get(0).getTime()));
        holder.setText(R.id.tv_end,getDateToDay(dates.get(dates.size()-1).getTime()));
        final boolean weekUsed[] = new boolean[8];
        //排序后时间从小到大，如果连续7天内的星期没有则都不会有
        for(int i = 0;i<(dates.size()>7?7:dates.size());i++)
            weekUsed[getWeek(dates.get(i))] = true;
        holder.setText(R.id.tv_week,buildWeekText(weekUsed));
    }

    //构建星期文本
    private String buildWeekText(boolean[] weekUsed) {
        final StringBuilder builder = new StringBuilder();
        boolean none = true;
        final String weeks[] = {"","周日","周一","周二","周三","周四","周五","周六"};
        for(int i = 1;i<weekUsed.length;i++)
        {
            if(weekUsed[i])
            {
                builder.append(weeks[i]);
                if(none)
                    none = false;
            }
        }
        if(none)
            return "无";
        else
            return builder.toString();
    }

    @Override
    public void onCancelClick(CancelableImageView iv) {
        LinearLayout ll_imgs = (LinearLayout) iv.getParent();
        ll_imgs.removeView(iv);
    }

    //获取格式到分的时间
    private String getDateToMinute(long time)
    {
        return DateUtil.dateToString(new Date(time),
                DateUtil.DatePattern.ONLY_MINUTE);
    }

    //获取格式到天的时间
    private String getDateToDay(long time)
    {
        return DateUtil.dateToString(new Date(time),
                DateUtil.DatePattern.ONLY_DAY);
    }

    //获取时分
    private String getTime(long time)
    {
        return DateUtil.dateToString(new Date(time), DateUtil.DatePattern.ONLY_HOUR_MINUTE);
    }

    private int getWeek(Date d)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
