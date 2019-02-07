package com.cxyz.logiccommons.manager;


import android.content.Intent;

import com.cxyz.commons.context.ContextManager;
import com.cxyz.logiccommons.domain.User;
import com.cxyz.logiccommons.service.PushService;
import com.cxyz.logiccommons.typevalue.PowerType;
import com.cxyz.logiccommons.typevalue.UserType;


/**
 * Created by 夏旭晨 on 2018/10/5.
 * 用户信息管理类
 */
public class UserManager {


    public User getUser() {
        return u;
    }

    public void setUser(User user) {
        this.u = user;
        if(u!=null)
        {
            Intent intent = new Intent(ContextManager.getContext(), PushService.class);
            ContextManager.getContext().startService(intent);
            //JPushInterface.setAlias(ContextManager.getContext(),SET_ALIAS,u.getId());
        }
        else
        {
            Intent intent = new Intent(ContextManager.getContext(), PushService.class);
            ContextManager.getContext().stopService(intent);
            //JPushInterface.setAlias(ContextManager.getContext(),SET_ALIAS,"");
        }
    }

    /**
     * 登出
     */
    public void logout()
    {
        setUser(null);
    }

    /**
     * 是否已登录
     * @return
     */
    public boolean isLogined()
    {
        return getUser() != null;
    }

    private User u=getFakeUser();

    private static UserManager userManager;

    public static UserManager getInstance()
    {
        if(userManager == null)
            userManager = new UserManager();
        return userManager;
    }

    /**
     * TODO 正式上线去除
     * 测试专用方法
     * @return
     */
    private User getFakeUser()
    {
        User u = new User();
        u.setGradeId(1702);
        u.setName("夏旭晨");
        u.setId("16478040");
        u.setGradeName("17软工二班");
        u.setCollegeName("信息与计算机工程学院");
        u.setPower(PowerType.TEA_NORMAL);
        u.setType(UserType.TEACHER);
        return u;
    }

}
