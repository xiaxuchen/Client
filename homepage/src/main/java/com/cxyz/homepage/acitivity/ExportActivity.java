package com.cxyz.homepage.acitivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.TitleView;
import com.cxyz.homepage.R;
import com.cxyz.homepage.dto.GTaskDto;
import com.cxyz.homepage.dto.GradeTaskDto;
import com.cxyz.homepage.icon.IconfontModule;
import com.cxyz.homepage.ipresenter.IExportPresenter;
import com.cxyz.homepage.ipresenter.impl.IExportPresenterImpl;
import com.cxyz.homepage.iview.IExportView;
import com.joanzapata.iconify.Iconify;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class ExportActivity extends BaseActivity<IExportPresenter> implements IExportView {
    private TitleView tv_title;
    private TextView tv_port_setbegintime;
    private TextView tv_port_setfinshtime;
    private Calendar calender;
    private int bMonth,fMonth;
    private int bDay,fDay;
    private int bYear,fYear;

    private Spinner sp_class_names,sp_lesson_names;

    private List<GradeTaskDto> data;

    private Integer classIndex,taskIndex;//班级的索引，任务的索引

    private Button btn_download;//下载

    @Override
    public int getContentViewId() {
        Iconify.with(new IconfontModule());
        return R.layout.activity_export_layout;
    }

    @Override
    public void initView() {
        tv_title = findViewById(R.id.tv_title);
        sp_class_names = findViewById(R.id.sp_class_names);
        sp_lesson_names  = findViewById(R.id.sp_lesson_names);
        btn_download = findViewById(R.id.btn_download);
    }

    @Override
    public void initData() {
        calender= Calendar.getInstance();
        bYear = calender.get(Calendar.YEAR);
        bMonth = calender.get(Calendar.MONTH);
        bDay = calender.get(Calendar.DAY_OF_MONTH);
        fYear = calender.get(Calendar.YEAR);
        fMonth = calender.get(Calendar.MONTH);
        fDay = calender.get(Calendar.DAY_OF_MONTH);

    }

    @Override
    protected void afterInit() {
        super.afterInit();
        iPresenter.getGradeTasks();
    }

    @Override
    public void setEvent() {
        tv_title.setBackClickListener(v -> onBackPressed());
        btn_download.setOnClickListener(view -> {

            if(data != null)
            {
                GradeTaskDto dto = data.get(classIndex);
                LogUtil.d(dto);
                iPresenter.getStatisticExcel(dto.getTasks().get(taskIndex).getId());
            }
        });
    }

    @Override
    protected IExportPresenter createIPresenter() {
        return new IExportPresenterImpl();
    }
    //开始时间对话框
    private  View.OnClickListener  tvbegintimeListner=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            new MyDatePickerDialog(getActivity(), begintimetListener, bYear, bMonth, bDay).show();
        }
    };
    private MyDatePickerDialog.OnDateSetListener begintimetListener = new MyDatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            bYear = year;
            bMonth = monthOfYear;
            bDay = dayOfMonth;
            String days;
            if (bMonth + 1 < 10) {
                if (bDay < 10) {
                    days = new StringBuffer().append(bYear).append("-").append("").
                            append(bMonth + 1).append("-").append("").append(bDay).append("").toString();
                } else {
                    days = new StringBuffer().append(bYear).append("-").append("").
                            append(bMonth + 1).append("-").append(bDay).append("").toString();
                }

            } else {
                if (bDay < 10) {
                    days = new StringBuffer().append(bYear).append("-").
                            append(bMonth + 1).append("-").append("").append(bDay).append("").toString();
                } else {
                    days = new StringBuffer().append(bYear).append("-").
                            append(bMonth + 1).append("-").append(bDay).append("").toString();
                }

            }
            tv_port_setbegintime.setText(days);
            LogUtil.e("timepicker回调");

        }
    };
    //结束时间对话框
    private  View.OnClickListener  tvfinshtimeListner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new MyDatePickerDialog(getActivity(), finishtimetListener, fYear, fMonth, fDay).show();
        }
    };
    private MyDatePickerDialog.OnDateSetListener finishtimetListener = new MyDatePickerDialog
            .OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            fYear = year;
            fMonth = monthOfYear;
            fDay = dayOfMonth;
            String days;
            if (fMonth + 1 < 10) {
                if (fDay < 10) {
                    days = new StringBuffer().append(fYear).append("-").append("").
                            append(fMonth + 1).append("-").append("").append(fDay).append("").toString();
                } else {
                    days = new StringBuffer().append(fYear).append("-").append("").
                            append(fMonth + 1).append("-").append(fDay).append("").toString();
                }

            } else {
                if (fDay < 10) {
                    days = new StringBuffer().append(fYear).append("-").
                            append(fMonth + 1).append("-").append("").append(fDay).append("").toString();
                } else {
                    days = new StringBuffer().append(fYear).append("-").
                            append(fMonth + 1).append("-").append(fDay).append("").toString();
                }
            }
            tv_port_setfinshtime.setText(days);


        }
    };

    @Override
    public void showSpinner(List<GradeTaskDto> gradeTaskDtos) {
        data = gradeTaskDtos;
        if(data != null && !data.isEmpty())
        {
            String[] arr = new String[data.size()];
            int i = 0;
            for(GradeTaskDto dto:data)
            {
                arr[i] = dto.getGradeName();
                i++;
            }

            classIndex = 0;

            initSpLesson(0);

            sp_class_names.setAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, arr));
            sp_class_names.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    initSpLesson(i);
                    classIndex = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    classIndex = null;
                }
            });
        }
    }

    private void initSpLesson(int index)
    {
        if(data.size()<=index)
            return;
        List<GTaskDto> taskDtos = data.get(index).getTasks();
        String[] lessons = new String[taskDtos.size()];
        int i=0;
        for(GTaskDto dto:taskDtos)
        {
            lessons[i]=dto.getName();
            i++;
        }

        sp_lesson_names.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, lessons));
        taskIndex = 0;
        sp_lesson_names.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                taskIndex = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                taskIndex = null;
            }
        });
    }

    @Override
    public void showError(String error) {
        ToastUtil.showShort(error);
    }

    @Override
    public void downloadSuccess(File file) {
        ToastUtil.showLong("文件已保存在:"+file.getAbsolutePath());
    }

    @Override
    public void downloadFail(String error) {
        ToastUtil.showShort(error);
    }

    @Override
    public void onProgress(int progress) {

    }
}
