package com.cxyz.vac.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cxyz.commons.activity.BaseActivity;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataListener;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.commons.utils.ToastUtil;
import com.cxyz.commons.widget.TitleView;
import com.cxyz.commons.widget.loading.LoadingCreator;
import com.cxyz.logiccommons.domain.CheckResult;
import com.cxyz.logiccommons.domain.Photo;
import com.cxyz.vac.R;
import com.cxyz.vac.adapter.VacateAdapter;
import com.cxyz.vac.constant.RequestCenter;
import com.cxyz.vac.dto.VacateDto;
import com.cxyz.vac.icon.IconfontModule;
import com.cxyz.vac.ipresenter.IMineVacatePresenter;
import com.cxyz.vac.ipresenter.impl.IMineVacatePresenterImpl;
import com.cxyz.vac.iview.IMineVacateView;
import com.joanzapata.iconify.Iconify;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.filter.entity.ImageFile;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/12/31.
 */

@Route(path = "/vac/MineVacateActivity")
public class MineVacateActivity extends BaseActivity<IMineVacatePresenter> implements IMineVacateView {

    private TitleView tv_title;

    private ListView lv_vacates;

    private VacateAdapter adapter;

    @Override
    public int getContentViewId() {
        Iconify.with(new IconfontModule());
        return R.layout.activity_mine_vacate_layout;
    }

    @Override
    public void initView() {
        tv_title = findViewById(R.id.tv_title);
        lv_vacates = findViewById(R.id.lv_vacates);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setEvent() {
        tv_title.setBackClickListener(v -> onBackPressed());
        lv_vacates.setEmptyView(findViewById(R.id.tv_empty));
    }

    @Override
    protected void afterInit() {
        super.afterInit();
        iPresenter.getVacates();
    }

    @Override
    protected IMineVacatePresenter createIPresenter() {
        return new IMineVacatePresenterImpl();
    }

    @Override
    public void showVacates(List<VacateDto> vacateDtos) {
        if(adapter == null)
            adapter = new VacateAdapter(getActivity(),vacateDtos,R.layout.item_vacate_layout);
        lv_vacates.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            List<ImageFile> selected = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            File files[] = new File[selected.size()];
            int i = 0;
            for (ImageFile file:selected)
            {
                String path = file.getPath();
                files[i] = new File(path);
                i++;
            }
            final Call call = RequestCenter.uploadPhoto(requestCode, files, new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    CheckResult<List<Photo>> cr = (CheckResult<List<Photo>>) responseObj;
                    if (!cr.isSuccess()) {
                        onFailure(cr.getError());
                        return;
                    }
                    List<VacateDto> list = adapter.getList();
                    for (VacateDto dto : list) {
                        if (dto.getId().intValue() == requestCode) {
                            dto.getPhotos().addAll(cr.getData());
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                    LoadingCreator.stopLoading();
                }

                @Override
                public void onFailure(Object error) {
                    ToastUtil.showShort("上传失败");
                    LoadingCreator.stopLoading();
                }
            });
            LoadingCreator.showLoading(getActivity(), dialogInterface -> {
                if(call != null && !call.isCanceled())
                    call.cancel();
            });
        }
    }

    @Override
    public void getVacatesFail(String error) {
        ToastUtil.showShort(error);
    }
}
