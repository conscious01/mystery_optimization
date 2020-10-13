package com.zgzx.metaphysics;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.zgzx.metaphysics.rongmessage.LuckMessage;
import com.zgzx.metaphysics.rongmessage.OrderMessage;
import com.zgzx.metaphysics.rongmessage.SystemMessage;
import com.zgzx.metaphysics.utils.AppNotificationJava;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {
    @Override
    public boolean onReceived(Message message, int i) {


        if (message.getContent() instanceof OrderMessage) {

            OrderMessage orderMessage = (OrderMessage) message.getContent();
            if (orderMessage != null) {
                if (!TextUtils.isEmpty(orderMessage.getTitle())) {
                    String trashNoti = orderMessage.getTitle();
                    if (trashNoti.equals("22222") || trashNoti.equals("11111")) {

                    } else {

                        LogUtils.i(message.getContent());
                        AppNotificationJava.sendOrderNotification((OrderMessage) (message.getContent()));
                    }
                }
            }


        } else if (message.getContent() instanceof SystemMessage) {

            SystemMessage orderMessage = (SystemMessage) message.getContent();
            if (orderMessage != null) {
                if (!TextUtils.isEmpty(orderMessage.getTitle())) {
                    String trashNoti = orderMessage.getTitle();
                    if (trashNoti.equals("22222") || trashNoti.equals("11111")) {

                    } else {

                        LogUtils.i(message.getContent());
                        AppNotificationJava.sendSystemNotification((SystemMessage) (message.getContent()));
                    }
                }
            }


        } else if (message.getContent() instanceof LuckMessage) {
            LogUtils.i(message.getContent());
            AppNotificationJava.sendLuckNotification((LuckMessage) (message.getContent()));
        }

        //落地推送-----------及时消息，获取不到push的具体内容
        LogUtils.aTag("PUSH-application", message.toString());

        return false;
    }

}
