package com.cxyz.mains.iview;

import com.cxyz.commons.IView.IBaseView;

import java.io.File;

/**
 * Created by 夏旭晨 on 2018/10/2.
 */

public interface ISplashView extends IBaseView {

    /**
     * 显示自动登录成功
     */
    public void autoLoginSuccess();

    /**
     * 显示自动登录失败
     */
    public void autoLoginFail(String info);
}
