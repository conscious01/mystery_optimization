package com.zgzx.metaphysics.ui.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected Context  mContext;
    private   View     mRootView;
    private Unbinder mUnbinder;

    private boolean initialDisplay = true; // 初次显示
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }

        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initialize(savedInstanceState);

        // 初始化事件总线
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initStatusBar(getActivity(), initialDisplay);
            initialDisplay = false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mUnbinder.unbind();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected boolean useEventBus() {
        return false;
    }

    protected <T extends View> T findViewById(int id) {
        return mRootView.findViewById(id);
    }

    protected void startActivityAndFinish(Class<?> cls){
        startActivity(cls).finish();
    }

    protected Activity startActivity(Class<?> cls) {
        if (getActivity() != null) {
            getActivity().startActivity(new Intent(getActivity(), cls));
        }

        return getActivity();
    }

    protected void initStatusBar(Activity activity, boolean initialDisplay) {

    }

    protected abstract int getLayoutId();

    protected abstract void initialize(Bundle savedInstanceState);

}
