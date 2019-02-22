package com.cxyz.mains.activity;

import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.mains.R;
import com.cxyz.mains.holder.LocalImageHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/7.
 */

public class FirstShowActivity extends BaseActivity {

    Long flags[] = new Long[2];

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

    @Override
    public void onBackPressed() {
        if(flags[0]==null)
        {
            flags[0] = System.currentTimeMillis();
            ToastUtil.showShort("再按一次返回退出应用");
        }
        else{
            if(flags[1]==null)
                flags[1] = System.currentTimeMillis();
            else if(flags[0]>=flags[1])
                    flags[1]=System.currentTimeMillis();
            else
                    flags[0]=System.currentTimeMillis();

            if(Math.abs(flags[0]-flags[1])<=2000)
                super.onBackPressed();
            else
                ToastUtil.showShort("再按一次返回退出应用");
            LogUtil.e(Math.abs(flags[0]-flags[1]));
        }

    }
}
