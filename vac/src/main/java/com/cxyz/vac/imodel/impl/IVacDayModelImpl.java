package com.cxyz.vac.imodel.impl;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataListener;
import com.cxyz.logiccommons.domain.CheckResult;
import com.cxyz.logiccommons.domain.Vacate;
import com.cxyz.vac.constant.RequestCenter;
import com.cxyz.vac.imodel.IVacDayModel;

import java.util.List;

/**
 * Created by Administrator on 2019/2/26.
 */

public class IVacDayModelImpl extends IVacDayModel {
    @Override
    public void getVacatesInDates(Integer gradeId, String from, String to, ModelListener<List<Vacate>, String> listener) {
        addCall(RequestCenter.getVacatesInDates(gradeId, from, to, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                CheckResult<List<Vacate>> cr = (CheckResult<List<Vacate>>) responseObj;
                if(cr.isSuccess())
                    listener.onSuccess(cr.getData());
                else
                    listener.onFail(cr.getError());
            }

            @Override
            public void onFailure(Object error) {
                if(error instanceof Exception)
                    listener.onFail("发生异常");
                else
                    listener.onFail(error.toString());
            }
        }));
    }
}
