package com.cxyz.logiccommons.service;

import android.accounts.NetworkErrorException;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cxyz.commons.utils.FileUtil;
import com.cxyz.commons.utils.HttpUtil.CommonOkHttpClient;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataHandler;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDownLoadListener;
import com.cxyz.commons.utils.HttpUtil.request.RequestParams;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.logiccommons.R;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2019/2/7.
 */

public class DownloadService extends Service {

    /**
     * 执行下载Service
     * @param downloadUrl 下载url
     * @param content_disposition content-disposition类型
     * @param filePath 文件路径
     * @param params 请求参数
     * @param context 上下文
     */
    public static void execute(String downloadUrl, String content_disposition, String filePath, HashMap<String,String> params, Context context,DownloadListener listener)
    {
        if(params == null)
            params = new HashMap<>();
        Intent intent = new Intent(context,DownloadService.class);
        intent.putExtra("downloadUrl",downloadUrl);
        if(content_disposition != null)
            intent.putExtra("content_disposition",content_disposition);
        intent.putExtra("filePath",filePath);
        intent.putExtra("params",params);
        if(listener != null)
            intent.putExtra("listener",listener);
        context.startService(intent);
    }

    public static void execute(String downloadUrl, String content_disposition, String filePath, HashMap<String,String> params, Context context)
    {
        execute(downloadUrl,content_disposition,filePath,params,context,null);
    }

    public static void execute(String downloadUrl, String content_disposition, String filePath, Context context)
    {
        execute(downloadUrl,content_disposition,filePath,null,context,null);
    }



    private String downloadUrl;

    private String content_disposition;

    private String filePath;

    private DownloadListener listener;

    private NotificationManager notificationManager;

    private Map<String,String> params;

    private Notification notification;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        filePath = intent.getStringExtra("filePath");
        downloadUrl = intent.getStringExtra("downloadUrl");
        content_disposition = intent.getStringExtra("content_disposition");
        params = (Map<String, String>) intent.getSerializableExtra("params");
        try {
            CommonOkHttpClient.getFile(downloadUrl,new RequestParams(params),new DisposeDataHandler(new DisposeDownLoadListener() {
                @Override
                public void onProgress(int progress) {
                    LogUtil.d(progress);
                    notifyUser(getString(R.string.update_download_progressing), getString(R.string.update_download_progressing), progress);
                }

                @Override
                public void onSuccess(Object responseObj) {
                    LogUtil.d("下载完成");
                    notifyUser(getString(R.string.update_download_finish), getString(R.string.update_download_finish), 100);
                    ToastUtil.showShort("文件已下载至"+((File)responseObj).getAbsolutePath());
                    stopSelf();
                }

                @Override
                public void onFailure(Object error) {
                    LogUtil.d("下载失败");
                    notifyUser(getString(R.string.update_download_failed), getString(R.string.update_download_failed), 0);
                    stopSelf();
                }
            },filePath,content_disposition));
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新notification
     * @param result
     * @param msg
     * @param progress
     */
    private void notifyUser(String result, String msg, int progress){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.common_logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.common_logo))
                .setContentTitle(getString(R.string.common_app_name))
                .setContentText(msg);
        if(progress>0 && progress<=100){

            builder.setProgress(100,progress,false);

        }else{
            builder.setProgress(0, 0, false);
        }
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setTicker(result);
        builder.setContentIntent(progress>=100 ? getContentIntent() :
                PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
        notification = builder.build();
        notificationManager.notify(0, notification);
    }


    /**
     * 获取pendingIntent
     * @return
     */
    private PendingIntent getContentIntent() {
        Log.e("tag", "getContentIntent()");
        File file = new File(filePath);
        Intent intent = null;
        if(listener != null)
        {
            intent = listener.onClickNotification(file);
        }
        try {
            if(intent == null)
                intent = FileUtil.getOpenIntent(this,file);
        } catch (IOException e) {
            e.printStackTrace();
            return PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        startActivity(intent);
        return pendingIntent;
    }

    public interface DownloadListener extends Serializable{

        Intent onClickNotification(File file);
    }
}
