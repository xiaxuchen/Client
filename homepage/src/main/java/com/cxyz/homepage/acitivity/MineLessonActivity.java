package com.cxyz.homepage.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cxyz.commons.Adapter.AdapterBase;
import com.cxyz.commons.Adapter.ViewHolder;
import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.TitleView;
import com.cxyz.homepage.R;
import com.cxyz.homepage.dto.GTaskDto;
import com.cxyz.homepage.dto.GradeLessonDto;
import com.cxyz.homepage.dto.GradeTaskDto;
import com.cxyz.homepage.dto.LessonDto;
import com.cxyz.homepage.ipresenter.IExportPresenter;
import com.cxyz.homepage.ipresenter.IMineLessonPresenter;
import com.cxyz.homepage.ipresenter.impl.IExportPresenterImpl;
import com.cxyz.homepage.ipresenter.impl.IMineLessonPresenterImpl;
import com.cxyz.homepage.view.IMineLessonView;
import com.qmuiteam.qmui.widget.QMUIEmptyView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 2019/2/2.
 */

public class MineLessonActivity extends BaseActivity<IMineLessonPresenter> implements IMineLessonView {

    private TitleView tv_title;

    private ListView lv_grades;

    private ListView lv_lessons;

    private QMUIEmptyView ev_lessons;

    private int selectedIndex = 0;

    private AdapterBase<LessonDto> lessonAdapter;

    @Override
    protected boolean eventBusEnable() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateLessonDto(LessonDto dto)
    {
        LogUtil.d(dto);
        final List<LessonDto> list = lessonAdapter.getList();
        LogUtil.d(list);
        int i = 0;
        for(LessonDto dto1:list)
        {
            if(dto.getId().equals(dto1.getId()))
            {
                list.set(i,dto);
                LogUtil.d(i);
                break;
            }
            i++;
        }
        LogUtil.d(list);
        lessonAdapter.notifyDataSetChanged();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_mine_lessons_layout;
    }

    @Override
    public void initView() {
        tv_title = findViewById(R.id.tv_title);
        lv_grades = findViewById(R.id.lv_grades);
        lv_lessons = findViewById(R.id.lv_lessons);
        ev_lessons = findViewById(R.id.ev_lessons);
    }

    @Override
    public void initData() {
    }

    @Override
    public void setEvent() {
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        iPresenter.getGradeLessons();
    }

    @Override
    protected IMineLessonPresenter createIPresenter() {
        return new IMineLessonPresenterImpl();
    }

    @Override
    public void showGradeLessons(List<GradeLessonDto> data) {
        if(data == null || data.isEmpty())
        {
            ev_lessons.show(false,"当前暂无课程信息",null,"重新加载",view ->
            {
                iPresenter.getGradeLessons();
            });
            return;
        }
        ev_lessons.hide();
        lv_grades.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int i) {
                return data.get(i);
            }

            @Override
            public long getItemId(int i) {
                return (long)data.get(i).getGradeId();
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView tv_grade = new TextView(getActivity());
                tv_grade.setText(data.get(i).getGradeName());
                tv_grade.setGravity(Gravity.CENTER);
                tv_grade.setPadding(0,24,0,24);
                tv_grade.setTextSize(16);
                if(selectedIndex == i)
                {
                    tv_grade.setTextColor(getResources().getColor(R.color.common_primary_color));
                    tv_grade.setBackgroundColor(getResources().getColor(R.color.common_white));
                }
                else
                {
                    tv_grade.setTextColor(getResources().getColor(R.color.common_line));
                    tv_grade.setBackgroundColor(getResources().getColor(R.color.float_transparent));
                }
                return tv_grade;
            }
        });

        lessonAdapter = new AdapterBase<LessonDto>(getActivity(),data.get(selectedIndex).getLessons(),R.layout.item_lesson_layout) {

            @Override
            public long getItemId(int position) {
                return getItem(position).getId();
            }

            @Override
            public void convertView(ViewHolder holder, LessonDto item) {
                super.convertView(holder, item);
                holder.setText(R.id.tv_lesson,item.getName());
                if(item.getRoomName() != null)
                    holder.setText(R.id.tv_room,item.getRoomName());
                if(item.getNum() != null)
                    holder.setText(R.id.tv_num,item.getNum());
                holder.setOnClickListener(R.id.tv_more,view ->
                {
                    Intent intent = new Intent(getActivity(),LessonDetailActivity.class);
                    intent.putExtra("lessonDto",item);
                    startActivity(intent);
                });
            }
        };

        lv_lessons.setAdapter(lessonAdapter);

        lv_grades.setOnItemClickListener((adapterView, view, i, l) -> {
            lessonAdapter.setList(data.get(i).getLessons());
            selectedIndex = i;
            ((BaseAdapter)lv_grades.getAdapter()).notifyDataSetChanged();
        });

    }

    @Override
    public void showError(Object error) {
        if(error instanceof String)
        {
            ev_lessons.show(false,"当前暂无课程信息",null,"重新加载",view ->
            {
                iPresenter.getGradeLessons();
            });
        }
    }

    @Override
    public void showLoadingView() {
        super.showLoadingView();
        ev_lessons.show(true,"正在加载课程信息",null,null,null);
    }

}
