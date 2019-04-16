package com.cxyz.vac.ipresenter.impl;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.logiccommons.domain.Vacate;
import com.cxyz.vac.imodel.IVacDayModel;
import com.cxyz.vac.imodel.impl.IVacDayModelImpl;
import com.cxyz.vac.ipresenter.IVacDayPresenter;

import java.util.List;

/**
 * Created by Administrator on 2019/2/26.
 */

public class IVacDayPresenterImpl extends IVacDayPresenter {
    @Override
    public void getVacatesInDates(Integer gradeId, String from, String to) {
        mIView.showLoadingView();
        mIModle.getVacatesInDates(gradeId, from, to, new IBaseModel.ModelListener<List<Vacate>, String>() {
            @Override
            public void onSuccess(List<Vacate> data) {
                mIView.showVacates(data);
                mIView.hideLoadingView();
            }

            @Override
            public void onFail(String s) {
                mIView.showError(s);
                mIView.hideLoadingView();
            }
        });
    }

    @Override
    public IVacDayModel createModel() {
        return new IVacDayModelImpl();
    }
}
