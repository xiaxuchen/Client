package com.cxyz.mains.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.commons.activity.FragmentActivity;
import com.cxyz.commons.dialog.DialogFactory;
import com.cxyz.commons.fragment.BaseFragment;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ScreenUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.TitleView;
import com.cxyz.logiccommons.adapter.FragmentAdapter;
import com.cxyz.mains.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeActivity extends FragmentActivity implements View.OnClickListener {

    //标题栏
    private TitleView tv_title;

    /**
     * 各个tab的图标
     */
    private ImageView iv_check;

    private ImageView iv_home;

    private ImageView iv_mine;

    private TextView tv_check;

    /**
     * 各个tab的文字
     */
    private TextView tv_mine;

    private TextView tv_home;

    private LinearLayout ll_check;

    private LinearLayout ll_mine;

    private LinearLayout ll_home;

    //装载fragment的viewpager
    private ViewPager vp_content;

    //所需要展示的所以fragment
    private List<BaseFragment> fragmentList;

    private PagerAdapter pagerAdapter;

    private int[] ids;
    private Dialog mDialog;

    @Override
    public int getContentViewId() {
        return R.layout.activity_home_layout;
    }

    @Override
    public void initView() {
        iv_check = findViewById(R.id.iv_check);
        iv_home = findViewById(R.id.iv_home);
        iv_mine = findViewById(R.id.iv_mine);
        tv_check = findViewById(R.id.tv_check);
        tv_mine = findViewById(R.id.tv_mine);
        tv_home = findViewById(R.id.tv_home);
        tv_title = findViewById(R.id.tv_title);
        ll_check = findViewById(R.id.ll_check);
        ll_mine = findViewById(R.id.ll_mine);
        ll_home = findViewById(R.id.ll_home);
        vp_content = findViewById(R.id.vp_content);

        //将fragment添加进viewpager
        vp_content.setAdapter(pagerAdapter);
        switchFragment(1, true);
        tv_title.setTitle("主页");
        tv_title.setBack(0, "");
    }

    @Override
    public void initData() {
        //获取所有的Fragment
        fragmentList = new ArrayList<>();
        fragmentList.add(getFragment("/check/MyCheckFragment"));
        fragmentList.add(getFragment("/homepage/HomeFragment"));
        fragmentList.add(getFragment("/mine/MineFragment"));
        ids = new int[]{R.id.ll_check, R.id.ll_home, R.id.ll_mine};
        //初始化pagerAdapter
        pagerAdapter = new FragmentAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

    }


    private BaseFragment getFragment(String fragmentName) {
        return (BaseFragment) ARouter.getInstance().
                build(fragmentName).navigation();
    }

    @Override
    public void setEvent() {
        ll_check.setOnClickListener(this);
        ll_mine.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                switchFragment(position, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void afterInit() {
        super.afterInit();
//        showDialog();
    }

    @Override
    protected IBasePresenter createIPresenter() {
        return null;
    }


    /**
     * 选择要显示的fragment
     */
    public void switchFragment(int position, boolean ismove) {
        int id = ids[position];
        if (id == R.id.ll_check) {
            iv_check.setImageResource(R.mipmap.statistic_on);
            iv_mine.setImageResource(R.mipmap.mine_off);
            iv_home.setImageResource(R.mipmap.home_off);
            tv_check.setTextColor(getResources().getColor(R.color.app_on));
            tv_mine.setTextColor(getResources().getColor(R.color.app_off));
            tv_home.setTextColor(getResources().getColor(R.color.app_off));
            tv_title.setTitle("统计");
        } else if (id == R.id.ll_mine) {
            iv_check.setImageResource(R.mipmap.statistic_off);
            iv_home.setImageResource(R.mipmap.home_off);
            iv_mine.setImageResource(R.mipmap.mine_on);
            tv_mine.setTextColor(getResources().getColor(R.color.app_on));
            tv_check.setTextColor(getResources().getColor(R.color.app_off));
            tv_home.setTextColor(getResources().getColor(R.color.app_off));
            tv_title.setTitle("我的");
        } else if (id == R.id.ll_home) {
            iv_check.setImageResource(R.mipmap.statistic_off);
            iv_mine.setImageResource(R.mipmap.mine_off);
            tv_mine.setTextColor(getResources().getColor(R.color.app_off));
            tv_check.setTextColor(getResources().getColor(R.color.app_off));
            tv_home.setTextColor(getResources().getColor(R.color.app_on));
            iv_home.setImageResource(R.mipmap.home_on);
            tv_title.setTitle("主页");
        }
        if (ismove) {
            vp_content.setCurrentItem(position);
            LogUtil.e(position + "");
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int i = 0;
        for (int fid : ids) {
            if (fid == id) {
                switchFragment(i, true);
                break;
            }
            i++;
        }
    }

    @Override
    public int getFragmentContentId() {
        return 0;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    public void onBackPressed() {
        DialogFactory.showExitDialog(getActivity());
//        if(flags[0]==null)
//        {
//            flags[0] = System.currentTimeMillis();
//            ToastUtil.showShort("再按一次返回退出应用");
//        }
//        else{
//            if(flags[1]==null)
//                flags[1] = System.currentTimeMillis();
//            else if(flags[0]>=flags[1])
//                flags[1]=System.currentTimeMillis();
//            else
//                flags[0]=System.currentTimeMillis();
//
//            if(Math.abs(flags[0]-flags[1])<=2000)
//                super.onBackPressed();
//            else
//                ToastUtil.showShort("再按一次返回退出应用");
//            LogUtil.e(Math.abs(flags[0]-flags[1]));
//        }

    }

//    public void showDialog() {
//        mDialog = new Dialog(getActivity(),R.style.common_dialog);
//        //2.填充布局
//        View mDialogView = View.inflate(getActivity(), R.layout.item_email_layout, null);
//        initDialogView(mDialogView);
//
//        //将自定义布局设置进去
//        mDialog.setContentView(mDialogView);
//        //3.设置指定的宽高,如果不设置的话，弹出的对话框可能不会显示全整个布局，当然在布局中写死宽高也可以
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        Window window = mDialog.getWindow();
//        if (window != null) {
//            lp.copyFrom(window.getAttributes());
//            lp.width = (int) (ScreenUtil.getScreenWidth(getActivity()) * 0.8);
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            window.setAttributes(lp);
//        }
//        //设置点击其它地方不让消失弹窗
//        mDialog.setCancelable(false);
//        //注意要在Dialog show之后，再将宽高属性设置进去，才有效果
//        mDialog.show();
////        initDialogView(dialogView);
//    }
//
//    private void initDialogView(View view) {
//        EditText et_email;
//        EditText et_new_pwd;
//        Button btn_verify;
//        Button btn_refresh;
//
//        et_email = view.findViewById(R.id.et_email);
//        et_new_pwd = view.findViewById(R.id.et_new_pwd);
//        btn_verify = view.findViewById(R.id.btn_verify);
//        btn_refresh = view.findViewById(R.id.btn_refresh);
//
//        btn_verify.setOnClickListener(view1 -> {
//            if(!verify(et_email))
//                return;
//        });
//
//        btn_refresh.setOnClickListener(view1 -> {
//            if (!verify(et_email)) {
//                return;
//            }
//            if(TextUtils.isEmpty(et_verify_code.getText()))
//            {
//                ToastUtil.showShort("请输入验证码！");
//                return;
//            }
//            mDialog.cancel();
//        });
//
//    }
//
//    /**
//     * 验证邮箱
//     * @return 是否验证成功
//     */
//    private boolean verify(EditText et_email)
//    {
//        //非空
//        if (TextUtils.isEmpty(et_email.getText())) {
//            ToastUtil.showShort("邮箱不能为空！");
//            return false;
//        }
//        //匹配邮箱格式
//        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
//
//        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(et_email.getText());
//        if (!matcher.matches())
//        {
//            ToastUtil.showShort("邮箱格式有误！");
//            return false;
//        }
//        return true;
//    }

}
