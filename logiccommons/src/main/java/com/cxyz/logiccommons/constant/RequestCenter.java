package com.cxyz.logiccommons.constant;

import android.accounts.NetworkErrorException;

import com.cxyz.commons.utils.HttpUtil.CommonOkHttpClient;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataHandler;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataListener;
import com.cxyz.commons.utils.HttpUtil.request.RequestParams;
import com.cxyz.logiccommons.domain.CheckResult;
import com.cxyz.logiccommons.dto.PushDto;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2019/2/1.
 */

public class RequestCenter {

    /**
     * 获取推送信息
     * @param id 用户id
     * @param type 用户类型
     * @param listener
     * @return
     */
    public static Call getPushes(String id, Integer type, DisposeDataListener listener)
    {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",id);
        hashMap.put("type",type+"");
        try {
            return CommonOkHttpClient.get(Constant.GET_PUSHES,new RequestParams
                    (hashMap),new DisposeDataHandler(listener,new TypeToken<CheckResult<List<PushDto>>>(){}.getType()));
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 推送成功
     * @param id 推送id
     * @param listener
     * @return
     */
    public static Call pushSuccess(Integer id,DisposeDataListener listener)
    {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("id",id+"");
        try {
            return CommonOkHttpClient.get(Constant.PUSH_SUCCESS,new RequestParams(hashMap),new DisposeDataHandler(listener));
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
        return null;
    }
}
