package com.cxyz.mine.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cxyz.commons.fragment.BaseFragment;
import com.cxyz.commons.utils.AppUtil;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.logiccommons.domain.User;
import com.cxyz.logiccommons.manager.UserManager;
import com.cxyz.logiccommons.service.UpdateService;
import com.cxyz.mine.IPresenter.IMineFragmentPresenter;
import com.cxyz.mine.IPresenter.impl.IMineFragmentPresenterlmpl;
import com.cxyz.mine.R;
import com.cxyz.mine.activity.ChangePwdACtivity;
import com.cxyz.mine.activity.ConfirmChangeActivity;
import com.cxyz.mine.activity.MyinfoActivity;
import com.cxyz.mine.activity.OtherSettingActivity;
import com.cxyz.mine.activity.UserResponse;
import com.cxyz.mine.iview.IMineFragementView;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.io.File;

/**
 * Created by Administrator on 2018/9/25.
 */
@Route(path = "/mine/MineFragment")
public class MineFragment extends BaseFragment<IMineFragmentPresenter> implements View.OnClickListener ,IMineFragementView {
    private EditText et_email_getemail;
    private ImageView iv_mine_othersetting;
    private LinearLayout ll_mine_pc;
    private LinearLayout ll_mine_update;
    private LinearLayout ll_mine_resetpwd;
    private LinearLayout ll_mine_about;
    private QMUIRadiusImageView iv_mine_pic;
    private RelativeLayout ll_mine_info;
    private Dialog dialog;
    private ProgressBar pb_pro;
    private TextView tv_mine_name;
    private TextView  tv_mine_code;
    private Dialog mDialog;
    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mines_layout;
    }

    @Override
    protected void initData(Bundle bundle) {
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        User u = UserManager.getInstance().getUser();
        tv_mine_name=findViewById(R.id.tv_mine_name);
        tv_mine_code=findViewById(R.id.tv_mine_code);
        iv_mine_othersetting=findViewById(R.id.iv_mine_othersetting);
        ll_mine_pc=findViewById(R.id.ll_mine_pc);
        ll_mine_update=findViewById(R.id.ll_mine_update);
        ll_mine_resetpwd=findViewById(R.id.ll_mine_resetpwd);
        ll_mine_about=findViewById(R.id.ll_mine_about);
        iv_mine_pic=findViewById(R.id.iv_mine_pic);
        ll_mine_info=findViewById(R.id.ll_mine_info);
        iv_mine_pic.setImageResource(R.mipmap.belief);
        iv_mine_pic.setCircle(true);
        tv_mine_name.setText(u.getName());
        tv_mine_code.setText(u.getId());



    }

    @Override
    protected IMineFragmentPresenter createIPresenter() {
        return  new IMineFragmentPresenterlmpl();
    }

    @Override
    protected void setListener() {
        ll_mine_about.setOnClickListener(this);
        ll_mine_info.setOnClickListener(this);
        ll_mine_update.setOnClickListener(this);
        iv_mine_othersetting.setOnClickListener(this);
        ll_mine_resetpwd.setOnClickListener(this);
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
        Intent intent = new Intent(getActivity().getApplicationContext(), ConfirmChangeActivity.class);
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
        else if (viewId==R.id.ll_mine_resetpwd){
            ll_mine_resetpwd();
        }
        else if (viewId==R.id.ll_mine_about){
            showDialog(View.inflate(getActivity(),R.layout.item_email_layout,null));
            initDialogView(View.inflate(getActivity(),R.layout.item_email_layout,null));
         
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


    public void showDialog(View view){
        //1.创建一个Dialog对象，如果是AlertDialog对象的话，弹出的自定义布局四周会有一些阴影，效果不好
        mDialog = new Dialog(getActivity());
        //去除标题栏
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //2.填充布局
        LayoutInflater   inflater = LayoutInflater.from(getActivity());
         View        dialogView     = inflater.inflate(R.layout.item_email_layout, null);
        //将自定义布局设置进去
        mDialog.setContentView(dialogView);
        //3.设置指定的宽高,如果不设置的话，弹出的对话框可能不会显示全整个布局，当然在布局中写死宽高也可以
        WindowManager.LayoutParams lp     = new WindowManager.LayoutParams();
        Window                     window = mDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        lp.copyFrom(window.getAttributes());
        lp.width = (int) ( ScreenUtil.getScreenWidth(getActivity())*0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //注意要在Dialog show之后，再将宽高属性设置进去，才有效果
        mDialog.show();
        window.setAttributes(lp);

        //设置点击其它地方不让消失弹窗
        mDialog.setCancelable(false);
        initDialogView(dialogView);
    }

    private void initDialogView(View view) {
       final QMUIRoundButton bt_email_confirm = view.findViewById(R.id.bt_email_confirm);
        final QMUIRoundButton  bt_email_getcode= view.findViewById(R.id.bt_email_getcode);
        final EditText et_email_getemail=view.findViewById(R.id.et_email_getemail);

        class MyCountDownTimer extends CountDownTimer {

            public MyCountDownTimer(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }

            //计时过程
            @Override
            public void onTick(long l) {
                //防止计时过程中重复点击
                bt_email_getcode.setClickable(false);
                bt_email_getcode.getBackground().getCurrent().setColorFilter(Color.parseColor("#cdcdcd"), PorterDuff.Mode.SRC_ATOP);
                bt_email_getcode.setText(l/1000+"秒");

            }

            //计时完毕的方法
            @Override
            public void onFinish() {
                //重新给Button设置文字
                bt_email_getcode.setText("重新获取");
                //设置可点击
                bt_email_getcode.getBackground().getCurrent().setColorFilter(Color.parseColor("#0aa0ff"),PorterDuff.Mode.SRC_ATOP);
                bt_email_getcode.setClickable(true);
            }
        }
        bt_email_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        bt_email_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  final  MyCountDownTimer myCountDownTimer = new MyCountDownTimer(10000,1000);
                myCountDownTimer.start();

            }
        });

    }



    }



