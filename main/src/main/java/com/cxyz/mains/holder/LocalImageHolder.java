package com.cxyz.mains.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.cxyz.commons.utils.AppUtil;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.SpUtil;
import com.cxyz.logiccommons.manager.UserManager;
import com.cxyz.mains.R;
import com.cxyz.mains.activity.HomeActivity;
import com.cxyz.mains.activity.LoginActivity;

/**
 * Created by Administrator on 2019/2/7.
 */

public class LocalImageHolder extends Holder<LocalImageHolder.Data> {

    private ImageView imageView;

    private Button btn_open;

    private Context context;

    public LocalImageHolder(View itemView,Context context) {
        super(itemView);
        this.context = context;
    }

    @Override
    protected void initView(View v) {
        imageView = v.findViewById(R.id.iv_content);
        btn_open = v.findViewById(R.id.btn_open);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void updateUI(Data data) {
        imageView.setImageResource(data.res);
        if(data.isLast)
        {
            btn_open.setVisibility(View.VISIBLE);
            btn_open.setOnClickListener(view -> {
                SpUtil instance = SpUtil.getInstance();
                LogUtil.e(SpUtil.getInstance().getString("versionName","caonima"));
                LogUtil.e("context:"+AppUtil.getVersionName(context));
                instance.putString("versionName", AppUtil.getVersionName(context));
                LogUtil.e(SpUtil.getInstance().getString("versionName","caonima"));
                Class clazz = LoginActivity.class;
                if(UserManager.getInstance().isLogined())
                    clazz = HomeActivity.class;
                Intent intent = new Intent(context, clazz);
                context.startActivity(intent);
            });
        }
    }

    public static class Data{

        public Data(Integer res, boolean isLast) {
            Data.this.res = res;
            Data.this.isLast = isLast;
        }

        public Integer res;

        public boolean isLast;
    }
}