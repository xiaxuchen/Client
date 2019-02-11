package com.cxyz.mine.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.cxyz.commons.fragment.BaseFragment;
import com.cxyz.commons.utils.AppUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.logiccommons.service.UpdateService;
import com.cxyz.mine.IPresenter.IMineFragmentPresenter;
import com.cxyz.mine.IPresenter.impl.IMineFragmentPresenterlmpl;
import com.cxyz.mine.R;
import com.cxyz.mine.activity.ChangePwdACtivity;
import com.cxyz.mine.activity.MyinfoActivity;
import com.cxyz.mine.activity.OtherSettingActivity;
import com.cxyz.mine.activity.UserResponse;
import com.cxyz.mine.iview.IMineFragementView;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import java.io.File;

/**
 * Created by Administrator on 2018/9/25.
 */
@Route(path = "/mine/MineFragment")
public class MineFragment extends BaseFragment<IMineFragmentPresenter> implements View.OnClickListener ,IMineFragementView {
    private ImageView iv_mine_othersetting;
    private LinearLayout ll_mine_pc;
    private LinearLayout ll_mine_update;
    private LinearLayout ll_mine_resetpwd;
    private LinearLayout ll_mine_about;
    private QMUIRadiusImageView iv_mine_pic;
    private RelativeLayout ll_mine_info;
    private Dialog dialog;
    private ProgressBar pb_pro;
    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.minelayoutmore;
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        iv_mine_othersetting=findViewById(R.id.iv_mine_othersetting);
        ll_mine_pc=findViewById(R.id.ll_mine_pc);
        ll_mine_update=findViewById(R.id.ll_mine_update);
        ll_mine_resetpwd=findViewById(R.id.ll_mine_resetpwd);
        ll_mine_about=findViewById(R.id.ll_mine_about);
        iv_mine_pic=findViewById(R.id.iv_mine_pic);
        ll_mine_info=findViewById(R.id.ll_mine_info);
        iv_mine_pic.setImageResource(R.mipmap.belief);
        iv_mine_pic.setCircle(true);
    }

    @Override
    protected IMineFragmentPresenter createIPresenter() {
        return  new IMineFragmentPresenterlmpl();
    }

    @Override
    protected void setListener() {
        ll_mine_info.setOnClickListener(this);
        ll_mine_update.setOnClickListener(this);
        iv_mine_othersetting.setOnClickListener(this);
    }

    //监听事件

    //界面跳转
    //从我的界面跳转到设置界面
    public void iv_mine_othersetting() {
        Intent intent = new Intent(getActivity().getApplicationContext(), OtherSettingActivity.class);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.enter_next, R.anim.enter_exit);

    }

    //从我的界面跳转到个人信息界面
    public void ll_mine_info() {
        Intent intent = new Intent(getActivity().getApplicationContext(), MyinfoActivity.class);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.enter_next, R.anim.enter_exit);
    }
    //从我的界面跳转到修改密码界面
    public void ll_mine_resetpwd() {
        Intent intent = new Intent(getActivity().getApplicationContext(), ChangePwdACtivity.class);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.enter_next, R.anim.enter_exit);
    }
    //从我的界面跳转到用户反馈界面
    public void tv_mine_useradvice() {
        Intent intent = new Intent(getActivity().getApplicationContext(), UserResponse.class);
        startActivity(intent);
        mActivity.overridePendingTransition(R.anim.enter_next, R.anim.enter_exit);
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.ll_mine_info)
            ll_mine_info();
        else if(viewId == R.id.iv_mine_othersetting)
            iv_mine_othersetting();
        else  if (viewId==R.id.ll_mine_update){
            ll_mine_update();
        }
    }
    public void ll_mine_update(){
        iPresenter.Update();

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