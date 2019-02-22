package com.cxyz.vac.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.date.DateTime;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.TitleView;
import com.cxyz.commons.widget.datetime.DateTimeDialog;
import com.cxyz.commons.widget.datetime.DateTimeSheet;
import com.cxyz.logiccommons.domain.User;
import com.cxyz.logiccommons.domain.Vacate;
import com.cxyz.logiccommons.manager.UserManager;
import com.cxyz.vac.R;
import com.cxyz.vac.adapter.UploadVacPhotoAdapter;
import com.cxyz.vac.dto.VacateDto;
import com.cxyz.vac.icon.IconfontModule;
import com.cxyz.vac.ipresenter.IUploadVacatePresenter;
import com.cxyz.vac.ipresenter.impl.IUploadVacatePresenterImpl;
import com.cxyz.vac.iview.IUploadVacateView;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.widget.IconTextView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2019/2/11.
 */

public class UploadVacateActivity extends BaseActivity<IUploadVacatePresenter> implements IUploadVacateView{

    private static final String PLEASE_CHECK = "请选择";

    private TitleView tv_title;

    private RadioGroup rg_time;//时间选择的选择器

    private LinearLayout ll_info,ll_week;//时间选择对应的布局

    private TextView tv_vac_type;//请假类型

    private EditText et_des;

    private IconTextView tv_type_icon;//请假类型指示icon

    private LinearLayout ll_time_selector;//时间选择的LinearLayout

    //时间段
    private TextView tv_vac_start,tv_vac_end;

    private IconTextView tv_start_icon,tv_end_icon;

    //周次
    private TextView tv_vac_start_date,tv_vac_end_date,tv_vac_start_time,tv_vac_end_time;

    private IconTextView tv_start_date_icon,tv_end_date_icon,tv_vac_start_time_icon,tv_vac_end_time_icon;

    //照片上传
    private CheckBox cb_monday,cb_tuesday,cb_wednesday,
            cb_thursday,cb_friday,cb_saturday,cb_sunday;

    private GridView gv_imgs;

    //数据

    private Integer vacType;//请假类型

    private DateTime start,end;//时间段的开始结束时间

    private DateTime week_start,week_end;

    private Button btn_commit;//提交按钮

    private UploadVacPhotoAdapter vacPhotosAdapter;

    @Override
    public int getContentViewId() {
        Iconify.with(new IconfontModule());
        return R.layout.activity_upload_vacate_layout;
    }

    @Override
    public void initView() {
        tv_title = findViewById(R.id.tv_title);
        rg_time = findViewById(R.id.rg_time);
        ll_info = findViewById(R.id.ll_info);
        ll_week = findViewById(R.id.ll_week);
        tv_vac_type = findViewById(R.id.tv_vac_type);
        tv_type_icon = findViewById(R.id.tv_type_icon);
        ll_time_selector = findViewById(R.id.ll_time_selector);
        et_des = findViewById(R.id.et_des);

        //时间段
        tv_vac_start = findViewById(R.id.tv_vac_start);
        tv_start_icon = findViewById(R.id.tv_start_icon);
        tv_vac_end = findViewById(R.id.tv_vac_end);
        tv_end_icon = findViewById(R.id.tv_end_icon);
        //周次
        tv_vac_start_date = findViewById(R.id.tv_vac_start_date);
        tv_vac_end_date = findViewById(R.id.tv_vac_end_date);
        tv_vac_start_time = findViewById(R.id.tv_vac_start_time);
        tv_vac_end_time = findViewById(R.id.tv_vac_end_time);
        tv_start_date_icon = findViewById(R.id.tv_start_date_icon);
        tv_end_date_icon = findViewById(R.id.tv_end_date_icon);
        tv_vac_end_time_icon = findViewById(R.id.tv_vac_end_time_icon);
        tv_vac_start_time_icon = findViewById(R.id.tv_vac_start_time_icon);
        
        //照片
        cb_monday = findViewById(R.id.cb_monday);
        cb_tuesday = findViewById(R.id.cb_tuesday);
        cb_wednesday = findViewById(R.id.cb_wednesday);
        cb_thursday = findViewById(R.id.cb_thursday);
        cb_friday = findViewById(R.id.cb_friday);
        cb_saturday = findViewById(R.id.cb_saturday);
        cb_sunday  = findViewById(R.id.cb_sunday);

        gv_imgs = findViewById(R.id.gv_imgs);
        gv_imgs.setAdapter(vacPhotosAdapter);

        btn_commit = findViewById(R.id.btn_commit);

        toggle();//先将日期部分切换为disable

    }

    @Override
    public void initData() {
        ArrayList<String> list = new ArrayList<>();
        vacPhotosAdapter = new UploadVacPhotoAdapter(getActivity(),list);
        week_start = new DateTime();
        week_end = new DateTime();
    }

