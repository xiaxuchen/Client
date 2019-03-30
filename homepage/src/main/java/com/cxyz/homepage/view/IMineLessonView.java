package com.cxyz.homepage.view;

import com.cxyz.commons.IView.IBaseView;
import com.cxyz.homepage.dto.GradeLessonDto;
import com.cxyz.homepage.dto.GradeTaskDto;

import java.util.List;

/**
 * Created by Administrator on 2019/2/2.
 */

public interface IMineLessonView extends IBaseView {

    /**
     * 显示信息
     * @param data
     */
    void showGradeLessons(List<GradeLessonDto> data);

    /**
     * 显示错误信息
     * @param error
     */
    void showError(Object error);
}
