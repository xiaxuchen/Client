package com.cxyz.vac.ipresenter;

import com.cxyz.commons.IPresenter.IBasePresenter;
import com.cxyz.vac.dto.VacateDto;
import com.cxyz.vac.imodel.IUploadVacateModel;
import com.cxyz.vac.iview.IUploadVacateView;

import java.io.File;

/**
 * Created by Administrator on 2019/2/15.
 */

public abstract class IUploadVacatePresenter extends IBasePresenter<IUploadVacateModel,IUploadVacateView> {

    /**
     * 上传请假
     * @param dto 请假信息
     * @param files 文件
     */
    public abstract void uploadVacate(VacateDto dto,File[] files);
}
