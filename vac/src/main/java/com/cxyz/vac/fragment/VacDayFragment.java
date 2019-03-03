package com.cxyz.vac.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.commons.date.Date;
import com.cxyz.commons.fragment.BaseFragment;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.datetime.DateTimeDialog;
import com.cxyz.logiccommons.domain.Vacate;
import com.cxyz.logiccommons.manager.UserManager;
import com.cxyz.vac.R;
import com.cxyz.vac.adapter.VacInfoAdapter;
import com.cxyz.vac.icon.IconfontModule;
import com.cxyz.vac.ipresenter.IVacDayPresenter;
import com.cxyz.vac.ipresenter.impl.IVacDayPresenterImpl;
import com.cxyz.vac.iview.IVacDayView;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/25.
 */

public class VacDayFragment extends BaseFragment<IVacDayPresenter> implements IVacDayView {

    private TextView itv_date;

    private Date date;//记录当前日期

    private ListView lv_vac_info;

    private VacInfoAdapter adapter;

    private Integer gradeId = UserManager.getInstance().getUser().getGradeId();

    public static VacDayFragment newInstance() {
        return new VacDayFragment();
    }

    @Override
    protected int getLayoutId() {
        Iconify.with(new IconfontModule());
        return R.layout.fragment_vac_day_layout;
    }

    @Override
    protected void initData(Bundle bundle) {
        date = Date.fromUDate(new java.util.Date());
        adapter = new VacInfoAdapter(getActivity(),new ArrayList());
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        itv_date = findViewById(R.id.itv_date);
        lv_vac_info = findViewById(R.id.lv_vac_info);

        lv_vac_info.setAdapter(adapter);
        lv_vac_info.setEmptyView(findViewById(R.id.tv_empty));
        itv_date.setText(date.getDate());
    }

    @Override
    protected IVacDayPresenter createIPresenter() {
        return new IVacDayPresenterImpl();
    }

    @Override
    protected void setListener() {
        getParent(itv_date).setOnClickListener(view -> {
            new DateTimeDialog.DateBuilder(this.getActivity()).setDate(date).setOnDateSelectListener((dialog, year, month, day) -> {
                date.setYear(year);
                date.setMonth(month);
                date.setDay(day);
                itv_date.setText(date.getDate());
                if( gradeId != null)
                    iPresenter.getVacatesInDates(gradeId,date.getDate(),date.getDate());
                dialog.cancel();
            }).build().show();
        });
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        if( gradeId != null)
            iPresenter.getVacatesInDates(gradeId,date.getDate(),date.getDate());
    }

    @Override
    public void showVacates(List<Vacate> vacates) {
        adapter.setList(vacates);
    }

    @Override
    public void showError(String error) {
        ToastUtil.showShort(error);
    }
}
