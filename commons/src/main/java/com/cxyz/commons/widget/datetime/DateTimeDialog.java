package com.cxyz.commons.widget.datetime;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Trace;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.view.WheelView;
import com.cxyz.commons.R;
import com.cxyz.commons.date.DateTime;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ScreenUtil;
import com.haibin.calendarview.CalendarView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2019/2/11.
 */

public class DateTimeDialog extends Dialog {
    public DateTimeDialog(@NonNull Context context) {
        super(context);
    }

    public DateTimeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DateTimeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = (int) (ScreenUtil.getScreenWidth(getContext())/1.5);
        setCanceledOnTouchOutside(true);
    }

    public interface OnDateSelectListener{
        void onDateSelect(DateTimeDialog dialog,int year,int month,int day);
    }

    public interface OnConfirmListener{
        void onConfirm(DateTimeDialog dialog,int hour,int minute,int second);
    }

    public static class DateBuilder{

        private Context mContext;

        private CalendarView calendarView;

        private DateTimeDialog mDialog;

        private OnDateSelectListener listener;

        public DateBuilder(Context context)
        {
            this.mContext = context;
        }

        /**
         * 获取
         * @return
         */
        private View getContentView()
        {
            return View.inflate(mContext,R.layout.dialog_date_layout,null);
        }

        private void initView(View view)
        {
            calendarView = view.findViewById(R.id.calendarView);
        }

        private void initListener()
        {
            calendarView.setOnDateSelectedListener((calendar, isClick) -> {
                if(listener != null && isClick)
                    listener.onDateSelect(mDialog,calendar.getYear(),calendar.getMonth(),calendar.getDay());

            });

        }

        /**
         * 设置当日期选中时的监听
         * @param listener
         * @return
         */
        public DateBuilder setOnDateSelectListener(OnDateSelectListener listener)
        {
            this.listener = listener;
            return this;
        }


        public DateTimeDialog build()
        {
            mDialog = new DateTimeDialog(mContext,R.style.dialog_date_time);
            View v = getContentView();
            mDialog.setContentView(v);
            initView(v);
            initListener();
            return mDialog;
        }

    }


    public static class TimeBuilder{

        private Context mContext;

        private WheelView wheelview_hour,wheelview_minute,wheelView_second;

        private DateTimeDialog mDialog;

        private TextView tv_cancel,tv_confirm;

        private boolean hourEnable = true,minuteEnable = true,secondEnable = true;

        private OnConfirmListener listener;

        private Integer hour,minute,second;

        public Integer getHour() {
            return hour;
        }

        public void setHour(Integer hour) {
            this.hour = hour;
        }

        public Integer getMinute() {
            return minute;
        }

        public void setMinute(Integer minute) {
            this.minute = minute;
        }

        public Integer getSecond() {
            return second;
        }

        public void setSecond(Integer second) {
            this.second = second;
        }

        public TimeBuilder(Context context)
        {
            this.mContext = context;
        }

        /**
         * 获取
         * @return
         */
        private View getContentView()
        {
            return View.inflate(mContext,R.layout.dialog_time_layout,null);
        }

        private void initView(View view)
        {
            wheelview_hour = view.findViewById(R.id.wheelview_hour);
            wheelview_minute = view.findViewById(R.id.wheelview_minute);
            wheelView_second = view.findViewById(R.id.wheelview_second);
            tv_cancel = view.findViewById(R.id.tv_cancel);
            tv_confirm = view.findViewById(R.id.tv_confirm);
            if(!hourEnable)
                wheelview_hour.setVisibility(View.GONE);
            if (!minuteEnable)
                wheelview_minute.setVisibility(View.GONE);
            if(!secondEnable)
                wheelView_second.setVisibility(View.GONE);
        }

        /**
         * 设置时间是否可用
         * @param hourEnable 小时
         * @param minuteEnable 分钟
         * @param secondEnable 秒
         */
        public TimeBuilder setTimeEnable(boolean hourEnable,boolean minuteEnable,boolean secondEnable)
        {
            this.hourEnable = hourEnable;
            this.minuteEnable = minuteEnable;
            this.secondEnable = secondEnable;
            return this;
        }

        /**
         * 设置当确定按钮点击时的监听
         * @param listener
         * @return
         */
        public TimeBuilder setOnConfirmListener(OnConfirmListener listener)
        {
            this.listener = listener;
            return this;
        }

        private void initListener()
        {
            tv_cancel.setOnClickListener(view -> mDialog.cancel());
            tv_confirm.setOnClickListener(view -> {
                if(listener != null)
                    listener.onConfirm(mDialog,wheelview_hour.getCurrentItem(),wheelview_minute.getCurrentItem(),wheelView_second.getCurrentItem());
            });
        }

        private void initWheels()
        {
            wheelview_hour.setCyclic(true);
            wheelview_minute.setCyclic(true);
            wheelView_second.setCyclic(true);
            final List<String> hours = new ArrayList<>();
            final List<String> minutes = new ArrayList<>();
            for(int i = 0;i<60;i++)
            {
                minutes.add(i+"");

                if(i<24)
                    hours.add(i+"");
            }

            wheelview_hour.setAdapter(new WheelAdapter() {
                @Override
                public int getItemsCount() {
                    return hours.size();
                }

                @Override
                public Object getItem(int index) {
                    return hours.get(index);
                }

                @Override
                public int indexOf(Object o) {
                    return hours.indexOf(o);
                }
            });

            wheelview_minute.setAdapter(new WheelAdapter() {
                @Override
                public int getItemsCount() {
                    return minutes.size();
                }

                @Override
                public Object getItem(int index) {
                    return minutes.get(index);
                }

                @Override
                public int indexOf(Object o) {
                    return minutes.indexOf(o);
                }
            });

            wheelView_second.setAdapter(new WheelAdapter() {
                @Override
                public int getItemsCount() {
                    return minutes.size();
                }

                @Override
                public Object getItem(int index) {
                    return minutes.get(index);
                }

                @Override
                public int indexOf(Object o) {
                    return minutes.indexOf(o);
                }
            });

            if(hour != null && minute != null && second != null)
            {
                wheelview_hour.setCurrentItem(hour-1);
                wheelview_minute.setCurrentItem(minute-1);
                wheelView_second.setCurrentItem(second-1);
                return;
            }
            Calendar calendar = Calendar.getInstance();
            wheelview_hour.setCurrentItem(calendar.get(Calendar.HOUR_OF_DAY));
            wheelview_minute.setCurrentItem(calendar.get(Calendar.MINUTE));
            wheelView_second.setCurrentItem(calendar.get(Calendar.SECOND));
        }


        public DateTimeDialog build()
        {
            mDialog = new DateTimeDialog(mContext,R.style.dialog_date_time);
            View v = getContentView();
            mDialog.setContentView(v);
            initView(v);
            initWheels();
            initListener();
            return mDialog;
        }

    }

}
