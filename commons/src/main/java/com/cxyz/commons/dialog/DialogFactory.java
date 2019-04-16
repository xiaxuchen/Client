package com.cxyz.commons.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;

import java.util.WeakHashMap;

/**
 * Created by Administrator on 2019/2/27.
 */

public class DialogFactory {

    /**
     * 获取退出程序的dialog
     * @param activity
     * @return
     */
    public static void showExitDialog(Activity activity){
        QMUIDialog.MessageDialogBuilder builder = new QMUIDialog.MessageDialogBuilder(activity);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("退出提示");
        builder.addAction("取消",(dialog, index) -> {dialog.cancel();});
        builder.addAction("确定",(dialog, index) -> {System.exit(0);});
        Dialog d = builder.create();
        d.show();
    }

}
