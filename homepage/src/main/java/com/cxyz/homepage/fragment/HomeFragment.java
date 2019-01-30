package com.cxyz.homepage.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.cxyz.commons.fragment.BaseFragment;
import com.cxyz.commons.utils.DateUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.TitleView;
import com.cxyz.homepage.R;
import com.cxyz.homepage.acitivity.CheckRedordActivity;
import com.cxyz.homepage.acitivity.ClazzActivity;
import com.cxyz.homepage.acitivity.ExportActivity;
import com.cxyz.homepage.adapter.FunctionAdapter;
import com.cxyz.homepage.dto.CheckTaskDto;
import com.cxyz.homepage.ipresenter.IHomePresenter;
import com.cxyz.homepage.ipresenter.impl.IHomePresenterImpl;
import com.cxyz.homepage.iview.IHomeView;
import com.cxyz.logiccommons.manager.UserManager;
import com.cxyz.logiccommons.typevalue.PowerType;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by 鱼塘主 on 2018/9/25.
 */
@Route(path="/homepage/HomeFragment")
public class HomeFragment extends BaseFragment<IHomePresenter> implements IHomeView,OnBannerListener {
    private TitleView tv_homepage_title;
    private Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    //Adapter需要的数据
    ArrayList<Map<String,Object>> data;

    //功能
    private GridView gv_function;

    private int indexs[];

    @Override
    protected int getLayoutId() {
        return R.layout.homepagelayout;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected void initData(Bundle bundle) {
        String texts[] = new String[]{"考勤","考勤统计","班级课表","请假申请","上传假条","请假审核","数据导出","导入数据"};
        int imgs[] = new int[]{R.mipmap.appa, R.mipmap.appb, R.mipmap.appc,
                R.mipmap.appd, R.mipmap.appe, R.mipmap.appf, R.mipmap.appg,
                R.mipmap.apph};
        data = new ArrayList<>();
        Map<String, Object> map = null;
        switch (UserManager.getInstance().getUser().getPower())
        {
            case PowerType.STU_NORMAL:
                {
                    indexs= new int[]{2, 3, 4};
                    for (int index:indexs) {
                        map = new HashMap<>();
                        map.put("text", texts[index]);
                        map.put("img", imgs[index]);
                        data.add(map);
                    }
                }break;
            case PowerType.STU_CHECKER:
                {
                    indexs = new int[]{0, 2, 3, 4, 7};
                    for (int index:indexs) {
                        map = new HashMap<>();
                        map.put("text", texts[index]);
                        map.put("img", imgs[index]);
                        data.add(map);
                    }
                }break;

            default:
            {
                indexs = new int[]{0, 1, 5, 6};
                for (int index:indexs) {
                    map = new HashMap<>();
                    map.put("text", texts[index]);
                    map.put("img", imgs[index]);
                    data.add(map);
                }
            }break;

        }
    }
    /**
     * 初始化view的
     * @param view mRootView
     * @param savedInstanceState
     */
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        gv_function = findViewById(R.id.gv_function);
        gv_function.setAdapter(new FunctionAdapter(getActivity(),data,R.layout.item_function_layout));
        banner=findViewById(R.id.banner);
        //放图片地址的集合
        list_path = new ArrayList<>();
        //放标题的集合
        list_title = new ArrayList<>();
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱劳动");
        list_title.add("不搞对象");
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);
        //设置轮播图的标题集合
        banner.setBannerTitles(list_title);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(this)
                //必须最后调用的方法，启动轮播图。
                .start();
    }

    @Override
    protected IHomePresenter createIPresenter() {
        return new IHomePresenterImpl();
    }

    /**
     * 设置监听的
     */
    @Override
    protected void setListener() {
        gv_function.setOnItemClickListener((adapterView, view, i, l) -> {

            switch (indexs[i])
            {
                case 0:
                    {
                        ARouter.getInstance().build("/check/CheckActivity").navigation();break;//跳转至考勤页面
                    }
                case 1:
                    {
                        getHoldingActivity().startActivity(CheckRedordActivity.class);break;//跳转至考勤图表;
                    }
                case 2:
                    {
                        getHoldingActivity().startActivity(ClazzActivity.class);break;//跳转至日历课次;
                    }
                case 3:
                    {
                        ARouter.getInstance().build("/vac/VacateActivity").navigation();break;
                    }
                case 4:
                    {
                        ToastUtil.showShort("此功能正在开发中...");break;
                    }
                case 5:
                    {
                        ARouter.getInstance().build("/vac/AuditActivity").navigation();break;
                    }
                case 6:
                    {
                        getHoldingActivity().startActivity(ExportActivity.class);break;
                    }
                case 7:
                    {
                        ARouter.getInstance().build("/info/UploadActivity").navigation();break;
                    }
            }
        });
    }



    @Override
    public void showTask(CheckTaskDto taskDto) {
        if(taskDto != null)
        {
            showDialog(taskDto);
        }
    }

    @Override
    public void showNoTask(String info) {
        ToastUtil.showShort(info);
    }

    /**
     * 显示对话框
     * @param info
     */
    private void showDialog(final CheckTaskDto info)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("检查到考勤任务,是否考勤？");
        builder.setIcon(R.mipmap.common_logo);
        View view = View.inflate(getActivity(),R.layout.item_dialog_layout,null);
        TextView tv_task_name = (TextView) view.findViewById(R.id.tv_task_name);
        tv_task_name.setText(info.getName());
        TextView tv_task_tea = (TextView) view.findViewById(R.id.tv_task_tea);
        tv_task_tea.setText(info.getSponsorName());
        TextView tv_task_time = (TextView) view.findViewById(R.id.tv_task_time);
        tv_task_time.setText(DateUtil.dateToString(info.getStart(), DateUtil.DatePattern.ONLY_TIME)+
                "-"+DateUtil.dateToString(info.getEnd(), DateUtil.DatePattern.ONLY_TIME));
        TextView tv_task_place = (TextView) view.findViewById(R.id.tv_place);
        //TODO 这里以后需要改成name
        tv_task_place.setText(info.getSpot()==null?"":info.getSpot());
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ARouter.getInstance().build("/check/DailyCheckActivity").
                        withInt("compId",info.getId()).navigation();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void OnBannerClick(int position) {
        ToastUtil.showLong("你点击了第"+position+"项");
    }

    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
