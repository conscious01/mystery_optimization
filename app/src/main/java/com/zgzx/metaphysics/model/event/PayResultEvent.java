package com.zgzx.metaphysics.model.event;

/**
 * 支付结果事件
 * Created by wison on 2017/5/10.
 */
public class PayResultEvent {

    private boolean isSuccess;

    public PayResultEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }


}
