package com.zgzx.metaphysics.utils;

import com.mondo.logger.Logger;
import com.umeng.umcrash.UMCrash;
import com.zgzx.metaphysics.BuildConfig;

/**
 * 崩溃捕捉对象
 */
public class CrashHelper {

    public static void generateCustomLog(Throwable t){
        Logger.e(t, t.getMessage());

        if (!BuildConfig.DEBUG){
            UMCrash.generateCustomLog(t, "手动抛出异常");
        }
    }

}
