package com.cxyz.homepage.iview;

import com.cxyz.commons.IView.IBaseView;
import com.cxyz.homepage.dto.CheckHistoryDto;

import java.util.List;

/**
 * Created by Administrator on 2019/2/6.
 */

public interface ILessonDetailView extends IBaseView {

    /**
     * 显示历史记录
     */
    void showHistories(List<CheckHistoryDto> data);

    /**
     * 添加更多历史考勤记录
     */
    void addMoreHistories(List<CheckHistoryDto> data);

    /**
     * 显示错误信息
     * @param error
     */
    void showError(Object error,boolean loadMore);

    /**
     * 显示更新后的编号
     * @param num 课程编号
     */
    void showUpdatedNum(String num);

    /**
     * 显示加载list
     */
    void showListLoading();

    /**
     * 显示更新编号错误
     * @param error
     */
    void updateNumError(String error);
}
