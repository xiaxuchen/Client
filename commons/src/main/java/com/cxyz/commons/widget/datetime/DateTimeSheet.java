package com.cxyz.commons.widget.datetime;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ScreenUtil;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.cxyz.commons.R;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetItemView;

/**
 * Created by Administrator on 2019/2/9.
 */

public class DateTimeSheet extends Dialog {

    private boolean mIsAnimating = false;

    private View contentView;//内容view

    private static final int DURATION = 200;

    public DateTimeSheet(@NonNull Context context) {
        super(context,R.style.sheet_date_time);
    }

    public DateTimeSheet(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DateTimeSheet(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //noinspection ConstantConditions
        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        // 在底部，宽度撑满
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;

        int screenWidth = ScreenUtil.getScreenWidth(getContext());
        int screenHeight = ScreenUtil.getScreenHeight(getContext());
        params.width = screenWidth < screenHeight ? screenWidth : screenHeight;
        getWindow().setAttributes(params);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void setContentView(int layoutResID) {
        contentView = LayoutInflater.from(getContext()).inflate(layoutResID, null);
        super.setContentView(contentView);
    }

    @Override
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        contentView = view;
        super.setContentView(view, params);
    }

    @Override
    public void setContentView(@NonNull View view) {
        contentView = view;
        super.setContentView(view);
    }

    @Override
    public void show() {
        super.show();
        animateUp();
    }

    @Override
    public void dismiss() {
        if (mIsAnimating) {
            return;
        }
        animateDown();
    }

    /**
     * 升起动画
     */
    private void animateUp()
    {
        if(contentView != null)
            return;
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
        );
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(DURATION);
        set.setFillAfter(true);
        contentView.startAnimation(set);
    }

