package com.cxyz.commons.utils.statebar.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.statebar.StateBarHelper;
import com.qmuiteam.qmui.util.QMUIDeviceHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

/**
 * Created by Administrator on 2019/2/3.
 */

public class QMUIStateBarHelper extends StateBarHelper{


    @Override
    public boolean translucentSupport() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                // Essential Phone 不支持沉浸式，否则系统又不从状态栏下方开始布局又给你下发 WindowInsets
                && !Build.BRAND.toLowerCase().contains("essential"))
        {
            if(QMUIDeviceHelper.isMeizu() || QMUIDeviceHelper.isMIUI())
                return true;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getStateBarHeight(Context context) {
        return QMUIStatusBarHelper.getStatusbarHeight(context);
    }

    @Override
    public void translucent(Activity activity,Integer color) {
        if(color == null)
            QMUIStatusBarHelper.translucent(activity);
        else
            QMUIStatusBarHelper.translucent(activity,color);
    }
}
