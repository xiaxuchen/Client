package com.cxyz.homepage.ipresenter.impl;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.homepage.dto.CheckHistoryDto;
import com.cxyz.homepage.imodel.ILessonDetailModel;
import com.cxyz.homepage.imodel.impl.ILessonDetailModelImpl;
import com.cxyz.homepage.ipresenter.ILessonDetailPresenter;

import java.util.List;

/**
 * Created by Administrator on 2019/2/6.
 */

public class ILessonDetailPresenterImpl extends ILessonDetailPresenter {
    @Override
    public void getLessonHistories(Integer id, Integer start) {
        if(start == null || start == 0)
            mIView.showListLoading();
        else
            mIView.showLoadingView();
        mIModle.getLessonHistories(id, start, new IBaseModel.ModelListener<List<CheckHistoryDto>, String>() {
            @Override
            public void onSuccess(List<CheckHistoryDto> data) {
                if(data == null || data.isEmpty())
                    onFail("没有更多历史记录了");
                if(start == null || start == 0)
                    mIView.showHistories(data);
                else
                    mIView.addMoreHistories(data);
                mIView.hideLoadingView();

            }

            @Override
            public void onFail(String s) {
                mIView.showError(s,!(start==null || start==0));
                mIView.hideLoadingView();
            }
        });
    }

    @Override
    public void updateLessonNum(Integer id, String num) {
        mIView.showLoadingView();
        mIModle.updateLessonNum(id, num, new IBaseModel.ModelListener<String, String>() {
            @Override
            public void onSuccess(String data) {
                mIView.showUpdatedNum(num);
                mIView.hideLoadingView();
            }

            @Override
            public void onFail(String s) {
                mIView.updateNumError(s);
                mIView.hideLoadingView();
            }
        });
    }

    @Override
    public ILessonDetailModel createModel() {
        return new ILessonDetailModelImpl();
    }
}
