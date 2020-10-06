package com.zgzx.metaphysics.controller.views.core;

public class CallbackResultDecorator<T> implements ICallbackResult<T> {

    private ICallbackResult<T> mCallbackResult;

    public CallbackResultDecorator(ICallbackResult<T> callbackResult) {
        this.mCallbackResult = callbackResult;
    }

    @Override
    public void successful(T result) {
        mCallbackResult.successful(result);
    }

    @Override
    public void loading() {
        mCallbackResult.loading();
    }

    @Override
    public void failure(Throwable throwable) {
        mCallbackResult.failure(throwable);
    }

    @Override
    public void complete() {
        mCallbackResult.complete();
    }

    @Override
    public void offline() {
        mCallbackResult.offline();
    }

}
