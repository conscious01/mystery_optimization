package com.zgzx.metaphysics;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.mondo.logger.AndroidLogAdapter;
import com.mondo.logger.Logger;
import com.qbw.spm.P;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zgzx.metaphysics.utils.ActivityNavigateManager;
import com.zgzx.metaphysics.utils.AppNotificationJava;
import com.zgzx.metaphysics.utils.CrashHelper;

import java.util.Locale;

import androidx.multidex.MultiDex;
import io.reactivex.plugins.RxJavaPlugins;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.push.RongPushClient;
import io.rong.push.pushconfig.PushConfig;

import static com.zgzx.metaphysics.BuildConfig.FLAVOR;

/**
 * 主程序入口
 * Data:    2020-07-15
 * Author:  Lee
 * E-mail:  1428728432@qq.com
 */
public class MetaphysicsApplication extends Application {
    private static final String TAG = "Application";
    //TODO 融云区分线上和测试的key
    public static String RONG_APPKEY = "sfci50a7sxpfi";

    static {

        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) ->
                layout.setReboundDuration(300)
                        .setEnableAutoLoadMore(true)
                        .setDragRate(0.4f)
                        .setEnableOverScrollBounce(true)
                        .setDisableContentWhenLoading(false)
        );

        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) ->
                new ClassicsHeader(context)
                        .setTextSizeTitle(12)
                        .setEnableLastTime(false)
                        .setDrawableSize(15)
                        .setDrawableMarginRight(6)
                        .setSpinnerStyle(SpinnerStyle.Translate)
                        .setFinishDuration(500));

        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) ->
                new ClassicsFooter(context).setTextSizeTitle(12)
                        .setFinishDuration(500)
                        .setDrawableSize(15)
                        .setDrawableMarginRight(8)
                        .setSpinnerStyle(SpinnerStyle.Translate));

        // 日志框架
        Logger.addLogAdapter(new AndroidLogAdapter() {

            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }

        });
    }


    public static MetaphysicsApplication sInstance;

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        // 测试、调试工具
        DoraemonKit.install(this);

        // 页面导航
        registerActivityLifecycleCallbacks(ActivityNavigateManager.instance());

        // 友盟
        initUM();
        // 处理RxJava取消订阅异常无法抛出导致闪退问题
        initializeRxJavaException();
        initLanguage();
        initRongPush();

        P.init(this, FLAVOR.equals("web"));

        //创建影视通知渠道
        AppNotificationJava.createNotificationChannel(this,
                AppNotificationJava.msgChannelId,
                AppNotificationJava.msgChannelName,
                AppNotificationJava.mediaChannelImportance);

    }

    private void initRongPush() {

        PushConfig config = new PushConfig.Builder()
                .enableMiPush("2882303761518684442", "zluerYz9Ildfw++3K/WJqQ==")
                .enableHWPush(true)  // 配置华为推送
                .enableMeiZuPush("3337892", "2ea4b23de30841cbbba8252db3204ae6") //配置魅族推送
                .enableOppoPush("14de4398da8449aaac7e445c11a7a6ab",
                        "1d92ac80c66149d4a0f908833269102c")
                .enableVivoPush(true)

                .build();
        RongPushClient.setPushConfig(config);
        RongIMClient.init(this, RONG_APPKEY);
        RongIMClient.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            /**
             * 连接状态返回回调
             * @param status 状态值
             */
            @Override
            public void onChanged(ConnectionStatus status) {
                System.out.println(TAG + status.getMessage() + " ," + status.getValue());
            }
        });

        RongIMClient.getInstance().setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageWrapperListener() {
            /**
             * 接收实时或者离线消息。
             * 注意:
             * 1. 针对接收离线消息时，服务端会将 200 条消息打成一个包发到客户端，客户端对这包数据进行解析。
             * 2. hasPackage 标识是否还有剩余的消息包，left 标识这包消息解析完逐条抛送给 App 层后，剩余多少条。
             * 如何判断离线消息收完：
             * 1. hasPackage 和 left 都为 0；
             * 2. hasPackage 为 0 标识当前正在接收最后一包（200条）消息，left 为 0 标识最后一包的最后一条消息也已接收完毕。
             *
             * @param message    接收到的消息对象
             * @param left       每个数据包数据逐条上抛后，还剩余的条数
             * @param hasPackage 是否在服务端还存在未下发的消息包
             * @param offline    消息是否离线消息
             * @return 是否处理消息。 如果 App 处理了此消息，返回 true; 否则返回 false 由 SDK 处理。
             */
            @Override
            public boolean onReceived(final Message message, final int left, boolean hasPackage,
                                      boolean offline) {
                LogUtils.aTag("PUSH-application", message);
                LogUtils.aTag("PUSH-application", message.getContent().toString());
//                NotificationUtils.sendNoti(getContext(),message);
                return false;
            }
        });

    }


    private void initLanguage() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getCountry();
        Logger.i(language);
        if ("CN".equals(language)) {
            LocalConfigStore.getInstance().updateAcceptLanguage("zh-CN");
        } else if ("TW".equals(language) || "HK".equals(language)) {
            LocalConfigStore.getInstance().updateAcceptLanguage("zh-TW");
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private void initializeRxJavaException() {
        try {

            RxJavaPlugins.setErrorHandler(CrashHelper::generateCustomLog);

        } catch (Exception e) {
            CrashHelper.generateCustomLog(e);
        }
    }


    private void initUM() {
        // 统计
        UMConfigure.init(this, "5f214cddb4b08b653e8f6748", "umeng", UMConfigure.DEVICE_TYPE_PHONE
                , null);
        UMConfigure.setLogEnabled(BuildConfig.DEBUG);
        MobclickAgent.setSessionContinueMillis(3000 * 60);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        PlatformConfig.setWeixin("wx6e1840953b329781", "1353b43f76155a82a4f7c4226b3e831f");
    }

    public static Context getContext() {
        return sInstance;
    }

}
