package com.zgzx.metaphysics;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.zgzx.metaphysics.model.entity.NotificationEntity;

import org.greenrobot.eventbus.EventBus;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class SealNotificationReceiver extends PushMessageReceiver {


    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType,
                                                PushNotificationMessage pushMsg) {

        if (pushMsg != null && !pushMsg.getPushData().isEmpty()) {
            NotificationEntity notificationEntity
                    = new Gson().fromJson(pushMsg.getPushData(), NotificationEntity.class);

            EventBus.getDefault().post(notificationEntity);
        }
        LogUtils.aTag("PUSH-receiver", pushMsg);

        return true; // 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType,
                                                PushNotificationMessage pushNotificationMessage) {
        return false; // 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面; 返回 true, 则由您自定义处理逻辑。
    }

    /**
     * 第三方push状态回调
     *
     * @param pushType   push类型
     * @param action     当前的操作，连接或者获取token
     * @param resultCode 返回的错误码
     */
    @Override
    public void onThirdPartyPushState(PushType pushType, String action, long resultCode) {
        super.onThirdPartyPushState(pushType, action, resultCode);
    }
}