package com.cxyz.vac.ipresenter;

import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.vac.imodel.IVacDayModel;
import com.cxyz.vac.iview.IVacDayView;

/**
 * Created by Administrator on 2019/2/26.
 */

public abstract class IVacDayPresenter extends IBasePresenter<IVacDayModel,IVacDayView> {

    /**
     * 获取请假信息及照片
     * @param gradeId 班级id
     * @param from 开始时间
     * @param to 结束时间
     */
    public abstract void getVacatesInDates(Integer gradeId,String from,String to);
}
