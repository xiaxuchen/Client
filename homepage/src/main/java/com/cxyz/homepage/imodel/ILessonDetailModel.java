package com.cxyz.homepage.imodel;

import android.content.Intent;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.homepage.dto.CheckHistoryDto;

import java.util.List;

/**
 * Created by Administrator on 2019/2/6.
 */

public abstract class ILessonDetailModel extends IBaseModel{

    /**
     * 获取课程考勤历史记录
     * @param id 课程id
     * @param start 起始位置
     */
    public abstract void getLessonHistories(Integer id, Integer start, IBaseModel.ModelListener<List<CheckHistoryDto>,String> listStringModelListener);

    /**
     * 更新课程编号
     * @param id 课程id
     * @param num 课程编号
     * @param listener
     */
    public abstract void updateLessonNum(Integer id,String num,IBaseModel.ModelListener<String,String> listener);
}
