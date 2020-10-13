package com.zgzx.metaphysics.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.SplashController;
import com.zgzx.metaphysics.controller.UserController;
import com.zgzx.metaphysics.model.entity.AdEntity;
import com.zgzx.metaphysics.model.entity.H5BaseEntity;
import com.zgzx.metaphysics.model.entity.NotificationEntity;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity implements SplashController.View {
    private boolean isFirst = true;
    private SharedPreferences sharedPreferences;
    @BindView(R.id.gif_bg)
    ImageView gif_bg;
    int duration;
    @BindView(R.id.tv_skip)
    TextView tv_skip;
    @BindView(R.id.ad_layout)
    FrameLayout ad_layout;
    @BindView(R.id.ad_img)
    ImageView ad_img;
    @BindView(R.id.gif_layout)
    FrameLayout gif_layout;
    private Handler mHandler;

    private Runnable runnable;
    private int recLen = 4;//跳过倒计时提示5秒
    Timer timer = new Timer();
   private SplashController.Presenter  mPresenter;
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
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        mPresenter = new SplashController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        // 显示语言
        AndroidUtil.alertLanguage(this, LocalConfigStore.getInstance().getAcceptLanguage());
        sharedPreferences = getSharedPreferences("FirstLaunch", MODE_PRIVATE);
        isFirst = sharedPreferences.getBoolean("FirstValue", true);


        GifLoadOneTimeGif.loadOneTimeGif(this, R.drawable.ic_splash_gif, gif_bg, 1, new GifLoadOneTimeGif.GifListener() {
            @Override
            public void gifPlayComplete() {
                // 页面
                new LifecycleHandler(getLifecycle(), Looper.getMainLooper())
                        .postDelayed(() -> {
                            if (isFirst) {
                                AgreementDialog.show(SplashActivity.this, SplashActivity.this);

                            } else {

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

        ad_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                task.cancel();
                if (LocalConfigStore.getInstance().isLogin()) {
                  //  EventBus.getDefault().post(new BookEvent());
                    startActivityAndFinish(new Intent(SplashActivity.this, MainActivity.class).putExtra("type",1));
                } else {
                    startActivityAndFinish(new Intent(SplashActivity.this, LoginActivity.class));
                }
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

        dealNotification();

    }

    private void dealNotification() {
        LogUtils.i("-----------------");
        Bundle extras = getIntent().getExtras();
        if (extras != null && !TextUtils.isEmpty(extras.toString())) {
            LogUtils.i(extras);

            if (!TextUtils.isEmpty(extras.getString("appData"))) {
                try {
                    JSONObject appData = new JSONObject(extras.getString("appData"));
                    LogUtils.i(appData);
                    if (appData != null && !TextUtils.isEmpty(appData.toString())) {
                        LogUtils.i("appData != null");
                        NotificationEntity notiMsg
                                = new Gson().fromJson(appData.toString(), NotificationEntity.class);

                        Intent intent = null;
                        if (notiMsg.getOpenType() == 1) {
                            intent = new Intent(this, MyOrderActivity.class);
                            intent.putExtra(Constants.NEED_GO_MAIN_WHEN_FINISH, true);
                            startActivityAndFinish(intent);
                        } else if (notiMsg.getOpenType() == 2) {
                            intent = new Intent(this, OrderDetailActivity.class);
                            intent.putExtra(Constants.KEY, notiMsg.getOrderId());
                            intent.putExtra(Constants.NEED_GO_MAIN_WHEN_FINISH, true);
                            startActivityAndFinish(intent);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            LogUtils.i("-----------------");
        }
    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    if (recLen > 0) {
                        tv_skip.setText(getResources().getString(R.string.tv_skip) + recLen + "s");
                    }
                    if (recLen < 1) {
                        timer.cancel();
                        task.cancel();
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
         //   sharedPreferences.edit().putBoolean("FirstValue", false).commit();
        }
    }

    @Override
    public void onGetConfigBase(UrlConfigEntity urlConfigEntity) {
        long time = new Date().getTime() / 1000;//获取系统时间的10位的时间戳
        long diff = urlConfigEntity.getTimestamp() - time;
        LocalConfigStore.getInstance().setConfirgUrl(diff, urlConfigEntity.getAk(),
                urlConfigEntity.getKey());
        LocalConfigStore.getInstance().setH5Base(urlConfigEntity.getDomain());
    }

    @Override
    public void loading() {

    }

    @Override
    public void failure(Throwable throwable) {

    }
}

