package com.cxyz.homepage.ipresenter;

import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.homepage.imodel.IExportModel;
import com.cxyz.homepage.imodel.IMineLessonModel;
import com.cxyz.homepage.iview.IExportView;
import com.cxyz.homepage.view.IMineLessonView;

/**
 * Created by Administrator on 2019/1/2.
 */

public abstract class IMineLessonPresenter extends IBasePresenter<IMineLessonModel,IMineLessonView> {

    /**
     * 获取班级的任务信息
     */
    public abstract void getGradeLessons();

}
