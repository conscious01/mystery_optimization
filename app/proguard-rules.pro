-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆
-keep class com.zgzx.metaphysics.city_time_picker.** { *; }
-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

-keepattributes Signature


## ----------------------------------
##      OkHttp
## ----------------------------------
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**


## ----------------------------------
##      Okio
## ----------------------------------
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }


## ----------------------------------
##      Glide
## ----------------------------------

-keep class com.bumptech.glide.** {*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.**

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder


## ----------------------------------
##      Gson
## ----------------------------------
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.** { *;}
-dontwarn com.zgzx.metaphysics.model.entity.**
-dontwarn com.zgzx.metaphysics.model.params.**
-dontwarn com.zgzx.metaphysics.xpopupext.bean.**
-keep class com.zgzx.metaphysics.model.entity.** { *;}
-keep class com.zgzx.metaphysics.model.params.** { *;}
-keep class com.zgzx.metaphysics.xpopupext.bean.** { *;}

## ----------------------------------
##      versionchecklib(版本更新)
## ----------------------------------
 -keepattributes *Annotation*
 -keepclassmembers class * {
     @org.greenrobot.eventbus.Subscribe <methods>;
 }
 -keep enum org.greenrobot.eventbus.ThreadMode { *; }
 -keep class com.allenliu.versionchecklib.**{*;}


## ----------------------------------
##      Rxjava/RxAndroid
## ----------------------------------
 -dontwarn sun.misc.**
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode producerNode;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
 }
 -dontnote rx.internal.util.PlatformDependent


## ----------------------------------
##      BaseRecyclerViewAdapterHelper
## ----------------------------------
 -keep class com.chad.library.adapter.** {
 *;
 }
 -keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
 -keep public class * extends com.chad.library.adapter.base.BaseViewHolder
 -keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
      <init>(...);
 }
 -keepattributes InnerClasses


## ----------------------------------
##      友盟
## ----------------------------------
 -keep class com.umeng.** {*;}
 -keep class com.uc.** {*;}
 -keepclassmembers class * {
    public <init> (org.json.JSONObject);
 }
 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }
 -keep class com.zui.** {*;}
 -keep class com.miui.** {*;}
 -keep class com.heytap.** {*;}
 -keep class a.** {*;}
 -keep class com.vivo.** {*;}
-keep class com.uc.** {*;}
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**

-keep public class com.umeng.socialize.* {*;}


-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.weixin.handler.**
-keep class com.umeng.weixin.handler.*
-keep class com.umeng.qq.handler.**
-keep class com.umeng.qq.handler.*
-keep class UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class com.tencent.mm.sdk.** {
   *;
}
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}

-keep class com.twitter.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep class com.kakao.** {*;}
-dontwarn com.kakao.**
-keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
}
-keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-keep class com.umeng.socialize.impl.ImageImpl {*;}
-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
-keepattributes Signature
 -keep public class com.zgzx.metaphysics.R$*{
 public static final int *;
 }
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}

## ----------------------------------
##      XPOPUP
## ----------------------------------
-dontwarn com.lxj.xpopup.widget.**
-keep class com.lxj.xpopup.widget.**{*;}


## ----------------------------------
##      CalendarView(日历)
## ----------------------------------
-keep class com.zgzx.metaphysics.widgets.calendar.CalendarMonthView {
    public <init>(android.content.Context);
}
-keep class com.zgzx.metaphysics.widgets.calendar.CalendarWeekBar {
    public <init>(android.content.Context);
}
-keep class com.zgzx.metaphysics.widgets.calendar.CalendarBlessWeekBar {
    public <init>(android.content.Context);
}
-keep class com.zgzx.metaphysics.widgets.calendar.CalendarWeekView {
    public <init>(android.content.Context);
}
-keep class com.zgzx.metaphysics.widgets.calendar.YearView {
    public <init>(android.content.Context);
}

-keep class com.zgzx.metaphysics.widgets.calendar.CalendarBlessMothView {
    public <init>(android.content.Context);
}

-keep class com.zgzx.metaphysics.widgets.calendar.CalendarBlessWeekBar {
    public <init>(android.content.Context);
}
-dontwarn  com.apng.**
-keep class com.apng.** { *; }
#融云
    -keepattributes Exceptions,InnerClasses

    -keepattributes Signature

    -keep class io.rong.** {*;}
    -keep class cn.rongcloud.** {*;}
    -keep class * implements io.rong.imlib.model.MessageContent {*;}
    -dontwarn io.rong.push.**
    -dontnote com.xiaomi.**
    -dontnote com.google.android.gms.gcm.**
    -dontnote io.rong.**

    # Location
    -keep class com.amap.api.**{*;}
    -keep class com.amap.api.services.**{*;}

    -ignorewarnings
    -keep class com.zgzx.metaphysics.SealNotificationReceiver {*;}

#sp 工具类
-keep class com.qbw.spm.*
-keep class com.github.chrisbanes.photoview.*

#华为推送
-ignorewarning
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}

-keep class com.huawei.gamebox.plugin.gameservice.**{*;}

-keep public class com.huawei.android.hms.agent.** extends android.app.Activity { public *; protected *; }
-keep interface com.huawei.android.hms.agent.common.INoProguard {*;}
-keep class * extends com.huawei.android.hms.agent.common.INoProguard {*;}
