package com.zgzx.metaphysics.controller.views.core;

public interface ISingleRequestView<T> extends IStatusView {

    default void result() {

    }


    default void result(T result) {

    }

}
