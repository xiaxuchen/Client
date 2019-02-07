package com.cxyz.mains.activity;

import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.mains.R;
import com.cxyz.mains.holder.LocalImageHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/7.
 */

public class FirstShowActivity extends BaseActivity {

    private ConvenientBanner cb_banner;

    private List<LocalImageHolder.Data> imgs;
    @Override
    public int getContentViewId() {
        return R.layout.activity_first_show_layout;
    }

    @Override
    public void initView() {
        cb_banner = findViewById(R.id.cb_banner);
        cb_banner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new LocalImageHolder(itemView,getActivity());
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner_page_layout;
            }
        },imgs).setCanLoop(false)
        .setPageIndicator(new int[]{R.drawable.first_show_indicator_normal_shape,R.drawable.first_show_indicator_using_shape});

    }

    @Override
    public void initData() {
        imgs = new ArrayList<>();
        imgs.add(new LocalImageHolder.Data(R.mipmap.app_splash,false));
        imgs.add(new LocalImageHolder.Data(R.mipmap.app_splash,false));
        imgs.add(new LocalImageHolder.Data(R.mipmap.app_splash,false));
        imgs.add(new LocalImageHolder.Data(R.mipmap.ic_launcher,true));
    }

    @Override
    public void setEvent() {

    }

    @Override
    protected IBasePresenter createIPresenter() {
        return null;
    }
}
