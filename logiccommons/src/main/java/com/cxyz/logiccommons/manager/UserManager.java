package com.cxyz.logiccommons.manager;


import android.content.Intent;

import com.cxyz.commons.context.ContextManager;
import com.cxyz.logiccommons.domain.User;
import com.cxyz.logiccommons.service.PushService;
import com.cxyz.logiccommons.typevalue.PowerType;
import com.cxyz.logiccommons.typevalue.UserType;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;


/**
 * Created by 夏旭晨 on 2018/10/5.
 * 用户信息管理类
 */
public class UserManager {

    private User u = getFakeUser();


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

    public static UserManager getInstance()
    {
        return InnerClass.userManager;
    }

    private static class InnerClass{
        private static UserManager userManager = new UserManager();
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
        u.setId("17478093");
        u.setGradeName("17软工二班");
        u.setCollegeName("信息与计算机工程学院");
        u.setPower(PowerType.STU_CHECKER);
        u.setType(UserType.STUDENT);
        return u;
    }

}
