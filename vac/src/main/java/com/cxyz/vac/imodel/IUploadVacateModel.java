package com.cxyz.vac.imodel;

import com.cxyz.commons.IModel.IBaseModel;
import com.cxyz.logiccommons.domain.Vacate;
import com.cxyz.vac.dto.VacateDto;

import java.io.File;

/**
 * Created by Administrator on 2019/2/15.
 */

public abstract class IUploadVacateModel extends IBaseModel {

    /**
     * 上传请假条
     * @param vacateDto 请假信息
     * @param files 请假照片
     * @param listener
     */
    public abstract void uploadVacate(VacateDto vacateDto, File files[], ModelListener<String,String> listener);
}
