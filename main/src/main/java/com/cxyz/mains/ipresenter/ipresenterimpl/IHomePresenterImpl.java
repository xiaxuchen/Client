package com.cxyz.mains.ipresenter.ipresenterimpl;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.mains.imodel.IHomeModel;
import com.cxyz.mains.imodel.imodelimpl.IHomeModelImpl;
import com.cxyz.mains.ipresenter.IHomePresenter;

/**
 * Created by Administrator on 2019/4/13.
 */

public class IHomePresenterImpl extends IHomePresenter {
    @Override
    public void sendMail(String email,String newPwd) {
        mIView.showLoadingView();
        mIModle.sendMail(email,newPwd, new IBaseModel.ModelListener<Void, String>() {
            @Override
            public void onSuccess(Void data) {
                mIView.onEvent(SEND_MAIL,true);
                mIView.hideLoadingView();
            }

            @Override
            public void onFail(String s) {
                mIView.onEvent(SEND_MAIL,false,s);
                mIView.hideLoadingView();
            }
        });
    }

    @Override
    public void refreshState() {
        mIView.showLoadingView();
        mIModle.refreshState(new IBaseModel.ModelListener<Boolean, String>() {
            @Override
            public void onSuccess(Boolean data) {
                mIView.onEvent(REFRESH,true,data);
                mIView.hideLoadingView();
            }

            @Override
            public void onFail(String s) {
                mIView.onEvent(REFRESH,false,s);
                mIView.hideLoadingView();
            }
        });
    }

    @Override
    public IHomeModel createModel() {
        return new IHomeModelImpl();
    }
}
