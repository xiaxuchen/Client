package com.cxyz.logiccommons.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.UserManager;
import android.support.annotation.Nullable;

import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataListener;
import com.cxyz.logiccommons.constant.RequestCenter;
import com.cxyz.logiccommons.domain.CheckResult;
import com.cxyz.logiccommons.domain.User;
import com.cxyz.logiccommons.dto.PushDto;

import java.util.List;

/**
 * Created by Administrator on 2019/2/1.
 */

public class PushService extends Service {

    public static final String PUSH_ACTION = "com.cxyz.untilchecked.intent.push";

    public static final int MSG = 0x123;//信息

    public static final int DELAY = 1000*60*5;//推迟时间

    private Handler handler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == MSG)
            {
                User u = com.cxyz.logiccommons.manager.UserManager.getInstance().getUser();
                RequestCenter.getPushes(u.getId(), u.getType(), new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        CheckResult<List<PushDto>> cr = (CheckResult<List<PushDto>>) responseObj;
                        if(!cr.isSuccess())
                            return;
                        for(PushDto dto:cr.getData())
                        {
                            Intent i = new Intent();
                            i.setAction(PUSH_ACTION);
                            i.putExtra("info",dto.getInfo());
                            i.putExtra("sendTime",dto.getSendTime());
                            i.putExtra("id",dto.getId());
                            sendBroadcast(i);
                        }

                    }

                    @Override
                    public void onFailure(Object error) {

                    }
                });
                handler.sendEmptyMessageDelayed(MSG,DELAY);
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.sendEmptyMessage(MSG);
        return super.onStartCommand(intent, START_STICKY, startId);
    }
}
