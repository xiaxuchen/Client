package com.cxyz.mine.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.manager.ActivityStackManager;
import com.cxyz.commons.utils.AppUtil;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.TitleView;
import com.cxyz.logiccommons.manager.UserManager;
import com.cxyz.logiccommons.service.UpdateService;
import com.cxyz.mine.IPresenter.IMineFragmentPresenter;
import com.cxyz.mine.IPresenter.impl.IMineFragmentPresenterlmpl;
import com.cxyz.mine.R;
import com.cxyz.mine.iview.IMineFragementView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.io.File;

/**
 * Created by Administrator on 2019/2/3.
 */

public class OtherSettingActivity extends BaseActivity<IMineFragmentPresenter> implements IMineFragementView{
    private QMUIRoundButton bt_email_confirm;
    private QMUIRoundButton bt_email_getcode;
    private Dialog mDialog;
    private TitleView tv_othersetting_title;
    private LinearLayout ll_othersetting_checkupdate;
    private LinearLayout ll_othersetting_userresp;
    private LinearLayout ll_othersetting_update;
    private QMUIRoundButton bt_othersetting_exit;
    private Switch sw_update;
    private Dialog dialog;
    private ProgressBar pb_pro;
    @Override
    public int getContentViewId() {
        return R.layout.activity_othersetting_layout;
    }

    @Override
    public void initView() {
        tv_othersetting_title = findViewById(R.id.tv_othersetting_title);
        tv_othersetting_title.setTitle("更多设置");
        ll_othersetting_checkupdate = findViewById(R.id.ll_othersetting_checkupdate);
        ll_othersetting_userresp = findViewById(R.id.ll_othersetting_userresp);
        ll_othersetting_update = findViewById(R.id.ll_othersetting_update);
        bt_othersetting_exit = findViewById(R.id.bt_othersetting_exit);
        sw_update = findViewById(R.id.sw_update);
        sw_update.setChecked(getSpUtil().getBoolean("update", true));
    }

    @Override
    public void initData() {

    }

    @Override
    public void setEvent() {
        tv_othersetting_title.setBackClickListener(new TitleView.OnBackClickListener() {
            @Override
            public void onBackClick(View v) {
                onBackPressed();
            }
        });
        bt_othersetting_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到考勤界面
                ARouter.getInstance().build("/main/LoginActivity").navigation();
                UserManager.getInstance().setUser(null);
                getSpUtil().remove("pwd");
                ActivityStackManager.getActivityStackManager().popAllActivity();
            }
        });
        sw_update.setOnCheckedChangeListener((compoundButton, b) -> getSpUtil().putBoolean("update", b));
        ll_othersetting_checkupdate.setOnClickListener(v -> iPresenter.Update());
    }


    @Override
    protected IMineFragmentPresenter createIPresenter() {
        return new IMineFragmentPresenterlmpl();
    }

    @Override
    public void showUpdateView(int versionCode, String des, final String url) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setIcon(R.mipmap.common_logo);
        builder.setTitle("发现新版本"+versionCode);
        builder.setMessage(des);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(),UpdateService.class);
                intent.putExtra("apkUrl",url);
                getActivity().startService(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void showDownload(int progress, int max) {
        if(pb_pro != null)
            pb_pro.setVisibility(View.VISIBLE);
    }


    @Override
    public void installApp(File app) {
        AppUtil.installApk(getActivity(),app.getAbsolutePath());
    }

    @Override
    public void noUpdate() {
        ToastUtil.showShort("已是最新版本");
    }
}
