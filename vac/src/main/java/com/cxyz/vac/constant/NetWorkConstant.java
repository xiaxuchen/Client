package com.cxyz.vac.constant;

import com.cxyz.logiccommons.constant.Constant;

/**
 * Created by Administrator on 2018/12/23.
 */

public interface NetWorkConstant {

    /**
     * 提交请假信息链接
     */
    String COMMIT_VAC_URL = Constant.ROOT_URL+"/vacate/vacate";

    /**
     * 获取请假信息审核
     */
    String GET_VACATES_TO_AUDIT = Constant.ROOT_URL+"/vacate/getVacatesToAudit";

    /**
     * 审核请假
     */
    String AUDIT_VACATE = Constant.ROOT_URL+"/vacate/auditVac";

    /**
     * 获取请假信息
     */
    String GET_VACATES = Constant.ROOT_URL+"/vacate/getVacates";

    /**
     * 上传请假条
     */
    String UPLOAD_VACATE = Constant.ROOT_URL+"/vacate/uploadVacate";

    /**
     * 获取假条的请求
     *
     */
    String VAC_PHOTO = Constant.ROOT_URL+"/resource/vac/photo/";

    /**
     * 删除图片
     */
    String DELETE_PHOTO = Constant.ROOT_URL+"/vacate/deletePhoto";

    /**
     * 上传图片
     */
    String UPLOAD_PHOTO = Constant.ROOT_URL+"/vacate/uploadPhoto";

    /**
     * 获取一定时间的请假信息（主要是请假条图片）
     */
    String GET_VACATE_IN_DATES = Constant.ROOT_URL+"/vacate/getVacateInDates";
}
