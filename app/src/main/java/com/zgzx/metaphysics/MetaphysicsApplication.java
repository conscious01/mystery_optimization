package com.zgzx.metaphysics;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
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
import com.zgzx.metaphysics.rongmessage.LuckMessage;
import com.zgzx.metaphysics.rongmessage.LuckMessageItemProvider;
import com.zgzx.metaphysics.rongmessage.OrderMessage;
import com.zgzx.metaphysics.rongmessage.OrderMessageItemProvider;
import com.zgzx.metaphysics.rongmessage.SystemMessage;
import com.zgzx.metaphysics.rongmessage.SystemMessageItemProvider;
import com.zgzx.metaphysics.utils.ActivityNavigateManager;
import com.zgzx.metaphysics.utils.AppNotificationJava;
import com.zgzx.metaphysics.utils.CrashHelper;

import java.util.Locale;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;
import io.reactivex.plugins.RxJavaPlugins;
import io.rong.imkit.RongIM;
import io.rong.imlib.AnnotationNotFoundException;
import io.rong.imlib.RongIMClient;
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
    public static String RONG_APPKEY_TEST = "sfci50a7sxpfi";
    public static String RONG_APPKEY_OFFICIAL = "y745wfm8yhp7v";

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

        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationObserver());

    }


    public static MetaphysicsApplication sInstance;

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

//        // 测试、调试工具
//        DoraemonKit.install(this);

        // 页面导航
        registerActivityLifecycleCallbacks(ActivityNavigateManager.instance());

        // 友盟
        initUM();
        // 处理RxJava取消订阅异常无法抛出导致闪退问题
        initializeRxJavaException();
        // initLanguage();
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
                .enableHWPush(true)  // 配置华为推送
                .enableMiPush("2882303761518684442", "5841868418442")
                .enableMeiZuPush("3337892", "2ea4b23de30841cbbba8252db3204ae6") //配置魅族推送
                .enableOppoPush("14de4398da8449aaac7e445c11a7a6ab",
                        "1d92ac80c66149d4a0f908833269102c")
                .enableVivoPush(true)
                .build();
        RongPushClient.setPushConfig(config);
        RongIMClient.init(this, RONG_APPKEY_TEST);


//        注册自定义消息类型
        //订单
        try {
            RongIMClient.registerMessageType(OrderMessage.class);
        } catch (AnnotationNotFoundException e) {
            e.printStackTrace();
        }
        RongIM.registerMessageTemplate(new OrderMessageItemProvider());
        //系统
        try {
            RongIMClient.registerMessageType(SystemMessage.class);
        } catch (AnnotationNotFoundException e) {
            e.printStackTrace();
        }
        RongIM.registerMessageTemplate(new SystemMessageItemProvider());

        //运势
        try {
            RongIMClient.registerMessageType(LuckMessage.class);
        } catch (AnnotationNotFoundException e) {
            e.printStackTrace();
        }
        RongIM.registerMessageTemplate(new LuckMessageItemProvider());


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

        RongIMClient.setOnReceiveMessageListener(new MyReceiveMessageListener());

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


    public static boolean mIfAppOpening = false;

    static class ApplicationObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void onForeground() {
            LogUtils.aTag(TAG, "onForeground!");
            mIfAppOpening = true;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onBackground() {
            LogUtils.aTag(TAG, "onBackground!");
            mIfAppOpening = false;

        }
    }


}
