package com.zgzx.metaphysics.controller.views.core;

public interface ICallbackResult<T> extends IStatusView {

    void successful(T result);

}
