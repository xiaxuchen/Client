package com.cxyz.mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.annotation.ColorRes;
import android.view.Display;
import android.view.View;

import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.widget.TitleView;
import com.cxyz.mine.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

/**
 * Created by Administrator on 2019/2/27.
 */

public class ConfirmChangeActivity extends BaseActivity {
    private QMUIRoundButton bt_confirm_getcode;
    private QMUIRoundButton bt_confirm_next;
    private TitleView tv_confirm_title;
    @Override
    public int getContentViewId() {
        return R.layout.activity_confirmchange_layout;
    }

    @Override
    public void initView() {
        tv_confirm_title=findViewById(R.id.tv_confirm_title);
        bt_confirm_getcode=findViewById(R.id.bt_confirm_getcode);
        bt_confirm_next=findViewById(R.id.bt_confirm_next);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setEvent() {
        tv_confirm_title.setBackClickListener(new TitleView.OnBackClickListener() {
            @Override
            public void onBackClick(View v) {
                onBackPressed();
                getActivity().overridePendingTransition(R.anim.enter_next, R.anim.enter_exit);//动画过渡效果
            }
        });
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(10000,1000);
        bt_confirm_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ChangePwdACtivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_next, R.anim.enter_exit);//动画过渡效果
            }
        });
        bt_confirm_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCountDownTimer.start();
            }
        });
    }

    @Override
    protected IBasePresenter createIPresenter() {
        return null;
    }
    //倒计时函数
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            bt_confirm_getcode.setClickable(false);
            bt_confirm_getcode.getBackground().getCurrent().setColorFilter(Color.parseColor("#cdcdcd"),PorterDuff.Mode.SRC_ATOP);
            bt_confirm_getcode.setText(l/1000+"秒");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            bt_confirm_getcode.setText("重新获取");
            //设置可点击
            bt_confirm_getcode.getBackground().getCurrent().setColorFilter(Color.parseColor("#0aa0ff"),PorterDuff.Mode.SRC_ATOP);
            bt_confirm_getcode.setClickable(true);
        }
    }
/*    private void clearTimer() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }*/
@Override
public void onBackPressed() {
    //TODO something
    super.onBackPressed();
    getActivity().overridePendingTransition(R.anim.enter_next, R.anim.enter_exit);//动画过渡效果
}

}
