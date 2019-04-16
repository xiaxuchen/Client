package com.cxyz.mains.imodel;

import com.cxyz.commons.IModel.IBaseModel;

/**
 * Created by Administrator on 2019/4/13.
 */

public abstract class IHomeModel extends IBaseModel {

    /**
     * 发送邮件
     * @param email 邮箱
     */
    public abstract void sendMail(String email,String newPwd,ModelListener<Void,String> listener);

    /**
     * 刷新当前激活状态
     */
    public abstract void refreshState(ModelListener<Boolean,String> listener);
}
