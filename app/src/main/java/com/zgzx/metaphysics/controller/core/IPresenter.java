package com.zgzx.metaphysics.controller.core;

import com.zgzx.metaphysics.controller.views.core.IView;

/**
 * base presenter
 */
public interface IPresenter<V extends IView> {

    /**
     * 绑定view
     */
    void setModelAndView(V view);

}
