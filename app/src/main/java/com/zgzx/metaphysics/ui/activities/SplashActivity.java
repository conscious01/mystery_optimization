package com.zgzx.metaphysics.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.UserController;
import com.zgzx.metaphysics.model.entity.AdEntity;
import com.zgzx.metaphysics.model.entity.H5BaseEntity;
import com.zgzx.metaphysics.model.entity.UrlConfigEntity;
import com.zgzx.metaphysics.model.entity.UserAssetEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.model.event.SplashEvent;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.ui.dialogs.AgreementDialog;
import com.zgzx.metaphysics.utils.AndroidUtil;
import com.zgzx.metaphysics.utils.LifecycleHandler;
import com.zgzx.metaphysics.utils.image.GifLoadOneTimeGif;

import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity implements UserController.View {
    private boolean isFirst = true;
    private SharedPreferences sharedPreferences;
    @BindView(R.id.gif_bg)
    ImageView gif_bg;
    int duration;
    @BindView(R.id.tv_skip)
    TextView tv_skip;
    @BindView(R.id.ad_layout)
    FrameLayout ad_layout;

    @BindView(R.id.gif_layout)
    FrameLayout gif_layout;
    private Handler mHandler;

    private Runnable runnable;
    private int recLen = 4;//跳过倒计时提示5秒
    Timer timer = new Timer();
    @Override
    protected boolean useEventBus() {
        return true;
    }



    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {


        // 显示语言
        AndroidUtil.alertLanguage(this, LocalConfigStore.getInstance().getAcceptLanguage());
        sharedPreferences = getSharedPreferences("FirstLaunch", MODE_PRIVATE);
        isFirst = sharedPreferences.getBoolean("FirstValue", true);

        UserController.Presenter presenter2 = new UserController.Presenter();
        presenter2.setModelAndView(this);
        getLifecycle().addObserver(presenter2);

        GifLoadOneTimeGif.loadOneTimeGif(this, R.drawable.ic_splash_gif, gif_bg, 1, new GifLoadOneTimeGif.GifListener() {
            @Override
            public void gifPlayComplete() {
                // 页面
                new LifecycleHandler(getLifecycle(), Looper.getMainLooper())
                        .postDelayed(() -> {
                            if (isFirst) {
                                AgreementDialog.show(SplashActivity.this,SplashActivity.this);
                                AgreementDialog.show(SplashActivity.this, SplashActivity.this);

                            } else {
                                if (LocalConfigStore.getInstance().isLogin()) {
                                    startActivityAndFinish(new Intent(SplashActivity.this, MainActivity.class));
                                } else {
                                    startActivityAndFinish(new Intent(SplashActivity.this, LoginActivity.class));
                                }
                                gif_layout.setVisibility(View.GONE);
                                ad_layout.setVisibility(View.VISIBLE);
                                mHandler = new Handler();
                                timer.schedule(task,10,1000);
                                tv_skip.setText(getResources().getString(R.string.tv_skip) + 3+"s");
                                mHandler.postDelayed(runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        if (LocalConfigStore.getInstance().isLogin()) {
                                            startActivityAndFinish(new Intent(SplashActivity.this, MainActivity.class));
                                        } else {
                                            startActivityAndFinish(new Intent(SplashActivity.this, LoginActivity.class));
                                        }
                                    }
                                }, 3000);

                            }
                        }, 10);
            }
        });


        tv_skip.setOnClickListener(v -> {
            if (runnable != null) {
                mHandler.removeCallbacks(runnable);
                task.cancel();
                if (LocalConfigStore.getInstance().isLogin()) {
                    startActivityAndFinish(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivityAndFinish(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }

        });
    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    if (tv_skip!=null) {
                        tv_skip.setText(getResources().getString(R.string.tv_skip) + recLen+"s");

                    }
                    if (recLen < 0) {
                        timer.cancel();

                    }
                }
            });
        }
    };
    @Subscribe
    public void onEvent(SplashEvent event) {
        if (event.getType() == 0) {
            onBackPressed();
        } else if (event.getType() == 1) {
            startActivityAndFinish(new Intent(SplashActivity.this, GuideActivity.class));
            sharedPreferences.edit().putBoolean("FirstValue", false).commit();
            //   sharedPreferences.edit().putBoolean("FirstValue", false).commit();
        }
    }

    @Override
    public void renderUserDetail(UserDetailEntity entity) {

    }

    @Override
    public void renderAssets(float amount) {

    }

    @Override
    public void onUserAssets(UserAssetEntity userAssetEntity) {

    }

    @Override
    public void onGetH5Base(H5BaseEntity h5BaseEntity) {

    }

    @Override
    public void onGetConfigBase(UrlConfigEntity urlConfigEntity) {
        long time = new Date().getTime() / 1000;//获取系统时间的10位的时间戳
        long diff = urlConfigEntity.getTimestamp() - time;
        LocalConfigStore.getInstance().setConfirgUrl(diff, urlConfigEntity.getAk(), urlConfigEntity.getKey());
        LocalConfigStore.getInstance().setH5Base(urlConfigEntity.getDomain());

    }

    @Override
    public void onGetAdConfig(AdEntity urlConfigEntity) {

    }

    @Override
    public void loading() {

    }

    @Override
    public void failure(Throwable throwable) {

    }
}

