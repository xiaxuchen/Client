package com.cxyz.homepage.ipresenter.impl;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.commons.utils.LogUtil;
import com.cxyz.homepage.dto.GradeLessonDto;
import com.cxyz.homepage.dto.GradeTaskDto;
import com.cxyz.homepage.imodel.IExportModel;
import com.cxyz.homepage.imodel.IMineLessonModel;
import com.cxyz.homepage.imodel.impl.IMineLessonModelImpl;
import com.cxyz.homepage.ipresenter.IMineLessonPresenter;

import java.util.List;

/**
 * Created by Administrator on 2019/2/2.
 */

public class IMineLessonPresenterImpl extends IMineLessonPresenter {
    @Override
    public void getGradeLessons() {
        mIView.showLoadingView();
        mIModle.getGradeTasks(new IBaseModel.ModelListener<List<GradeLessonDto>, String>() {
            @Override
            public void onSuccess(List<GradeLessonDto> data) {
                LogUtil.d(data);
                mIView.showGradeLessons(data);
                mIView.hideLoadingView();
            }

            @Override
            public void onFail(String s) {
                mIView.showError(s);
                mIView.hideLoadingView();
            }
        });
    }

    @Override
    public IMineLessonModel createModel() {
        return new IMineLessonModelImpl();
    }
}
