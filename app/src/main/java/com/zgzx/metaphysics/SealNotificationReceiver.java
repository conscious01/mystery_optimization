package com.zgzx.metaphysics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.zgzx.metaphysics.ui.activities.SplashActivity;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class SealNotificationReceiver extends PushMessageReceiver {

    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType,
                                                PushNotificationMessage pushMsg) {
        //不落地推送
//        if (pushMsg != null && !pushMsg.getPushData().isEmpty()) {
//            NotificationEntity notificationEntity
//                    = new Gson().fromJson(pushMsg.getPushData(), NotificationEntity.class);
//
//            EventBus.getDefault().post(notificationEntity);
//        }else {
//            NotificationEntity notificationEntity = new NotificationEntity(0,
//                    pushMsg.getObjectName(), pushMsg.getPushContent(), 0, -1, 0, "", "");
//            EventBus.getDefault().post(notificationEntity);
//
//        }


        return true; // 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType,
                                                PushNotificationMessage pushNotificationMessage) {


        //小米手机点击通知栏的方式
        Intent intent = new Intent(context, SplashActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("appData", pushNotificationMessage.getPushData());
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        LogUtils.aTag("PUSH-receiver", pushNotificationMessage);
        return true;


//        return false; // 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面; 返回 true, 则由您自定义处理逻辑。
    }

    /**
     * 第三方push状态回调
     *
     * @param pushType   push类型
     * @param action     当前的操作，连接或者获取token
     * @param resultCode 返回的错误码
     */
    public static boolean needUpdate = false;

    @Override
    public void onThirdPartyPushState(PushType pushType, String action, long resultCode) {
        super.onThirdPartyPushState(pushType, action, resultCode);
        if (pushType.equals(PushType.HUAWEI)) {
            if (resultCode == CommonCode.ErrorCode.CLIENT_API_INVALID) {
                //设置标记位，引导用户升级
                needUpdate = true;
            }
        }
    }
}