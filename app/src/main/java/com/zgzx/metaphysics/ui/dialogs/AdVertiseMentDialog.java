package com.zgzx.metaphysics.ui.dialogs;

import android.app.Activity;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.event.PlayCompleteEvent;
import com.zgzx.metaphysics.utils.AppToast;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;

public class AdVertiseMentDialog extends CenterPopupView {
    private int postion,mType;
    private TTAdNative mTTAdNative;
    private TTRewardVideoAd mttRewardVideoAd;
    private boolean mIsLoaded;
    private Context mContext;
    private Activity mActivity;
    public static void show(
            Context context, int pos,Activity activity,int type) {
        new XPopup.Builder(context)
                .isDestroyOnDismiss(false)
                .enableShowWhenAppBackground(false)
                .asCustom(new AdVertiseMentDialog(context, pos,activity,type))
                .show();
    }

    public AdVertiseMentDialog(@NonNull Context context, int pos, Activity activity,int type) {
        super(context);
        postion = pos;
        mContext=context;
        mActivity=activity;
        mType=type;
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(context);
        initTTAd(pos);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_ad_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
      initTTAd(postion);


    }

    private void initTTAd(int pos) {
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(LocalConfigStore.getInstance().getAdCodeId())
                .setAdCount(2)
                //个性化模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                .setExpressViewAcceptedSize(500, 500)
                .setImageAcceptedSize(720, 1080)
                //非必传参数，仅奖励发放服务端回调时需要使用
                .setUserID(LocalConfigStore.getInstance().getUserId())
                //非必传参数，仅奖励发放服务端回调时需要使用
                .setMediaExtra(LocalConfigStore.getInstance().getAdCodeId())
                .build();
         mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {

            @Override
            public void onError(int i, String s) {
                LogUtils.i(s);
            }

            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ttRewardVideoAd) {
                mttRewardVideoAd = ttRewardVideoAd;
                mIsLoaded = false;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
                    @Override
                    public void onAdShow() {

                    }

                    @Override
                    public void onAdVideoBarClick() {

                    }

                    @Override
                    public void onAdClose() {
                        EventBus.getDefault().post(new PlayCompleteEvent(pos,mType));

                    }

                    @Override
                    public void onVideoComplete() {
                    }

                    @Override
                    public void onVideoError() {
                    }

                    @Override
                    public void onRewardVerify(boolean b, int i, String s, int i1, String s1) {

                    }

                    @Override
                    public void onSkippedVideo() {

                    }
                });
            }

            @Override
            public void onRewardVideoCached() {
                mIsLoaded = true;
                findViewById(R.id.not_play_layout).setOnClickListener(v -> dialog.dismiss());
                findViewById(R.id.Ad_layout_paly).setOnClickListener(v -> {
                    if (mttRewardVideoAd != null && mIsLoaded) {
                        //展示广告，并传入广告展示的场景
                        mttRewardVideoAd.showRewardVideoAd(mActivity, TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
                        mttRewardVideoAd = null;
                    } else {
                       // AppToast.showShort(mContext.getResources().getString(R.string.tv_ad_tips));
                    }
                    dialog.dismiss();
                });

            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
