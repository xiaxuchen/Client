package com.cxyz.vac.imodel;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.logiccommons.domain.Vacate;

import java.util.List;

/**
 * Created by Administrator on 2019/2/26.
 */

public abstract class IVacDayModel extends IBaseModel {

    /**
     * 获取请假信息
     * @param gradeId
     * @param from
     * @param to
     * @param listener
     */
    public abstract void getVacatesInDates(Integer gradeId, String from, String to, ModelListener<List<Vacate>,String> listener);
}
