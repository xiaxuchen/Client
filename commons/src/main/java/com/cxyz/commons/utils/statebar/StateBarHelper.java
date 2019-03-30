package com.cxyz.commons.utils.statebar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.cxyz.commons.utils.statebar.impl.QMUIStateBarHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

/**
 * Created by Administrator on 2019/2/3.
 * 状态栏工具类
 */

public abstract class StateBarHelper {

    private static StateBarHelper mInstance = null;

    public static StateBarHelper getInstance()
    {
        if(mInstance == null)
        {
            synchronized (StateBarHelper.class)
            {
                if(mInstance == null)
                {
                    mInstance = new QMUIStateBarHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 是否支持沉浸式状态栏
     * @return
     */
    public abstract boolean translucentSupport();

    /**
     * 获取状态栏高度
     * @return
     */
    public abstract int getStateBarHeight(Context context);

    /**
     * 状态栏变色，若为空则透明
     * @param color
     */
    public abstract void translucent(Activity activity,Integer color);


}
