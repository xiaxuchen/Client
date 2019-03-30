package com.cxyz.logiccommons.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataListener;
import com.cxyz.logiccommons.constant.RequestCenter;

/**
 * Created by Administrator on 2019/2/1.
 */

public class PushSuccessService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RequestCenter.pushSuccess(intent.getIntExtra("id", -1), new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

            }

            @Override
            public void onFailure(Object error) {

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
