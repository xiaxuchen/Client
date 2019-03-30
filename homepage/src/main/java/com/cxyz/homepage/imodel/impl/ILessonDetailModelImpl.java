package com.cxyz.homepage.imodel.impl;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.commons.utils.HttpUtil.listener.DisposeDataListener;
import com.cxyz.homepage.constant.RequestCenter;
import com.cxyz.homepage.dto.CheckHistoryDto;
import com.cxyz.homepage.imodel.ILessonDetailModel;
import com.cxyz.logiccommons.domain.CheckResult;

import java.util.List;

/**
 * Created by Administrator on 2019/2/6.
 */

public class ILessonDetailModelImpl extends ILessonDetailModel {
    @Override
    public void getLessonHistories(Integer id, Integer start, ModelListener<List<CheckHistoryDto>, String> listener) {
        addCall(RequestCenter.getLessonHistories(id, start, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                CheckResult<List<CheckHistoryDto>> cr = (CheckResult<List<CheckHistoryDto>>) responseObj;
                if(cr.isSuccess())
                    listener.onSuccess(cr.getData());
                else
                    listener.onFail(cr.getError());
            }

            @Override
            public void onFailure(Object error) {
                if(error instanceof Exception)
                    listener.onFail(((Exception) error).getMessage());
                else
                    listener.onFail(error.toString());
            }
        }));
    }

    @Override
    public void updateLessonNum(Integer id, String num, ModelListener<String, String> listener) {
        addCall(RequestCenter.updateLessonNum(id, num, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                CheckResult<String> cr = (CheckResult<String>) responseObj;
                if(cr.isSuccess())
                    listener.onSuccess(cr.getData());
                else
                    listener.onFail(cr.getError());
            }

            @Override
            public void onFailure(Object error) {
                if(error instanceof Exception)
                    listener.onFail(((Exception) error).getMessage());
                else
                    listener.onFail(error.toString());
            }
        }));
    }
}
