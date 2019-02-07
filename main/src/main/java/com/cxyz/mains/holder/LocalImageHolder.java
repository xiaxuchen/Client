package com.cxyz.mains.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.cxyz.commons.utils.AppUtil;
import com.cxyz.commons.utils.SpUtil;
import com.cxyz.mains.R;
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
                instance.putBoolean("isFirst",false);
                instance.putString("versionName", AppUtil.getVersionName(context));
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            });
        }
    }

    public static class Data{

        public Data(Integer res, boolean isLast) {
            this.res = res;
            this.isLast = isLast;
        }

        public Integer res;

        public boolean isLast;
    }
}