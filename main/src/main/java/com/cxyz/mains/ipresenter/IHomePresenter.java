package com.cxyz.mains.ipresenter;

import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.commons.IView.ICommonView;
import com.cxyz.mains.imodel.IHomeModel;

/**
 * Created by Administrator on 2019/4/13.
 */

public abstract class IHomePresenter extends IBasePresenter<IHomeModel,ICommonView> {

    public static final int SEND_MAIL = 0;//发送邮件

    public static final int REFRESH = 1;//刷新
    /**
     * 发送激活邮件
     * @param email 邮箱
     */
    public abstract void sendMail(String email,String newPwd);

    /**
     * 刷新当前状态
     */
    public abstract void refreshState();
}
