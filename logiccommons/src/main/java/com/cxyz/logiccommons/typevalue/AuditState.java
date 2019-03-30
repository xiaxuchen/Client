package com.cxyz.logiccommons.typevalue;

public interface AuditState {

    /**
     * 待审核
     */
    int WAIT_AUDIT = 0;

    /**
     * 审核成功
     */
    int SUCCESS = 1;

    /**
     * 审核失败
     */
    int FAIL = -1;

    /**
     * 只是请假条不是请假(条件是没有请假审核)
     */
    int ONLY_VACATE = 2;
}