    /**
     * BottomSheet降下动画
     */
    private void animateDown() {
        if (contentView == null) {
            return;
        }
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
        );
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.setInterpolator(new DecelerateInterpolator());
        set.setDuration(DURATION);
        set.setFillAfter(true);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIsAnimating = false;
                /**
                 * Bugfix： Attempting to destroy the window while drawing!
                 */
                contentView.post(() -> {
                    // java.lang.IllegalArgumentException: View=com.android.internal.policy.PhoneWindow$DecorView{22dbf5b V.E...... R......D 0,0-1080,1083} not attached to window manager
                    // 在dismiss的时候可能已经detach了，简单try-catch一下
                    try {
                        DateTimeSheet.super.dismiss();
                    } catch (Exception e) {
                        LogUtil.w( "dismiss error\n" + Log.getStackTraceString(e));
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        contentView.startAnimation(set);
    }

    public static class Builder{

        private boolean isDate = true;//当前视图标识，是否为日期视图

        private boolean isAnimating = false;

        private boolean isFirstSelect = true;//为了解决框架自带bug(初始化日期时会自动调用OnDateSelectedListener...)

        private Integer year,month,day,hour,minute;
        private TextView tv_date,tv_time,tv_confirm;
        private DateTimeSheet mDailog;
        private WheelView wheelview_hour,wheelview_minute;
        private View v_date_line,v_time_line;
        private LinearLayout ll_time;
        private CalendarView calendarView;
        private Context mContext;
        private OnConfirmListener listener;

        public Builder(Context context)
        {
            mContext = context;
        }

        private View getContentView()
        {
            return View.inflate(mContext,R.layout.dialog_date_time_layout,null);
        }

        /**
         * 设置日期
         * @param year
         * @param month
         * @param day
         * @return
         */
        public Builder setDate(int year,int month,int day)
        {
            this.year = year;
            this.month = month;
            this.day = day;
            return this;
        }

        /**
         * 设置时间
         * @param hour
         * @param minute
         * @return
         */
        public Builder setTime(int hour,int minute)
        {
            this.hour = hour;
            this.minute = minute;
            return this;
        }

        public Builder setOnConfirmListener(OnConfirmListener listener)
        {
            this.listener = listener;
            return this;
        }

        public DateTimeSheet build()
        {
            mDailog = new DateTimeSheet(mContext,R.style.sheet_date_time);
            View v = getContentView();
            mDailog.setContentView(v);
            initView(v);
            initWheels();
            initDateTime();
            initListener();
            return mDailog;
        }

        private void initView(View v)
        {
            tv_date = v.findViewById(R.id.tv_date);
            tv_time = v.findViewById(R.id.tv_time);
            tv_confirm = v.findViewById(R.id.tv_confirm);
            v_date_line = v.findViewById(R.id.v_date_line);
            v_time_line = v.findViewById(R.id.v_time_line);
            wheelview_hour = v.findViewById(R.id.wheelview_hour);
            wheelview_minute = v.findViewById(R.id.wheelview_minute);
            calendarView = v.findViewById(R.id.calendarView);
            ll_time = v.findViewById(R.id.ll_time);
            v_date_line = v.findViewById(R.id.v_date_line);
            v_time_line = v.findViewById(R.id.v_time_line);
        }

        private void initDateTime() {
            if(year != null && month != null && day != null)
                calendarView.scrollToCalendar(year,month,day);
            if(hour != null && minute != null)
            {
                wheelview_hour.setCurrentItem(hour-1);
                wheelview_minute.setCurrentItem(minute-1);
            }else {
                //如果没有设置时间则用当前时间
                Calendar calendar = Calendar.getInstance();
                wheelview_hour.setCurrentItem(calendar.get(Calendar.HOUR_OF_DAY));
                wheelview_minute.setCurrentItem(calendar.get(Calendar.MINUTE));
            }
            initDateView();
            initTimeView();
        }

        private void initWheels()
        {
            wheelview_hour.setCyclic(true);
            wheelview_minute.setCyclic(true);
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
        }

        private void initListener(){
            tv_date.setOnClickListener(view ->
            {
                showAndHide(true);
            });
            tv_time.setOnClickListener(view -> {
                showAndHide(false);
            });
            calendarView.setOnDateSelectedListener( (calendar, isClick) -> {
                if (isFirstSelect)
                {
                    isFirstSelect = false;
                    return;
                }
                if(isAnimating)
                    return;
                initDateView();
                if(isClick)
                showAndHide(false);
            });
            wheelview_hour.setOnItemSelectedListener(index -> {
                initTimeView();
            });
            wheelview_minute.setOnItemSelectedListener(index -> {
                initTimeView();
            });
            if(listener != null)
                tv_confirm.setOnClickListener(view -> {
                    com.haibin.calendarview.Calendar c = calendarView.getSelectedCalendar();
                    listener.onConfirm(mDailog,c.getYear(),c.getMonth(),c.getDay(),wheelview_hour.getCurrentItem(),wheelview_minute.getCurrentItem());
                });
        }

        private void initDateView()
        {
            final com.haibin.calendarview.Calendar calendar = calendarView.getSelectedCalendar();
            StringBuilder builder = new StringBuilder();
            builder.append(calendar.getYear()).append("年").append(calendar.getMonth()).append("月").append(calendar.getDay()).append("日");
            tv_date.setText(builder.toString());
        }

        private void initTimeView()
        {
            StringBuilder builder = new StringBuilder();
            builder.append(wheelview_hour.getCurrentItem()).append(":").append(wheelview_minute.getCurrentItem());
            tv_time.setText(builder.toString());
        }

        /**
         * 显示隐藏动画
         * @param isDate 是否显示日期view
         */
        private void showAndHide(boolean isDate)
        {

            if(isDate == this.isDate || isAnimating)
                return;
            View v1 = calendarView,v2 = ll_time;
            if(!isDate)
            {
                v1 = ll_time;
                v2 = calendarView;
                v_time_line.setVisibility(View.VISIBLE);
                v_date_line.setVisibility(View.INVISIBLE);
            }else {
                v_time_line.setVisibility(View.INVISIBLE);
                v_date_line.setVisibility(View.VISIBLE);
            }
            final View showView = v1,hideView = v2;
            final ValueAnimator animator = ValueAnimator.ofFloat(0, 1).setDuration(DURATION);
            animator.addUpdateListener(anim -> {
                float val = (Float)anim.getAnimatedValue();
                if (val == 0)
                {
                    isAnimating = true;
                    showView.setVisibility(View.VISIBLE);
                }
                if(val == 1)
                {
                    hideView.setVisibility(View.INVISIBLE);
                    isAnimating = false;
                }
                hideView.setAlpha(1-val);
                showView.setAlpha(val);
            });
            animator.start();
            this.isDate = isDate;
        }

        /**
         * 确定按钮的点击监听
         */
        public interface OnConfirmListener{

            void onConfirm(DateTimeSheet dialog, int year, int month, int day, int hour, int minute);
        }

    }


}
