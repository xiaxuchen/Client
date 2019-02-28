package com.cxyz.mine.activity;

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


/**
 * Created by ${喻济生} on 2018/11/12.
 */

public class ChangePwdACtivity extends BaseActivity <IChangePwdPresenter>implements IChangePwdView{


    @Override
    public int getContentViewId() {
        return R.layout.activity_changepwds_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setEvent() {

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
}
