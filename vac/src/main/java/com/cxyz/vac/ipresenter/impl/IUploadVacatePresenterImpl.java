package com.cxyz.vac.ipresenter.impl;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.vac.dto.VacateDto;
import com.cxyz.vac.imodel.IUploadVacateModel;
import com.cxyz.vac.imodel.impl.IUploadVacateModelImpl;
import com.cxyz.vac.ipresenter.IUploadVacatePresenter;

import java.io.File;

/**
 * Created by Administrator on 2019/2/15.
 */

public class IUploadVacatePresenterImpl extends IUploadVacatePresenter {
    @Override
    public void uploadVacate(VacateDto dto, File[] files) {
        mIView.showLoadingView();
        mIModle.uploadVacate(dto, files, new IBaseModel.ModelListener<String, String>() {
            @Override
            public void onSuccess(String data) {
                mIView.uploadSuccess();
                mIView.hideLoadingView();
            }

            @Override
            public void onFail(String s) {
                mIView.uploadFail(s);
                mIView.hideLoadingView();
            }
        });
    }

    @Override
    public IUploadVacateModel createModel() {
        return new IUploadVacateModelImpl();
    }
}
