package com.cxyz.mains.constant;

import com.cxyz.logiccommons.constant.Constant;

/**
 * Created by 夏旭晨 on 2018/9/30.
 */

public interface NetWorkConstant {

    String ROOT_URL = "http:/119.29.101.171:8080/Server_Check";

    //用户登录
    String LOGIN_URL = Constant.ROOT_URL+"/user/login";

    //获取app最新版本信息
    String UPDATE_URL = Constant.ROOT_URL+"/resource/updateApp";

    //下载apk文件
    String GETAPP = Constant.ROOT_URL+"/resource/getApp";

    //发送邮件
    String SEND_MAIL = Constant.ROOT_URL+"/user/activeAccountPre";

    String REFRESH = Constant.ROOT_URL+"/user/refresh";


}
