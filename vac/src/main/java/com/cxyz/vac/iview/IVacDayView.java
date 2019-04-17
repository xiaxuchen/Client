package com.cxyz.vac.iview;

import com.cxyz.commons.IView.IBaseView;
import com.cxyz.logiccommons.domain.Vacate;

import java.util.List;

/**
 * Created by Administrator on 2019/2/26.
 */

public interface IVacDayView extends IBaseView {

    /**
     * 显示请假条
     * @param vacates
     */
    void showVacates(List<Vacate> vacates);

    /**
     * 显示错误信息
     * @param error 错误信息
     */
    void showError(String error);
}
