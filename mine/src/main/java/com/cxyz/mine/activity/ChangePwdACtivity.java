package com.cxyz.mine.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.manager.ActivityStackManager;
import com.cxyz.commons.utils.SpUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.TitleView;
import com.cxyz.mine.IPresenter.IChangePwdPresenter;
import com.cxyz.mine.IPresenter.impl.IChangePwdPresenterImpl;
import com.cxyz.mine.R;
import com.cxyz.mine.iview.IChangePwdView;
import com.maksim88.passwordedittext.PasswordEditText;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;


/**
 * Created by ${喻济生} on 2018/11/12.
 */

public class ChangePwdACtivity extends BaseActivity <IChangePwdPresenter>implements IChangePwdView{
    private TitleView tv_changepwd_title;
    private EditText et_changepwd_pwd;
    private EditText et_changepwd_checkpwd;
    private QMUIRoundButton bt_changepwd_finish;
    @Override
    public int getContentViewId() {
        return R.layout.activity_changepwds_layout;
    }

    @Override
    public void initView() {
        tv_changepwd_title=findViewById(R.id.tv_changepwd_title);
        et_changepwd_pwd=findViewById(R.id.et_changepwd_pwd);
        et_changepwd_checkpwd=findViewById(R.id.et_changepwd_checkpwd);
        bt_changepwd_finish=findViewById(R.id.bt_changepwd_finish);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setEvent() {
        tv_changepwd_title.setBackClickListener(new TitleView.OnBackClickListener() {
            @Override
            public void onBackClick(View v) {
                onBackPressed();
                getActivity().overridePendingTransition(R.anim.enter_next, R.anim.enter_exit);//动画过渡效果

            }
        });
        bt_changepwd_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkpwd();
            }
        });

    }

    @Override
    protected IChangePwdPresenter createIPresenter() {
        return new IChangePwdPresenterImpl();
    }

    @Override
    public void changeSuccess(String message) {

    }

    @Override
    public void chanegFail(String message) {

    }
    public void  checkpwd(){
        String pwd=et_changepwd_pwd.getText().toString();
        String checkpwd=et_changepwd_checkpwd.getText().toString();
        if (pwd.equals(checkpwd)) {
            if (!Validator.isPassword(pwd) && pwd.length() < 6) {
                ToastUtil.showShort("您输入的密码过于简单，请输入正确格式！");
            } else if (!Validator.isPassword(pwd) && pwd.length() > 20) {
                ToastUtil.showShort("您输入的密码过于复杂，请输入正确格式！");
            }
        }
        else ToastUtil.showShort("两次输入的密码不一致哦");
    }
    public void onBackPressed() {
        //TODO something
        super.onBackPressed();
        getActivity().overridePendingTransition(R.anim.enter_next, R.anim.enter_exit);//动画过渡效果
    }
}
