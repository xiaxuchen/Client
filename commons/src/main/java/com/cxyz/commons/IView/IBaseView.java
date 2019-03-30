package com.cxyz.commons.IView;

import android.content.Context;

/**
 * Created by 夏旭晨 on 2018/9/22.
 */

public interface IBaseView {

    /**
     * 显示加载的视图
     */
    void showLoadingView();

    /**
     * 隐藏加载的视图
     */
    public void hideLoadingView();
}