    @Override
    public void setEvent() {
        tv_title.setBackClickListener(v -> onBackPressed());
        rg_time.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i == R.id.rb_week)
            {
                ll_week.setVisibility(View.VISIBLE);
                ll_info.setVisibility(View.GONE);
            }
            else
            {
                ll_week.setVisibility(View.GONE);
                ll_info.setVisibility(View.VISIBLE);
            }
        });

        getParent(tv_vac_type).setOnClickListener(view -> createTypeDialog());//点击类型的父view则显示类型对话框
        tv_type_icon.setOnClickListener(view -> {
            TextView v = (TextView) view;
            ToastUtil.showShort(v.getText());
            if(tv_vac_type.getText().toString().equals(PLEASE_CHECK))
            {
                createTypeDialog();
            }
            else
            {
                toggle();
                vacType = null;
                tv_vac_type.setText(PLEASE_CHECK);
                v.setText("{vac-next}");
            }

        });

        //选择时间段的事件
        getParent(tv_vac_start).setOnClickListener(view -> {
            showDateTimeDialog(tv_vac_start,tv_start_icon);
        });

        tv_start_icon.setOnClickListener(view -> {
            if(tv_vac_start.getText().equals(PLEASE_CHECK))
            {
                showDateTimeDialog(tv_vac_start,tv_start_icon);
            }
            else {
                tv_vac_start.setText(PLEASE_CHECK);
                tv_vac_start.setTextColor(getResources().getColor(R.color.common_line));
                tv_start_icon.setText("{vac-next}");
            }

        });
        getParent(tv_vac_end).setOnClickListener(view -> {
            showDateTimeDialog(tv_vac_end,tv_end_icon);
        });

        tv_end_icon.setOnClickListener(view -> {
            if(tv_vac_end.getText().equals(PLEASE_CHECK))
            {
                showDateTimeDialog(tv_vac_end,tv_end_icon);
            }
            else {
                tv_vac_end.setText(PLEASE_CHECK);
                tv_vac_end.setTextColor(getResources().getColor(R.color.common_line));
                tv_end_icon.setText("{vac-next}");
            }

        });

        //周次
        getParent(tv_vac_start_date).setOnClickListener(view -> {
            showDateDialog(tv_vac_start_date,tv_start_date_icon,true);
        });
        tv_start_date_icon.setOnClickListener(view -> {
            if(tv_vac_start_date.getText().equals(PLEASE_CHECK))
                showDateDialog(tv_vac_start_date,tv_start_date_icon,true);
            else
            {
                week_start.setYear(0).setMonth(0).setDay(0);
                tv_vac_start_date.setText(PLEASE_CHECK);
                tv_start_date_icon.setText("{vac-next}");
                tv_vac_start_date.setTextColor(getResources().getColor(R.color.common_line));
            }
        });

        getParent(tv_vac_end_date).setOnClickListener(view -> {
            showDateDialog(tv_vac_end_date,tv_end_date_icon,false);
        });
        tv_end_date_icon.setOnClickListener(view -> {
            if(tv_vac_end_date.getText().equals(PLEASE_CHECK))
                showDateDialog(tv_vac_end_date,tv_end_date_icon,false);
            else
            {
                week_end.setYear(0).setMonth(0).setDay(0);
                tv_vac_end_date.setText(PLEASE_CHECK);
                tv_end_date_icon.setText("{vac-next}");
                tv_vac_end_date.setTextColor(getResources().getColor(R.color.common_line));
            }
        });

        getParent(tv_vac_start_time).setOnClickListener(view -> {
            showTimeDialog(tv_vac_start_time,tv_vac_start_time_icon,true);
        });
        tv_vac_start_time_icon.setOnClickListener(view -> {
            if(tv_vac_start_time.getText().equals(PLEASE_CHECK))
                showTimeDialog(tv_vac_start_time,tv_vac_start_time_icon,true);
            else
            {
                week_start.setHour(0);
                week_start.setMinute(0);
                tv_vac_start_time.setText(PLEASE_CHECK);
                tv_vac_start_time_icon.setText("{vac-next}");
                tv_vac_start_time.setTextColor(getResources().getColor(R.color.common_line));
            }
        });

        getParent(tv_vac_end_time).setOnClickListener(view -> {
            showTimeDialog(tv_vac_end_time,tv_vac_end_time_icon,false);
        });
        tv_vac_end_time_icon.setOnClickListener(view -> {
            if(tv_vac_end_time.getText().equals(PLEASE_CHECK))
                showTimeDialog(tv_vac_end_time,tv_vac_end_time_icon,false);
            else
            {
                week_end.setHour(0);
                week_end.setMinute(0);
                tv_vac_end_time.setText(PLEASE_CHECK);
                tv_vac_end_time_icon.setText("{vac-next}");
                tv_vac_end_time.setTextColor(getResources().getColor(R.color.common_line));
            }
        });

        //照片
