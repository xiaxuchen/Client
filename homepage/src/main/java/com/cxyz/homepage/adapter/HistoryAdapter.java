package com.cxyz.homepage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import com.cxyz.commons.Adapter.AdapterBase;
import com.cxyz.commons.Adapter.ViewHolder;
import com.cxyz.commons.date.Date;
import com.cxyz.commons.date.DateTime;
import com.cxyz.homepage.R;
import com.cxyz.homepage.dto.CheckHistoryDto;
import com.cxyz.logiccommons.typevalue.CheckRecordResult;

import java.util.List;

/**
 * Created by Administrator on 2019/2/6.
 */

public class HistoryAdapter extends AdapterBase<CheckHistoryDto> {

    private OnItemClickListener listener;

    public OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public HistoryAdapter() {
    }

    public HistoryAdapter(Context mContext, List<CheckHistoryDto> list, int... mItemLayoutId) {
        super(mContext, list, mItemLayoutId);
    }


    @Override
    public void convertView(ViewHolder holder, CheckHistoryDto item,int position) {
        super.convertView(holder, item);
        holder.getConvertView().setOnClickListener(view -> {
            if(listener != null)
            listener.onItemClick(holder.getConvertView(),position,item);
        });
        DateTime dateTime = DateTime.fromTS(item.getCommitTime());
        Date date = Date.fromUDate(item.getDate());
        holder.setText(R.id.tv_lesson_name,item.getLessonName());
        holder.setText(R.id.tv_date, date.getDate());
        holder.setText(R.id.tv_times,item.getStart()+"-"+item.getEnd()+"节");
        if(date.getDate().equals(dateTime.getDate()))
            holder.setText(R.id.tv_commit_time,dateTime.getTime());
        else
            holder.setText(R.id.tv_commit_time,dateTime.getDate()+" "+dateTime.getTime());

        if(item.getResults() == null)
            return;
        int counts[] = new int[4];

        if(item.getResults().get(0).getResultType() == null)
        {
            holder.setText(R.id.tv_result,"全勤");
        }else {
            for(CheckHistoryDto.RecordResultCustom result:item.getResults())
            {
                switch (result.getResultType())
                {
                    case CheckRecordResult.VACATE:{
                        counts[3] = result.getCount();
                    }break;
                    case CheckRecordResult.ABSENTEEISM:{
                        counts[2] = result.getCount();
                    }break;
                    case CheckRecordResult.EARLYLEAVE:{
                        counts[1] = result.getCount();
                    }break;
                    case CheckRecordResult.LATE:{
                        counts[0] = result.getCount();
                    }break;
                }
            }
            StringBuilder builder = new StringBuilder();
            boolean flag = true;
            if(counts[0] != 0)
                builder.append(counts[0]).append("迟到 ");
            if(counts[1] != 0)
                builder.append(counts[1]).append("早退 ");
            if(counts[2] != 0)
                builder.append(counts[2]).append("缺勤 ");
            if(counts[3]!=0)
                builder.append(counts[3]).append("请假 ");

            holder.setText(R.id.tv_result,builder.toString());
        }

    }

    public interface OnItemClickListener{

        void onItemClick(View view,int position,CheckHistoryDto data);
    }


}
