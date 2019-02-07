package com.cxyz.mains.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.utils.AppUtil;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.SpUtil;
import com.cxyz.commons.utils.ToastUtil;
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


    private Timer timer = new Timer();

    private int times = 0;

    private TextView tv_timer;

    private boolean autoLogined = false;

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
    }

    @Override
    public void setEvent() {
        tv_timer.setOnClickListener(view -> {
            if(!autoLogined)
            {
                iPresenter.autoLogin();
                timer.cancel();
            }
        });
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    times++;
                    if(times == 3)
                    {
                        iPresenter.autoLogin();
                        autoLogined = true;
                        timer.cancel();
                    }
                    tv_timer.setText(3-times+"s 跳过");
                });
            }
        },0,1000);
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
        LogUtil.e(System.currentTimeMillis()+"");
        startActivity(HomeActivity.class);
        finish();
    }

    @Override
    public void autoLoginFail(final String info) {
        if(SpUtil.getInstance().getBoolean("isFirst",true) && !SpUtil.getInstance().getString("versionName","0").equals(AppUtil.getVersionName(getActivity())))
            startActivity(FirstShowActivity.class);
        else
            startActivity(LoginActivity.class);
        finish();
    }

}
