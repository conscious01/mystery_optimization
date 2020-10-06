package com.zgzx.metaphysics.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * 页面导航
 */
public class ActivityNavigateManager implements Application.ActivityLifecycleCallbacks {

    private static ActivityNavigateManager sActivityManager;


    public static ActivityNavigateManager instance() {
        if (sActivityManager == null) {

            synchronized (ActivityNavigateManager.class) {

                if (sActivityManager == null) {
                    sActivityManager = new ActivityNavigateManager();
                }

            }
        }

        return sActivityManager;
    }


    /**
     * 跳转指定页面，关闭其他所有页面
     */
    public static void navigate(Context context, Class<?> clz) {
        context.startActivity(
                new Intent(context, clz)
                        .addFlags(FLAG_ACTIVITY_NEW_TASK)
        );

        for (Activity activity : sActivityManager.mActivityList) {
            if (activity.getClass() != clz) {
                activity.finish();
            }
        }
    }



    private ActivityNavigateManager() {
        this.mActivityList = new ArrayList<>();
    }

    List<Activity> mActivityList;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        mActivityList.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        mActivityList.remove(activity);
    }

}
