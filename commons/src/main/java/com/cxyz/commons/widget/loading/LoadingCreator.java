package com.cxyz.commons.widget.loading;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.cxyz.commons.R;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.widget.loading.indicator.Creator;
import com.cxyz.commons.widget.loading.indicator.IndicatorType;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallClipRotateIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2019/2/22.
 */

public class LoadingCreator {

    private static final int DIMEN_SCALE = 8;

    private static final List<Dialog> LOADERS = new CopyOnWriteArrayList<>();

    private static final IndicatorType DEFAULT_TYPE = IndicatorType.BallClipRotatePulseIndicator;

    private static final boolean DEFAULT_CANCELABLE = true;

    /**
     * 显示加载
     * @param context
     * @param type
     */
    public static void showLoading(Context context, Enum<IndicatorType> type, DialogInterface.OnCancelListener listener,boolean cancelable)
    {
        Dialog dialog = new Dialog(context, R.style.dialog_loading);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(listener);
        AVLoadingIndicatorView view = new AVLoadingIndicatorView(context);
        view.setIndicator(Creator.create(context,type.name()));
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        if(window != null)
        {
            WindowManager.LayoutParams params = window.getAttributes();
            int screenWidth = ScreenUtil.getScreenWidth(context);
            int screenHeight = ScreenUtil.getScreenHeight(context);
            params.width = screenWidth/DIMEN_SCALE;
            params.height = screenHeight/DIMEN_SCALE;
            params.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void showLoading(Context context,IndicatorType type,boolean cancelable)
    {
        showLoading(context,type,null,cancelable);
    }

    public static void showLoading(Context context,IndicatorType type,DialogInterface.OnCancelListener listener)
    {
        showLoading(context,type,listener,DEFAULT_CANCELABLE);
    }

    public static void showLoading(Context context,boolean cancelable)
    {
        showLoading(context,DEFAULT_TYPE,cancelable);
    }

    public static void showLoading(Context context, DialogInterface.OnCancelListener listener)
    {
        showLoading(context,DEFAULT_TYPE,listener);
    }

    /**
     * 不用监听
     * @param context
     * @param type
     */
    public static void showLoading(Context context, IndicatorType type) {
        showLoading(context,type,null,DEFAULT_CANCELABLE);
    }

    /**
     * 采用默认的图案显示加载
     * @param context
     */
    public static void showLoading(Context context)
    {
        showLoading(context,DEFAULT_TYPE);
    }

    public static void stopLoading()
    {
        for(Dialog dialog:LOADERS)
        {
            if(dialog != null && dialog.isShowing())
                dialog.cancel();
            synchronized (LoadingCreator.class){
                LOADERS.remove(dialog);
            }
        }
    }

}
