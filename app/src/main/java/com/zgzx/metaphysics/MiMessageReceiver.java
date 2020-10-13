package com.zgzx.metaphysics;


import android.content.Context;
import android.content.Intent;

import com.xiaomi.mipush.sdk.MiPushMessage;
import com.zgzx.metaphysics.ui.activities.SplashActivity;

public class MiMessageReceiver extends io.rong.push.platform.mi.MiMessageReceiver {

    //用不到
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {

        super.onNotificationMessageClicked(context, message);
        Intent intent = new Intent(context, SplashActivity.class);
        context.startActivity(intent);
    }
}
