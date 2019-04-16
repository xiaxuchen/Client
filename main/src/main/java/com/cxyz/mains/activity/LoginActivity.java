package com.cxyz.mains.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cxyz.commons.IView.ICommonView;
import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.utils.SpUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.logiccommons.typevalue.UserType;
import com.cxyz.mains.R;
import com.cxyz.mains.ipresenter.IHomePresenter;
import com.cxyz.mains.ipresenter.ILoginPresenter;
import com.cxyz.mains.ipresenter.ipresenterimpl.IHomePresenterImpl;
import com.cxyz.mains.ipresenter.ipresenterimpl.ILoginPresenterImpl;
import com.cxyz.mains.iview.ILoginView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 夏旭晨 on 2018/9/30.
 */

@Route(path = "/main/LoginActivity")
public class LoginActivity extends BaseActivity<IHomePresenter> implements ICommonView{

    /**
     * 用户id
     */
    private EditText et_username;
    /**
     * 密码
     */
    private EditText et_password;
    /**
     * 忘记密码
     */
    private TextView tv_forget_pwd;
    /**
     * 登录
     */
    private Button bt_login;

    Dialog mDialog;
    /**
     * 用户类型
     */
    private RadioGroup rg_type;

    @Override
    public int getContentViewId() {
        return R.layout.activity_logins_layout;
    }

    @Override
    public void initView() {
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        bt_login = findViewById(R.id.bt_login);
        rg_type = findViewById(R.id.rg_type);
        tv_forget_pwd = findViewById(R.id.tv_forget_pwd);

        et_username.setText(getSpUtil().getString("username",""));
        if(SpUtil.getInstance().getInt("type",UserType.STUDENT) == UserType.TEACHER)
        {
            RadioButton rb_tea = findViewById(R.id.rb_teacher);
            rb_tea.setChecked(true);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void setEvent() {
        bt_login.setOnClickListener(v -> {
            /**
             * TODO 关掉注释
             * 从输入框和单选框中获取数据后登录
             */
//            iPresenter.login(et_username.getText().toString(),et_password.getText().toString(),rg_type
//                    .getCheckedRadioButtonId()==R.id.rb_student?0:1);
        });
    }

    @Override
    protected IHomePresenter createIPresenter() {
        return new IHomePresenterImpl();
    }

    public void loginSuccess() {
        ToastUtil.showShort("登录成功");
        startActivity(HomeActivity.class);
        this.finish();
    }

    public void loginFail(String message) {
        ToastUtil.showShort(message);
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        showDialog();
    }

    public void showDialog() {
        mDialog = new Dialog(getActivity(),R.style.common_dialog);
        //2.填充布局
        View mDialogView = View.inflate(getActivity(), R.layout.item_email_layout, null);
        initDialogView(mDialogView);

        //将自定义布局设置进去
        mDialog.setContentView(mDialogView);
        //3.设置指定的宽高,如果不设置的话，弹出的对话框可能不会显示全整个布局，当然在布局中写死宽高也可以
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = mDialog.getWindow();
        if (window != null) {
            lp.copyFrom(window.getAttributes());
            lp.width = (int) (ScreenUtil.getScreenWidth(getActivity()) * 0.8);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
        //设置点击其它地方不让消失弹窗
        mDialog.setCancelable(false);
        //注意要在Dialog show之后，再将宽高属性设置进去，才有效果
        mDialog.show();
//        initDialogView(dialogView);
    }

    private void initDialogView(View view) {
        EditText et_email;
        EditText et_new_pwd;
        Button btn_verify;
        Button btn_refresh;
        CountDownTimer timer;

        et_email = view.findViewById(R.id.et_email);
        et_new_pwd = view.findViewById(R.id.et_new_pwd);
        btn_verify = view.findViewById(R.id.btn_verify);
        btn_refresh = view.findViewById(R.id.btn_refresh);
        timer = new CountDownTimer(60000,1000) {
            //计时过程
            @Override
            public void onTick(long l) {
                //防止计时过程中重复点击
                btn_verify.setClickable(false);
                btn_verify.setEnabled(false);
                btn_verify.setText(l/1000+"秒");
            }

            //计时完毕的方法
            @Override
            public void onFinish() {
                //重新给Button设置文字
                btn_verify.setText("重新发送");
                btn_verify.setEnabled(true);
                //设置可点击
                btn_verify.setClickable(true);
            }
        };


        btn_verify.setOnClickListener(view1 -> {
            if(!verify(et_email,et_new_pwd))
                return;
            iPresenter.sendMail(et_email.getText().toString().trim(),et_new_pwd.getText().toString().trim());
            timer.start();
        });

        btn_refresh.setOnClickListener(view1 -> {
            if(!verify(et_email,et_new_pwd))
                return;
            iPresenter.refreshState();
        });

    }

    /**
     * 验证邮箱
     * @return 是否验证成功
     */
    private boolean verify(EditText et_email,EditText et_new_pwd)
    {
        String email = et_email.getText().toString().trim();
        String pwd = et_new_pwd.getText().toString().trim();
        //非空
        if (TextUtils.isEmpty(email)) {
            ToastUtil.showShort("邮箱不能为空！");
            return false;
        }
        if(TextUtils.isEmpty(pwd))
        {
            ToastUtil.showShort("新密码不能为空！");
            return false;
        }
        if(pwd.equals(SpUtil.getInstance().getString("pwd","")))
        {
            ToastUtil.showShort("新旧密码不能相同");
            return false;
        }
        //匹配邮箱格式
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(et_email.getText());
        if (!matcher.matches())
        {
            ToastUtil.showShort("邮箱格式有误！");
            return false;
        }
        return true;
    }

    @Override
    public void onEvent(int type, boolean result, Object... params) {
        switch (type)
        {
            case IHomePresenter.SEND_MAIL:
                {
                    if(result)
                        ToastUtil.showShort("邮件发送成功");
                    else
                        ToastUtil.showShort(params[0]);
                    break;
                }
            case IHomePresenter.REFRESH:
                {
                    if(result)
                    {

                    }else {
                        ToastUtil.showShort(params[0]);
                    }
                }
        }
    }
}
