package com.cxyz.homepage.ipresenter;

import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.homepage.imodel.ILessonDetailModel;
import com.cxyz.homepage.iview.ILessonDetailView;

/**
 * Created by Administrator on 2019/2/6.
 */

public abstract class ILessonDetailPresenter extends IBasePresenter<ILessonDetailModel,ILessonDetailView> {

    /**
     * 获取课程考勤历史
     * @param id 课程id
     * @param start 起始位置,如果start为0或null,请求成功则回调ILessonHisotriesView的showHisotories,否则回调addMoreHistories
     */
    public abstract void getLessonHistories(Integer id,Integer start);

    /**
     * 更新课程编号
     * @param id 课程id
     * @param num 课程编号
     */
    public abstract void updateLessonNum(Integer id,String num);
}
