package com.zgzx.metaphysics.utils


import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.zgzx.metaphysics.R

/**
 * created by alexwu
 * on 2020/10/1 23:50
 * @description
 */

@TargetApi(Build.VERSION_CODES.N)
object AppNotification {
    //通知ID
    //对于通知来说ID相同即为同一条通知，如果通知ID已存在，则更新通知内容，否则发送一条新的通知
    //这里为了每次都能发送一条新的通知，对ID进行累加
    private var id = 1

    //影视类通知渠道
    const val msgChannelId = "0x1" //通知渠道ID
    const val msgChannelName = "消息推送" //通知渠道名称，显示在手机上该APP的通知管理中
    const val mediaChannelImportance = NotificationManager.IMPORTANCE_HIGH //通知渠道重要性

    //美食类通知渠道
    const val foodChannelId = "0x2" //通知渠道ID
    const val foodChannelName = "美食" //通知渠道名称，显示在手机上该APP的通知管理中
    const val foodChannelImportance = NotificationManager.IMPORTANCE_DEFAULT //通知渠道重要性

    /**
     * 创建通知渠道
     *
     * @param applicationContext 上下文
     * @param channelId 渠道ID
     * @param channelIdName 渠道名称，显示在手机上该APP的通知管理中
     * @param channelIdImportance 渠道重要程度
     */
    @JvmStatic
     fun createNotificationChannel(applicationContext: Context, channelId: String,
                                  channelIdName: String, channelIdImportance: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = applicationContext.getSystemService(
                    Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(channelId, channelIdName,
                    channelIdImportance)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    /**
     * 发送通知，根据需要进行扩展
     *
     * @param context 上下文
     * @param channelId 渠道ID（必须对应已创建的渠道ID）
     * @param title 通知标题
     * @param text 通知内容
     * @param smallIcon 通知小图标（显示在状态栏中的）,必须设置
     * @param largeIcon 通知大图标（下拉状态栏可见，显示在通知栏中），
     *         注意：这里的图片ID不能是mipmap文件夹下的，因为BitmapFactory.decodeResource方法只能
     *         获取到 drawable, sound, and raw resources;
     * @param pi 点击通知打开的页面
     *
     */
    @JvmStatic
    fun sendNotification(context: Context, channelId: String, title: String,
                         text: String, smallIcon: Int, largeIcon: Int, pi: PendingIntent) {
        //判断通知是否开启
        if (!isNotificationEnabled(context)) {
            AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.warm_hint))
                    .setMessage(context.getString(R.string.sure_to_open_notification))
                    .setPositiveButton(context.getString(R.string.confirm)) { _, _ ->
                        openNotification(context)
                    }
                    .setNegativeButton(context.getString(R.string.cancel), null)
                    .show()
            return
        }
        //判断某个渠道的通知是否开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!isNotificationChannelEnabled(context, channelId)) {
                val message = when (channelId) {
                    msgChannelId -> msgChannelName
                    foodChannelId -> foodChannelName
                    else -> ""
                }
                AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.warm_hint))
//                        .setMessage("是否开启${message}通知？")
                        .setMessage(context.getString(R.string.sure_to_open_notification))

                        .setPositiveButton(context.getString(R.string.confirm)) { _, _ ->
                            openNotificationChannel(context, channelId)
                        }
                        .setNegativeButton(context.getString(R.string.cancel), null)
                        .show()
                return
            }
        }
        //发送通知
        val notification = NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(text)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(smallIcon)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, largeIcon))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build()
        val notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id++, notification)
    }

    /**
     * 判断App通知是否开启
     * 注意这个方法判断的是通知总开关，如果APP通知被关闭，则其下面的所有通知渠道也被关闭
     */
    @JvmStatic
    fun isNotificationEnabled(context: Context): Boolean {
        val notificationManagerCompat = NotificationManagerCompat
                .from(context)
        return notificationManagerCompat.areNotificationsEnabled()
    }

    /**
     * 判断APP某个通知渠道的通知是否开启
     */
    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.O)
    fun isNotificationChannelEnabled(context: Context, channelId: String): Boolean {
        val notificationManager = context.getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = notificationManager.getNotificationChannel(channelId)
        return channel.importance != NotificationManager.IMPORTANCE_NONE
    }

    /**
     * 打开通知设置页面
     */
    @JvmStatic
    fun openNotification(context: Context) {
        val packageName = context.packageName
        val uid = context.applicationInfo.uid
        val intent = Intent()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, uid)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("app_package", packageName)
                intent.putExtra("app_uid", uid)
            }
            Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT -> {
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.data = Uri.parse("package:$packageName")
            }
            else -> intent.action = Settings.ACTION_SETTINGS
        }
        context.startActivity(intent)
    }

    /**
     * 打开通知渠道设置页面
     */
    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.O)
    fun openNotificationChannel(context: Context, channelId: String) {
        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId)
        context.startActivity(intent)
    }
}