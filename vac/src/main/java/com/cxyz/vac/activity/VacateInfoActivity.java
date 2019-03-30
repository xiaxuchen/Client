package com.cxyz.vac.activity;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.activity.FragmentActivity;
import com.cxyz.commons.dialog.DialogFactory;
import com.cxyz.commons.fragment.BaseFragment;
import com.cxyz.commons.widget.TitleView;
import com.cxyz.logiccommons.adapter.FragmentAdapter;
import com.cxyz.vac.R;
import com.cxyz.vac.fragment.VacDayFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/16.
 */

public class VacateInfoActivity extends BaseActivity {

    private ViewPager vp_vac_photos;

//    private RadioGroup rg_time_type,rg_indicators;

    private List<Fragment> fragments;

    private TitleView tv_title;

    @Override
    public int getContentViewId() {
        return R.layout.activity_vacate_info_layout;
    }

    @Override
    public void initView() {
//        rg_time_type = findViewById(R.id.rg_time_type);
//        rg_indicators = findViewById(R.id.rg_indicators);
        tv_title = findViewById(R.id.tv_title);

        vp_vac_photos = findViewById(R.id.vp_vac_photos);

        vp_vac_photos.setAdapter(new FragmentAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    public void initData() {
        fragments = new ArrayList<>();
        fragments.add(VacDayFragment.newInstance());
    }

    @Override
    public void setEvent() {
        //保证指示器正确显示
//        rg_time_type.setOnCheckedChangeListener((radioGroup, i) -> {
//            for(int v = 0; v < radioGroup.getChildCount(); v++)
//            {
//                if(radioGroup.getChildAt(v).getId() == i) {
//                    final RadioButton indicator = (RadioButton) rg_indicators.getChildAt(v);
//                    indicator.setChecked(true);
//                }
//            }
//        });
        tv_title.setBackClickListener(v -> onBackPressed());
    }

    @Override
    protected IBasePresenter createIPresenter() {
        return null;
    }
}
