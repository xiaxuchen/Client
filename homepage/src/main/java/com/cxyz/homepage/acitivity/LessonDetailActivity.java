package com.cxyz.homepage.acitivity;

import android.content.Intent;
import android.os.Environment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxyz.commons.Adapter.AdapterBase;
import com.cxyz.commons.Adapter.ViewHolder;
import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.date.Date;
import com.cxyz.commons.date.DateTime;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.TitleView;
import com.cxyz.homepage.R;
import com.cxyz.homepage.adapter.HistoryAdapter;
import com.cxyz.homepage.constant.NetWorkHomeUrl;
import com.cxyz.homepage.dto.CheckHistoryDto;
import com.cxyz.homepage.dto.LessonDto;
import com.cxyz.homepage.ipresenter.ILessonDetailPresenter;
import com.cxyz.homepage.ipresenter.impl.ILessonDetailPresenterImpl;
import com.cxyz.homepage.iview.ILessonDetailView;
import com.cxyz.logiccommons.domain.Audit;
import com.cxyz.logiccommons.domain.User;
import com.cxyz.logiccommons.manager.UserManager;
import com.cxyz.logiccommons.service.DownloadService;
import com.cxyz.logiccommons.typevalue.CheckRecordResult;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2019/2/3.
 */

public class LessonDetailActivity extends BaseActivity<ILessonDetailPresenter> implements ILessonDetailView {

    private TitleView tv_title;

    private TextView tv_checker;

    private TextView tv_num;

    private TextView tv_room;

    private TextView tv_edit_room;

    private TextView tv_edit_num;

    private QMUIEmptyView ev_check;

    private ListView lv_check;

    private LessonDto lessonDto;

    private int current;

    private HistoryAdapter adapter;

    private SmartRefreshLayout srl_refrash;

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
        lessonDto = (LessonDto) intent.getSerializableExtra("lessonDto");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_lesson_detail_layout;
    }

    @Override
    public void initView() {
        tv_checker = findViewById(R.id.tv_checker);
        tv_num = findViewById(R.id.tv_num);
        tv_room = findViewById(R.id.tv_room);
        tv_edit_num = findViewById(R.id.tv_edit_num);
        tv_edit_room = findViewById(R.id.tv_edit_room);
        ev_check = findViewById(R.id.ev_check);
        lv_check = findViewById(R.id.lv_check);
        srl_refrash = findViewById(R.id.srl_refrash);
        tv_title = findViewById(R.id.tv_title);

        if(lessonDto == null)
            return;
        tv_checker.setText(lessonDto.getCheckerName());
        if(!(lessonDto.getNum() == null || lessonDto.getNum().isEmpty()))
        tv_num.setText(lessonDto.getNum());
        if(!(lessonDto.getRoomName() == null || lessonDto.getRoomName().isEmpty()))
            tv_room.setText(lessonDto.getRoomName());
        lv_check.setAdapter(adapter);
        tv_title.setTitle(lessonDto.getName());
    }

    @Override
    public void initData() {
        adapter = new HistoryAdapter(getActivity(),new ArrayList<>(),R.layout.item_history_layout);
    }

    @Override
    public void setEvent() {
        tv_title.setBackClickListener(v -> onBackPressed());
        tv_edit_num.setOnClickListener(view -> {
            QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
            String txt = tv_num.getText().toString().trim();
            builder.setInputType(InputType.TYPE_CLASS_TEXT)
                    .setPlaceholder("请输入课程编号")
                    .setDefaultText(txt.equals("暂无")?"":txt)
                    .setCancelable(false)
                    .addAction("取消",(dialog, index) -> dialog.dismiss())
                    .addAction("确定",(dialog, index) -> {
                       String curTxt = builder.getEditText().getText().toString().trim();
                       if(!txt.equals(curTxt))
                           iPresenter.updateLessonNum(lessonDto.getId(),curTxt);
                        dialog.dismiss();
                    }).show();
        });
        srl_refrash.setOnRefreshListener(refreshLayout -> {
           iPresenter.getLessonHistories(lessonDto.getId(),0);
        });
        srl_refrash.setOnLoadMoreListener(refreshLayout -> {
            iPresenter.getLessonHistories(lessonDto.getId(),current);
        });
        tv_title.setMoreClickListener(v -> {
            LogUtil.e("...");
            HashMap<String,String> map = new HashMap<>();
            User u = UserManager.getInstance().getUser();
            map.put("sponsorId",u.getId());
            map.put("sponsorType",u.getType()+"");
            map.put("lessonId",lessonDto.getId()+"");
            DownloadService.execute(NetWorkHomeUrl.GET_STATISTIC_EXCEL,"attachment;filename=statisic.xlsx",
                    Environment.getExternalStorageDirectory().getAbsolutePath()+"/data/statisic.xlsx",map,getActivity());
        });
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        iPresenter.getLessonHistories(lessonDto.getId(),current);
    }

    @Override
    protected ILessonDetailPresenter createIPresenter() {
        return new ILessonDetailPresenterImpl();
    }

    @Override
    public void showHistories(List<CheckHistoryDto> data) {
        if(ev_check.isShowing())
            ev_check.hide();
        adapter.setList(data);
        adapter.notifyDataSetChanged();
        current = adapter.getList().size();
        srl_refrash.finishRefresh();
    }

    @Override
    public void addMoreHistories(List<CheckHistoryDto> data) {
        if(ev_check.isShowing())
            ev_check.hide();
        adapter.appendToList(data);
        adapter.notifyDataSetChanged();
        current = adapter.getList().size();
        srl_refrash.finishRefresh();
    }

    @Override
    public void showError(Object error,boolean loadMore) {
        LogUtil.e(error);
        LogUtil.e(loadMore);
        if(!loadMore)
        {
            ev_check.show(false, "加载失败", error.toString(), null,null);
            srl_refrash.finishRefresh();
        }
        else
            srl_refrash.finishLoadMore(500,false,false);

    }

    @Override
    public void showUpdatedNum(String num) {
        tv_num.setText(num);
        lessonDto.setNum(num);
        LogUtil.d(lessonDto);
        EventBus.getDefault().post(lessonDto);
    }

    @Override
    public void showListLoading() {
        if(!ev_check.isShowing() && current == 0)
            ev_check.setVisibility(View.VISIBLE);
        ev_check.show(true,"正在加载中...",null,null,null);
    }

    @Override
    public void updateNumError(String error) {
        ToastUtil.showShort("更新课程编号失败");
    }

}
