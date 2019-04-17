package com.cxyz.commons.IView;

/**
 * Created by Administrator on 2019/4/13.
 * 事件的共同抽取
 */

public interface ICommonView extends IBaseView {

    /**
     * 有事件发生时
     * @param type 事件类型
     * @param result 事件结果，成功或者失败
     * @param params 带回的信息
     */
    void onEvent(int type,boolean result,Object ... params);

}
