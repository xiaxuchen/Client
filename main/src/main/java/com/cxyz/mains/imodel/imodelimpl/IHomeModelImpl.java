package com.cxyz.mains.imodel.imodelimpl;


import com.cxyz.commons.utils.HttpUtil.CommonOkHttpClient;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataHandler;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataListener;
import com.cxyz.commons.utils.HttpUtil.request.RequestParams;
import com.cxyz.logiccommons.domain.CheckResult;
import com.cxyz.logiccommons.domain.User;
import com.cxyz.logiccommons.manager.UserManager;
import com.cxyz.mains.constant.NetWorkConstant;
import com.cxyz.mains.imodel.IHomeModel;
import com.google.gson.reflect.TypeToken;


/**
 * Created by Administrator on 2019/4/14.
 */

public class IHomeModelImpl extends IHomeModel {
    @Override
    public void sendMail(String email,String newPwd, ModelListener<Void, String> listener) {
        User user = UserManager.getInstance().getUser();
        RequestParams params = new RequestParams()
                .put("mail",email)
                .put("newPwd",newPwd)
                .put("id",user.getId())
                .put("type",user.getType().toString());
        addCall(CommonOkHttpClient.call(CommonOkHttpClient.Method.POST,NetWorkConstant.SEND_MAIL,params,new DisposeDataHandler(new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    CheckResult cr = (CheckResult) responseObj;
                    if(cr.isSuccess())
                        listener.onSuccess(null);
                    else
                        onFailure("邮件发送失败");

                }

                @Override
                public void onFailure(Object error) {
                    listener.onFail(handleFail(error));
                }
            },new TypeToken<CheckResult>(){}.getType())));
    }

    @Override
    public void refreshState(ModelListener<Boolean, String> listener) {
        addCall(CommonOkHttpClient.call(CommonOkHttpClient.Method.GET,NetWorkConstant.REFRESH,null,
                new DisposeDataHandler(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        CheckResult<Boolean> cr = (CheckResult<Boolean>) responseObj;
                        if(cr.isSuccess())
                            listener.onSuccess(cr.isSuccess());
                        else
                            onFailure(cr.getError());
                    }

                    @Override
                    public void onFailure(Object error) {
                        listener.onFail(handleFail(error));
                    }
                },new TypeToken<CheckResult<Boolean>>(){}.getType())));
    }
}
