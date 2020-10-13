package com.zgzx.metaphysics.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.NotificationEntity;
import com.zgzx.metaphysics.rongmessage.LuckMessage;
import com.zgzx.metaphysics.rongmessage.OrderMessage;
import com.zgzx.metaphysics.rongmessage.SystemMessage;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.core.app.NotificationManagerCompat;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010" +
                "\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n" +
                "\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018" +
                "\u0002\n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002" +
                "\u0010\u0002J(\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2" +
                "\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042" +
                "\u0006\u0010\u0012\u001a\u00020\u0006H\u0007J\u0018\u0010\u0013\u001a\u00020" +
                "\u00142\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020" +
                "\u0004H\u0007J\u0010\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0015\u001a" +
                "\u00020\u000fH\u0007J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0015\u001a" +
                "\u00020\u000fH\u0007J\u0018\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0015\u001a" +
                "\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0004H\u0007J@\u0010\u0019\u001a" +
                "\u00020\r2\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020" +
                "\u00042\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020" +
                "\u00042\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020" +
                "\u00062\u0006\u0010\u001e\u001a\u00020\u001fH\u0007R\u000e\u0010\u0003\u001a" +
                "\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020" +
                "\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X" +
                "\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e" +
                "¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n" +
                "\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e" +
                "\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006 "},
        d2 = {"Lcom/zgzx/metaphysics/utils/AppNotificationJava;", "", "()V", "foodChannelId", "",
                "foodChannelImportance", "", "foodChannelName", "id", "mediaChannelImportance",
                "msgChannelId", "msgChannelName", "createNotificationChannel", "",
                "applicationContext", "Landroid/content/Context;", "channelId", "channelIdName",
                "channelIdImportance", "isNotificationChannelEnabled", "", "context",
                "isNotificationEnabled", "openNotification", "openNotificationChannel",
                "sendNotification", "title", "text", "smallIcon", "largeIcon", "pi", "Landroid" +
                "/app/PendingIntent;", "app"}
)
@TargetApi(24)
public class AppNotificationJava {
    private static int id;
    public static final String msgChannelId = "0x1";
    public static final String msgChannelName = "消息推送";
    public static final int mediaChannelImportance = 4;
    public static final String foodChannelId = "0x2";
    public static final String foodChannelName = "美食";
    public static final int foodChannelImportance = 3;
    public static final AppNotificationJava INSTANCE;

