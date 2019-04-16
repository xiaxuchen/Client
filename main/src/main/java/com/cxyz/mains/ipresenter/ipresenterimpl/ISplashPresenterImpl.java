package com.cxyz.mains.ipresenter.ipresenterimpl;

import com.cxyz.commons.autoupdate.UpdateEntity;
import com.cxyz.commons.context.ContextManager;
import com.cxyz.commons.utils.AppUtil;
import com.cxyz.commons.utils.HttpUtil.exception.OKHttpException;
import com.cxyz.commons.utils.SpUtil;
import com.cxyz.logiccommons.domain.User;
import com.cxyz.logiccommons.manager.UserManager;
import com.cxyz.mains.constant.NetWorkConstant;
import com.cxyz.mains.imodel.ILoginModel;
import com.cxyz.mains.imodel.ISplashModel;
import com.cxyz.mains.imodel.imodelimpl.ILoginModelImpl;
import com.cxyz.mains.imodel.imodelimpl.ISplashModelImpl;
import com.cxyz.mains.ipresenter.ISplashPresenter;

/**
 * Created by 夏旭晨 on 2018/10/2.
 */

public class ISplashPresenterImpl extends ISplashPresenter {

    @Override
    public void autoLogin() {
        //从sp中获取用户信息
        final String username = SpUtil.getInstance().getString("username", "");
        final String pwd = SpUtil.getInstance().getString("pwd", "");
        final int type = SpUtil.getInstance().getInt("type",-2);
        //判断是否在sp中保存完整
        if(username!=""&&pwd!=""&&type!=-2)
        {
            //如果完整则登录
            new ILoginModelImpl().getLoginInfo(username, pwd, type, new ILoginModel.getLoginInfoListener() {
                @Override
                public void getInfoSuccess(User user) {
                    //把用户数据保存到UserManager
                    UserManager.getInstance().setUser(user);
                    if(mIView != null)
                    //显示登录成功
                        mIView.autoLoginSuccess();
                }

                @Override
                public void getInfoFail(Object error) {
                    if(mIView == null)
                        return;
                    if(error instanceof OKHttpException)
                        mIView.autoLoginFail("自动登录异常:"+((OKHttpException) error).getMessage());
                    else if(error instanceof String)
                        mIView.autoLoginFail("自动登录异常:"+error.toString());
                    else
                        mIView.autoLoginFail("");
                }

            });
        }else{
            mIView.autoLoginFail("");
        }
    }

    @Override
    public ISplashModel createModel() {
        return new ISplashModelImpl();
    }
}
