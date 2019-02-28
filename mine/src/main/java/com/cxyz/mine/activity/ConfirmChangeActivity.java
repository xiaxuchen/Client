package com.cxyz.mine.activity;

import android.content.Intent;
import android.view.View;

import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.mine.R;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

/**
 * Created by Administrator on 2019/2/27.
 */

public class ConfirmChangeActivity extends BaseActivity {
    private QMUIRoundButton bt_confirm_getcode;
    private QMUIRoundButton bt_confirm_next;
    @Override
    public int getContentViewId() {
        return R.layout.activity_confirmchange_layout;
    }

    @Override
    public void initView() {
        bt_confirm_getcode=findViewById(R.id.bt_confirm_getcode);
        bt_confirm_next=findViewById(R.id.bt_confirm_next);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setEvent() {
        bt_confirm_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ChangePwdACtivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected IBasePresenter createIPresenter() {
        return null;
    }
}
