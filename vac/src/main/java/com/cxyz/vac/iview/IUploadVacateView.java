package com.cxyz.vac.iview;

import com.cxyz.commons.IView.IBaseView;

/**
 * Created by Administrator on 2019/2/15.
 */

public interface IUploadVacateView extends IBaseView {

    /**
     * 上传成功
     */
    void uploadSuccess();

    /**
     * 上传失败
     * @param error
     */
    void uploadFail(String error);
}
