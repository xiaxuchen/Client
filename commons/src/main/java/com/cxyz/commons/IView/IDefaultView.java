package com.cxyz.commons.IView;

import android.content.Context;

import com.cxyz.commons.widget.loading.LoadingCreator;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * Created by Administrator on 2018/11/7.
 */

public class IDefaultView implements IBaseView{

    private Context context;
    /**
     * @param context
     */
    public IDefaultView(Context context)
    {
        this.context = context;
    }


    @Override
    public void showLoadingView() {
        LoadingCreator.showLoading(context);
    }

    @Override
    public void hideLoadingView() {
        LoadingCreator.stopLoading();
    }
}
