package com.cxyz.vac.imodel.impl;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataListener;
import com.cxyz.logiccommons.domain.CheckResult;
import com.cxyz.vac.constant.RequestCenter;
import com.cxyz.vac.dto.VacateDto;
import com.cxyz.vac.imodel.IUploadVacateModel;

import java.io.File;

/**
 * Created by Administrator on 2019/2/15.
 */

public class IUploadVacateModelImpl extends IUploadVacateModel {
    @Override
    public void uploadVacate(VacateDto vacateDto, File[] files, ModelListener<String, String> listener) {
        addCall(RequestCenter.uploadVacate(vacateDto, files, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                CheckResult<String> cr = (CheckResult<String>) responseObj;
                if(cr.isSuccess())
                    listener.onSuccess(cr.getData());
                else
                    listener.onFail(cr.getError());
            }

            @Override
            public void onFailure(Object error) {
                if(error instanceof Exception)
                    listener.onFail(((Exception) error).getMessage());
                else
                    listener.onFail(error.toString());
            }
        }));
    }
}