    public static final void createNotificationChannel(@NotNull Context applicationContext,
                                                       @NotNull String channelId,
                                                       @NotNull String channelIdName,
                                                       int channelIdImportance) {
        Intrinsics.checkParameterIsNotNull(applicationContext, "applicationContext");
        Intrinsics.checkParameterIsNotNull(channelId, "channelId");
        Intrinsics.checkParameterIsNotNull(channelIdName, "channelIdName");
        if (VERSION.SDK_INT >= 26) {
            Object var10000 = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
            if (var10000 == null) {
                throw new TypeCastException("null cannot be cast to non-null type android.app" +
                        ".NotificationManager");
            }

            NotificationManager notificationManager = (NotificationManager) var10000;
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    (CharSequence) channelIdName, channelIdImportance);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

    @JvmStatic
    public static final void sendNotification(@NotNull final Context context,
                                              @NotNull final String channelId,
                                              @NotNull String title, @NotNull String text,
                                              int smallIcon, int largeIcon,
                                              @NotNull PendingIntent pi) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(channelId, "channelId");
        Intrinsics.checkParameterIsNotNull(title, "title");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(pi, "pi");
        if (!isNotificationEnabled(context)) {
            (new Builder(context))
                    .setTitle(context.getString(R.string.warm_hint))
                    .setMessage(context.getString(R.string.sure_to_open_notification))
                    .setPositiveButton(context.getString(R.string.confirm),
                            (OnClickListener) (new OnClickListener() {
                                public final void onClick(DialogInterface $noName_0,
                                                          int $noName_1) {
                                    AppNotificationJava.openNotification(context);
                                }
                            })).setNegativeButton(context.getString(R.string.cancel),
                    (OnClickListener) null).show();
        } else if (VERSION.SDK_INT >= 26 && !isNotificationChannelEnabled(context, channelId)) {
            label29:
            {
                String var10;
                switch (channelId.hashCode()) {
                    case 49897:
                        if (channelId.equals("0x1")) {
                            var10 = "消息推送";
                            break label29;
                        }
                        break;
                    case 49898:
                        if (channelId.equals("0x2")) {
                            var10 = "美食";
                            break label29;
                        }
                }

                var10 = "";
            }

            (new Builder(context))
                    .setTitle(context.getString(R.string.warm_hint))
                    .setMessage(context.getString(R.string.sure_to_open_notification))
                    .setPositiveButton(context.getString(R.string.confirm),
                            (OnClickListener) (new OnClickListener() {
                                public final void onClick(DialogInterface $noName_0,
                                                          int $noName_1) {
                                    AppNotificationJava.openNotificationChannel(context, channelId);
                                }
                            })).setNegativeButton((CharSequence) context.getString(R.string.cancel), (OnClickListener) null).show();
        } else {
            Notification notification = (new androidx.core.app.NotificationCompat.Builder(context
                    , channelId)).
                    setContentTitle((CharSequence) title).setContentText((CharSequence) text).
                    setWhen(System.currentTimeMillis()).setSmallIcon(smallIcon).
                    setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIcon)).
                    setContentIntent(pi).setAutoCancel(true).build();
            Object var10000 = context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (var10000 == null) {
                throw new TypeCastException("null cannot be cast to non-null type android.app" +
                        ".NotificationManager");
            } else {
                NotificationManager notificationManager = (NotificationManager) var10000;
                int var9;
                id = (var9 = id) + 1;
                notificationManager.notify(var9, notification);
            }
        }
    }

    @JvmStatic
    public static final boolean isNotificationEnabled(@NotNull Context context) {

        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(context);
        return notificationManagerCompat.areNotificationsEnabled();

//        Intrinsics.checkParameterIsNotNull(context, "context");
//        NotificationManagerCompat var10000 = NotificationManagerCompat.from(context);
//        Intrinsics.checkExpressionValueIsNotNull(var10000, "NotificationManagerCompa…
//        .from(context)");
//        NotificationManagerCompat notificationManagerCompat = var10000;
//        return notificationManagerCompat.areNotificationsEnabled();
    }

    @JvmStatic
    @RequiresApi(26)
    public static final boolean isNotificationChannelEnabled(@NotNull Context context,
                                                             @NotNull String channelId) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(channelId, "channelId");
        Object var10000 = context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.app" +
                    ".NotificationManager");
        } else {
            NotificationManager notificationManager = (NotificationManager) var10000;
            NotificationChannel channel = notificationManager.getNotificationChannel(channelId);
            Intrinsics.checkExpressionValueIsNotNull(channel, "channel");
            return channel.getImportance() != 0;
        }
    }

    @JvmStatic
    public static final void openNotification(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        String packageName = context.getPackageName();
        int uid = context.getApplicationInfo().uid;
        Intent intent = new Intent();
        if (VERSION.SDK_INT >= 26) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", packageName);
            Intrinsics.checkExpressionValueIsNotNull(intent.putExtra("android.provider.extra" +
                    ".CHANNEL_ID", uid), "intent.putExtra(Settings.EXTRA_CHANNEL_ID, uid)");
        } else if (VERSION.SDK_INT >= 21) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", packageName);
            Intrinsics.checkExpressionValueIsNotNull(intent.putExtra("app_uid", uid), "intent" +
                    ".putExtra(\"app_uid\", uid)");
        } else if (VERSION.SDK_INT == 19) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("package:" + packageName));
        } else {
            intent.setAction("android.settings.SETTINGS");
        }

        context.startActivity(intent);
    }

    @JvmStatic
    @RequiresApi(26)
    public static final void openNotificationChannel(@NotNull Context context,
                                                     @NotNull String channelId) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(channelId, "channelId");
        Intent intent = new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
        intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        intent.putExtra("android.provider.extra.CHANNEL_ID", channelId);
        context.startActivity(intent);
    }

    private AppNotificationJava() {
    }

    static {
        AppNotificationJava var0 = new AppNotificationJava();
        INSTANCE = var0;
        id = 1;
    }


    public static void sendOrderNotification(OrderMessage msg) {
        if (msg != null) {
            NotificationEntity notificationEntity = new NotificationEntity(msg.getId(),
                    msg.getTitle(), msg.getContent(), msg.getOrderId()
                    , msg.getOpenType(), msg.getTime(), msg.getExtra(), msg.getPushdata());
            EventBus.getDefault().post(notificationEntity);
        }
    }



    public static void sendSystemNotification(SystemMessage msg) {
        if (msg != null) {
            NotificationEntity notificationEntity = new NotificationEntity(0,
                    msg.getTitle(), msg.getContent(), -1
                    , -1, msg.getTime(), msg.getExtra(), msg.getPushdata());
            EventBus.getDefault().post(notificationEntity);
        }
    }

    public static void sendLuckNotification(LuckMessage msg) {
        if (msg != null) {
            NotificationEntity notificationEntity = new NotificationEntity(0,
                    msg.getTitle(), msg.getContent(), -1
                    , -1, msg.getTime(), msg.getExtra(), msg.getPushdata());
            EventBus.getDefault().post(notificationEntity);
        }
    }


}
