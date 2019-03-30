package com.cxyz.mains.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.utils.AppUtil;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.SpUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.logiccommons.manager.UserManager;
import com.cxyz.logiccommons.service.UpdateService;
import com.cxyz.mains.R;
import com.cxyz.mains.ipresenter.ISplashPresenter;
import com.cxyz.mains.ipresenter.ipresenterimpl.ISplashPresenterImpl;
import com.cxyz.mains.iview.ISplashView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity<ISplashPresenter> implements ISplashView {

    private static final int STATE_LOGIN = 0;

    private static final int STATE_FIRST_SHOW = 1;

    private static final int STATE_HOME = 2;

    private Timer timer = new Timer();

    private int times = 0;

    private boolean flag = false;//判断是否为第一次启动

    private TextView tv_timer;

    private boolean autoLogined = false;

    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_splash_layout;
    }

    @Override
    public void initView() {
        tv_timer = findViewById(R.id.tv_timer);
    }

    @Override
    public void initData() {
        if(!getSpUtil().getString("versionName","0").equals(AppUtil.getVersionName(getActivity())))
            flag = true;
        LogUtil.e(getSpUtil().getString("versionName","0"));
        LogUtil.e(!getSpUtil().getString("versionName","0").equals(AppUtil.getVersionName(getActivity())));
        if(flag)
            state = STATE_FIRST_SHOW;
        else
            state = STATE_LOGIN;
    }

    @Override
    public void setEvent() {
        tv_timer.setOnClickListener(view -> {
            if(!autoLogined)
            {
                afterLogin();
                timer.cancel();
            }
        });
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        iPresenter.autoLogin();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    times++;
                    if(times == 3)
                    {
                        afterLogin();
                        return;
                    }
                    tv_timer.setText(3-times+"s 跳过");
                });
            }
        },1000,1000);
        //初始化完成后根据sp中的update值选择更新或自动登录
        //iPresenter.autoLogin();
    }

    @Override
    protected ISplashPresenter createIPresenter() {
        return new ISplashPresenterImpl();
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    public void autoLoginSuccess() {
        if(flag)
            state = STATE_FIRST_SHOW;
        else
            state = STATE_HOME;
    }

    @Override
    public void autoLoginFail(final String info) {
        if(flag)
            state = STATE_FIRST_SHOW;
        else
            state = STATE_LOGIN;
    }

    /**
     * 登录之后（如果登录超时也会调用）
     */
    private void afterLogin()
    {
        autoLogined = true;
        timer.cancel();
        Class clazz = null;
        switch (state)
        {
            case STATE_FIRST_SHOW:clazz = FirstShowActivity.class;break;
            case STATE_LOGIN:clazz = LoginActivity.class;break;
            case STATE_HOME:clazz = HomeActivity.class;break;
        }
        LogUtil.e(state);
        Intent intent = new Intent(getActivity(),clazz);
        startActivity(intent);
        finish();
    }
}