//        iv_add.setOnClickListener(view -> {
//        });

        btn_commit.setOnClickListener(view -> {
            LogUtil.e(week_start);
            LogUtil.e(week_end);
            //如果没有选择请假类型直接return
            if(vacType == null)
            {
                ToastUtil.showShort("请选择请假类型");
                return;
            }
            boolean isTimeLen = rg_time.getCheckedRadioButtonId() == R.id.rb_time_len;
            VacateDto dto = new VacateDto();
            if(isTimeLen)
            {
                //没有填写请假时间return
                if(start == null || end == null)
                {
                    ToastUtil.showShort("请填写请假时间");
                    return;
                }
                dto.setTimeType(Vacate.TIME_LEN);
                dto.setStart(start.toTimeStamp());
                dto.setEnd(end.toTimeStamp());
            }else {
                try{
                    dto.setTimeType(Vacate.WEEK);
                    dto.setStart(new Timestamp(getCalendar(0,0,0,Integer.parseInt
                            (week_start.getHour()),Integer.parseInt(week_start.getMinute()),Integer
                            .parseInt(week_start.getSecond())).getTimeInMillis()));
                    dto.setEnd(new Timestamp(getCalendar(0,0,0,Integer.parseInt
                            (week_end.getHour()),Integer.parseInt(week_end.getMinute()),Integer
                            .parseInt(week_end.getSecond())).getTimeInMillis()));
                    List<java.util.Date> dates = new ArrayList<>();
                    boolean weekChecked[] = new boolean[]{cb_sunday.isChecked(),cb_monday.isChecked(),
                            cb_tuesday.isChecked(),cb_wednesday.isChecked(),cb_thursday.isChecked(),
                            cb_friday.isChecked(), cb_saturday.isChecked()};
                    Calendar start = Calendar.getInstance();
                    start.clear();
                    start.set(Integer.parseInt(week_start.getYear()),Integer.parseInt(week_start.getMonth())-1,Integer.parseInt(week_start.getDay()));
                    Calendar end = Calendar.getInstance();
                    end.clear();
                    end.set(Integer.parseInt(week_end.getYear()),Integer.parseInt(week_end.getMonth())-1,Integer.parseInt(week_end.getDay()));
                    while(!start.getTime().equals(end.getTime()))
                    {
                        if(weekChecked[start.get(Calendar.DAY_OF_WEEK)-1])
                        {
                            dates.add(start.getTime());
                        }
                        start.add(Calendar.DAY_OF_MONTH,1);
                    }
                    dto.setDates(dates);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            dto.setType(vacType);
            dto.setDes(et_des.getText().toString().trim());
            User u = UserManager.getInstance().getUser();
            dto.setSponsor(new User(u.getId(),u.getType()));
            List<String> imgs = vacPhotosAdapter.getList();
            if(!imgs.isEmpty())
            {
                File files[] = new File[imgs.size()];
                int i = 0;
                for(String img:imgs)
                {
                    files[i] = new File(img);
                    i++;
                }
                iPresenter.uploadVacate(dto,files);
                LogUtil.e(dto);
                return;
            }
            iPresenter.uploadVacate(dto,null);
            LogUtil.e(dto);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.e(requestCode == Constant.REQUEST_CODE_PICK_IMAGE);
        switch (requestCode)
        {
            case Constant.REQUEST_CODE_PICK_IMAGE:
            {
                LogUtil.e(resultCode == RESULT_OK);
                if(resultCode == RESULT_OK)
                {
                    List<ImageFile> selected = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    LogUtil.e(selected);
                    for (ImageFile file:selected)
                    {
                        String path = file.getPath();
                        vacPhotosAdapter.appendItem(path);
                    }
                }
                break;
            }
        }
    }


    private void showTimeDialog(TextView tv, IconTextView tvIcon, boolean isStart)
    {
        DateTimeDialog.TimeBuilder builder = new DateTimeDialog.TimeBuilder(getActivity()).setTimeEnable(true,true,false);
        builder.setOnConfirmListener((dialog, hour, minute, second) -> {
            try {
                DateTime dateTime;
                if(isStart)
                    dateTime = week_start;
                else
                    dateTime = week_end;
                DateTime old = (DateTime) dateTime.clone();
                dateTime.setHour(hour);
                dateTime.setMinute(minute);
                tv.setText(new StringBuilder().append(hour).append(":").append(minute));
                tvIcon.setText("{vac-clear}");
                tv.setTextColor(getResources().getColor(R.color.common_black));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        });
        builder.build().show();
    }

    private void showDateDialog(TextView tv, IconTextView tvIcon,boolean isStart)
    {
        DateTimeDialog.DateBuilder builder = new DateTimeDialog.DateBuilder(getActivity());
        builder.setOnDateSelectListener((dialog, year, month, day) -> {
            try {
                DateTime dateTime = null;
                if(isStart)
                     dateTime = week_start;
                else
                    dateTime = week_end;
                DateTime old = (DateTime) dateTime.clone();
                dateTime.setYear(year);
                dateTime.setMonth(month);
                dateTime.setDay(day);
                if(week_start.compareTo(week_end) != 1 ||(week_start.getDate().equals
                        (new DateTime().getDate()))||(week_end.getDate().equals(new DateTime().getDate())))
                {
                    tv.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
                    tvIcon.setText("{vac-clear}");
                    tv.setTextColor(getResources().getColor(R.color.common_black));
                }
                else{
                    if(isStart)
                        week_start = old;
                    else
                        week_end = old;
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        });
        builder.build().show();
    }

    /**
     * 显示时间选择器
     */
    private void showDateTimeDialog(TextView tv,IconTextView icon)
    {
        DateTimeSheet.Builder dialogBuilder = new DateTimeSheet.Builder(getActivity());
        dialogBuilder.setOnConfirmListener((dialog, s, s1, s2, s3, s4) -> {
            DateTime oldTime;
            icon.setText("{vac-clear}");
            DateTime dateTime = new DateTime(s,s1,s2,s3,s4,0);
            LogUtil.e(dateTime);
            LogUtil.e(start+":"+end);
            if(tv == tv_vac_start)
            {
                oldTime = start;
                start = dateTime;
            }
            else
            {
                oldTime = end;
                end = dateTime;
            }
            LogUtil.e(start+":"+end);
            if(start == null || end == null)
            {
                StringBuilder builder = new StringBuilder();
                builder.append(s).append("-").append(s1).
                        append("-").append(s2).append(" ").append(s3).append(":").append(s4);
                tv.setText(builder.toString());
                tv.setTextColor(getResources().getColor(R.color.common_black));
                dialog.dismiss();
                return;
            }
            if(start.compareTo(end) != -1)
            {
                if(tv.equals(tv_vac_start))
                {
                    start = oldTime;
                    if(oldTime == null)
                        tv_start_icon.setText("{vac-next}");
                }
                else
                {
                    end = oldTime;
                    if(oldTime == null)
                        tv_end_icon.setText("{vac-next}");
                }
                ToastUtil.showShort("开始时间必须早于结束时间");
                return;
            }
            StringBuilder builder = new StringBuilder();
            builder.append(s).append("-").append(s1).
                    append("-").append(s2).append(" ").append(s3).append(":").append(s4);
            tv.setText(builder.toString());
            tv.setTextColor(getResources().getColor(R.color.common_black));
            dialog.dismiss();
            dialog.dismiss();
        });
        dialogBuilder.build().show();
    }

    /**
     * 切换时间选择是否可用
     */
    private void toggle()
    {
        toggleEnable(ll_time_selector);
        if(ll_time_selector.isEnabled())
            ll_time_selector.setAlpha(1f);
        else
            ll_time_selector.setAlpha(0.5f);
    }

    private void toggleEnable(ViewGroup vg)
    {
        boolean enable = !vg.isEnabled();
        vg.setEnabled(enable);
        for(int i = 0;i<vg.getChildCount();i++)
        {
            View v = vg.getChildAt(i);
            if(v instanceof ViewGroup)
            {
                toggleEnable((ViewGroup) v);
            }
            v.setEnabled(enable);
        }
    }

    private void createTypeDialog()
    {
        String[] items = {"事假","病假","值班"};
        QMUIDialog.MenuDialogBuilder builder = new QMUIDialog.MenuDialogBuilder(getActivity());
        builder.addItems(items,(dialogInterface, i) ->{
            if(tv_vac_type.getText().toString().equals("请选择"))
                toggle();
            tv_vac_type.setText(items[i]);
            tv_vac_type.setTextColor(getResources().getColor(R.color.common_black));
            dialogInterface.cancel();
            vacType = i;
            tv_type_icon.setText("{vac-clear}");
        });
        builder.show();
    }


    @Override
    protected IUploadVacatePresenter createIPresenter() {
        return new IUploadVacatePresenterImpl();
    }


    @Override
    public void uploadSuccess() {
        SweetAlertDialog dialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.SUCCESS_TYPE)
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    finish();
                })
                .setContentText("请假条上传成功")
                .setConfirmText("我知道了")
                .setTitleText("请假信息");
        dialog.show();
    }

    @Override
    public void uploadFail(String error) {
        SweetAlertDialog dialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE)
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                })
                .setContentText("请假条上传失败")
                .setConfirmText("我知道了")
                .setTitleText("请假信息");
        dialog.show();
    }

    Calendar getCalendar(int year,int month,int day,int hour,int minute,int second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year,month,day,hour,minute,second);
        return calendar;
    }
}
