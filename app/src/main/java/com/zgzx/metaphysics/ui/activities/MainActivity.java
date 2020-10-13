package com.zgzx.metaphysics.ui.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.SealNotificationReceiver;
import com.zgzx.metaphysics.controller.UserController;
import com.zgzx.metaphysics.controller.presenters.VersionUpdatePresenter;
import com.zgzx.metaphysics.model.entity.AdEntity;
import com.zgzx.metaphysics.model.entity.H5BaseEntity;
import com.zgzx.metaphysics.model.entity.NotificationEntity;
import com.zgzx.metaphysics.model.entity.UrlConfigEntity;
import com.zgzx.metaphysics.model.entity.UserAssetEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.model.event.BookEvent;
import com.zgzx.metaphysics.model.event.Go2HomePageEvent;
import com.zgzx.metaphysics.model.event.MasterEvent;
import com.zgzx.metaphysics.model.event.OnFragmentChanged;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.ui.dialogs.VersionUpdateDialog;
import com.zgzx.metaphysics.ui.fragments.CalculationFragment;
import com.zgzx.metaphysics.ui.fragments.FateBookFragment;
import com.zgzx.metaphysics.ui.fragments.FindFragment;
import com.zgzx.metaphysics.ui.fragments.MasterFragment;
import com.zgzx.metaphysics.ui.fragments.MineFragment;
import com.zgzx.metaphysics.ui.fragments.V2HomeFragment;
import com.zgzx.metaphysics.ui.view.MainViewPage;
import com.zgzx.metaphysics.ui.view.bottomNav.NavItem;
import com.zgzx.metaphysics.ui.view.bottomNav.NavViewBottomContainer;
import com.zgzx.metaphysics.utils.AppNotificationJava;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import io.rong.imlib.RongIMClient;
import io.rong.push.RongPushClient;

/**
 * 程序主activity，由BottomNavigationView+FrameLayout组成
 * V2HomeFragment：主页面
 * FateBookFragment：命书
 * FindFragment：发现
 * MineFragment：我的
 */

public class MainActivity extends BaseActivity implements UserController.View,
        MainViewPage.OnSingleTouchListener {

    private static final String TAG = "MainActivity";
    //  public BottomNavigationView mBottomNavigation;
    private boolean isFirst = true;
    private SharedPreferences sharedPreferences;
    private Map<Integer, Fragment> mFragmentMap = new HashMap<>();
    private NotificationManager mNotificationManager;

    //    @BindView(R.id.main_viewpager)
    public static MainViewPage main_viewpager;


    public static NavViewBottomContainer mNavBottomContainer;
    private int mPos = 0;
    private List<NavItem> navItems = new ArrayList<>();

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        StatusBarUtil.setLightMode(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGotoMain(Go2HomePageEvent event) {
        mShouldSelectPosition = 0;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGotPushMsg(NotificationEntity notiMsg) {
        //opentype 跳转类型:1,订单列表;2,订单详情
        Intent intent = null;
//        if (MetaphysicsApplication.mIfAppOpening) {
//
//        }else {
//
//        }
        if (notiMsg.getOpenType() == 1) {
            intent = new Intent(this, MyOrderActivity.class);
        } else if (notiMsg.getOpenType() == 2) {
            intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra(Constants.KEY, notiMsg.getOrderId());
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        AppNotificationJava.sendNotification(this, AppNotificationJava.msgChannelId,
                notiMsg.getTitle(),
                notiMsg.getContent(), R.mipmap.ic_launcher,
                R.drawable.icon_logo_bg, pendingIntent);

    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected int getBackgroundColor() {
        return getColorForRes(R.color.backgroundColor);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    UserController.Presenter presenter2;

    @Override
    protected void initialize(Bundle savedInstanceState) {


        main_viewpager = findViewById(R.id.main_viewpager);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.PATTERN_2);

        Date date = new Date(System.currentTimeMillis());

        initViewPage(simpleDateFormat, date);

        initBottomNav(savedInstanceState);

        // 版本更新
        VersionUpdatePresenter presenter = new VersionUpdatePresenter();
        presenter.setModelAndView(new VersionUpdateDialog(this));
        getLifecycle().addObserver(presenter);
        presenter.checkVersion();

        presenter2 = new UserController.Presenter();
        presenter2.setModelAndView(this);
        getLifecycle().addObserver(presenter2);


        if (!LocalConfigStore.getInstance().isCompletedInfo()) {
            LocalConfigStore.getInstance().saveUserToken("");
        }


        if (SealNotificationReceiver.needUpdate) {
            //重置标记位，防止多次弹窗提醒
            SealNotificationReceiver.needUpdate = false;
            RongPushClient.resolveHWPushError(this, 0);
        }



    }



    /**
     * 初始化底部导航栏
     *
     * @param savedInstanceState
     */
    private void initBottomNav(Bundle savedInstanceState) {
        mNavBottomContainer = findViewById(R.id.mNavBottomContainer);

        navItems.add(new NavItem(NavItem.NAV_HOME, R.string.homepage, R.raw.home));
        navItems.add(new NavItem(NavItem.NAV_CATEGORY, R.string.nav_fate_book, R.raw.book));
        navItems.add(new NavItem(NavItem.NAV_CATEGORY, R.string.nav_master, R.raw.master));
        navItems.add(new NavItem(NavItem.NAV_CATEGORY, R.string.nav_calculation,
                R.raw.calculation));
        navItems.add(new NavItem(NavItem.NAV_MINE, R.string.nav_mine, R.raw.my));
        mNavBottomContainer.addItem(navItems);
        mNavBottomContainer.setOnItemClickListener(new NavViewBottomContainer.OnItemClickListener() {
            @Override
            public void onItemSelect(int position) {

                Fragment targetFragment = mFragmentMap.get(position);

                iniFragemnt(targetFragment);
                EventBus.getDefault().post(new OnFragmentChanged(position));
            }
        });


        // 初始化页面
        if (savedInstanceState != null) {
            restoreFragment(savedInstanceState);

        } else {
            mFragmentMap.put(0, new V2HomeFragment());
            mFragmentMap.put(1, new FateBookFragment());
            mFragmentMap.put(2, new MasterFragment());

            mFragmentMap.put(3, new CalculationFragment());
            mFragmentMap.put(4, new MineFragment());
            // 默认选中位置

            if (getIntent().getExtras() != null) {
                switch (getIntent().getExtras().getInt("type")) {
                    case 0:
                        Fragment targetFragment1 = mFragmentMap.get(0);
                        iniFragemnt(targetFragment1);
                        mNavBottomContainer.setSelectedPosition(0);
                        break;
                    case 1:
                        Fragment targetFragment = mFragmentMap.get(1);
                        iniFragemnt(targetFragment);
                        mNavBottomContainer.setSelectedPosition(1);
                        break;
                }

            } else {
                mNavBottomContainer.setSelectedPosition(0);
                Fragment targetFragment = mFragmentMap.get(0);
                iniFragemnt(targetFragment);
            }


        }
    }

    private void iniFragemnt(Fragment targetFragment) {
        if (targetFragment != null) {
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            if (!targetFragment.isAdded()) {
                fragmentTransaction.add(R.id.content, targetFragment);
            }

            Set<Map.Entry<Integer, Fragment>> entries = mFragmentMap.entrySet();
            for (Map.Entry<Integer, Fragment> fragmentEntry : entries) {
                Fragment fragmentEntryValue = fragmentEntry.getValue();
                if (fragmentEntryValue == targetFragment) {
                    fragmentTransaction.show(fragmentEntryValue);
                } else {
                    fragmentTransaction.hide(fragmentEntryValue);
                }
            }

            fragmentTransaction.commit();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBookEvent(BookEvent event) {
        Fragment targetFragment = mFragmentMap.get(1);

        iniFragemnt(targetFragment);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMasterEvent(MasterEvent event) {
        Fragment targetFragment = mFragmentMap.get(2);
        iniFragemnt(targetFragment);
    }

    /**
     * 首次进入引导页
     *
     * @param simpleDateFormat
     * @param date
     */
    private void initViewPage(SimpleDateFormat simpleDateFormat, Date date) {
        sharedPreferences = getSharedPreferences("FirstLaunch", MODE_PRIVATE);
        isFirst = sharedPreferences.getBoolean("FirstValue", true);

        if (isFirst) {

            main_viewpager.setAdapter(new GalleryPagerAdapter());
            main_viewpager.setOnSingleTouchListener(this);
            main_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {
                    mPos = position;
                    if (position > 4) {
                        main_viewpager.setVisibility(View.GONE);
                        sharedPreferences.edit().putBoolean("FirstValue", false).commit();
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    mPos = position;
                    if (position > 4) {
                        main_viewpager.setVisibility(View.GONE);
                        sharedPreferences.edit().putBoolean("FirstValue", false).commit();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            main_viewpager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    main_viewpager.setCurrentItem(mPos + 1);
                    if (mPos + 1 == 4) {
                        main_viewpager.setVisibility(View.GONE);
                        sharedPreferences.edit().putBoolean("FirstValue", false).commit();
                    }
                }
            });
            main_viewpager.setOnTouchListener(new View.OnTouchListener() {
                float startX;
                float startY;
                float endX;
                float endY;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX = event.getX();
                            startY = event.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            endX = event.getX();
                            endY = event.getY();
                            WindowManager windowManager =
                                    (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                            //获取屏幕的宽度
                            Point size = new Point();
                            windowManager.getDefaultDisplay().getSize(size);
                            int width = size.x;
                            //首先要确定的是，是否到了最后一页，然后判断是否向左滑动，并且滑动距离是否符合，我这里的判断距离是屏幕宽度的4分之一（这里可以适当控制）
                            if (mPos + 1 == 4 && startX - endX > 0 && startX - endX >= (width / 4)) {
                                main_viewpager.setVisibility(View.GONE);
                                sharedPreferences.edit().putBoolean("FirstValue", false).commit();
                            }
                            break;
                    }
                    return false;
                }
            });
        } else {
            main_viewpager.setVisibility(View.GONE);
        }


    }


    // 恢复页面
    private void restoreFragment(Bundle savedInstanceState) {
        // 运势
        Fragment homeFragment = getSupportFragmentManager().getFragment(savedInstanceState,
                V2HomeFragment.class.getName());
        mFragmentMap.put(R.id.nav_main, homeFragment != null ? homeFragment : new V2HomeFragment());

        // 命书
        Fragment fateBookFragment = getSupportFragmentManager().getFragment(savedInstanceState,
                FateBookFragment.class.getName());
        mFragmentMap.put(R.id.nav_book, fateBookFragment != null ? fateBookFragment :
                new FateBookFragment());
        // 师傅
        Fragment findFragment = getSupportFragmentManager().getFragment(savedInstanceState,
                FindFragment.class.getName());
        mFragmentMap.put(R.id.nav_find, findFragment != null ? findFragment : new FindFragment());
        // 测算
        Fragment calculationFragment = getSupportFragmentManager().getFragment(savedInstanceState
                , CalculationFragment.class.getName());
        mFragmentMap.put(R.id.nav_calculation, calculationFragment != null ? calculationFragment
                : new CalculationFragment());

        // 我的
        Fragment mineFragment = getSupportFragmentManager().getFragment(savedInstanceState,
                MineFragment.class.getName());
        mFragmentMap.put(R.id.nav_mine, mineFragment != null ? mineFragment : new MineFragment());
        mNavBottomContainer.setSelectedPosition(savedInstanceState.getInt("SelectedItemId"));
        // 选择
        //   mBottomNavigation.setSelectedItemId(savedInstanceState.getInt("SelectedItemId"));
    }

    int mShouldSelectPosition = -1;

    @Override
    protected void onResume() {
        super.onResume();

        if (LocalConfigStore.getInstance().isLogin()) {
            presenter2.getRongToken();
            connectRongService(LocalConfigStore.getInstance().getRongToken());

        }
        if (mShouldSelectPosition > -1) {
            Fragment targetFragment1 = mFragmentMap.get(mShouldSelectPosition);
            iniFragemnt(targetFragment1);
            mNavBottomContainer.setSelectedPosition(mShouldSelectPosition);
            mShouldSelectPosition = -1;
        }
//        startActivity(new Intent(this, PreviewActivity.class));
    }


    @Override
    public void onSingleTouch() {
        if (mPos + 1 == 4) {
            main_viewpager.setVisibility(View.GONE);
            sharedPreferences.edit().putBoolean("FirstValue", false).commit();
        } else {
            main_viewpager.setCurrentItem(mPos + 1);
        }
    }

    class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View item = null;
            switch (position) {
                case 0:
                    item = View.inflate(MainActivity.this,
                            R.layout.fragment_main_viewpage_1_layout, null);
                    break;
                case 1:
                    item = View.inflate(MainActivity.this,
                            R.layout.fragment_main_viewpage_2_layout, null);
                    break;
                case 2:
                    item = View.inflate(MainActivity.this,
                            R.layout.fragment_main_viewpage_3_layout, null);
                    break;
                case 3:
                    item = View.inflate(MainActivity.this,
                            R.layout.fragment_main_viewpage_4_layout, null);
                    break;
            }
            container.addView(item);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mPos + 1 == 4) {
                        main_viewpager.setVisibility(View.GONE);
                        sharedPreferences.edit().putBoolean("FirstValue", false).commit();
                    } else {
                        main_viewpager.setCurrentItem(position + 1);
                    }
                }
            });
            return item;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position,
                                @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    private void connect(String token) {

        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {

                System.out.println(TAG + "RONG_LOG--onSuccess" + userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                System.out.println(TAG + "RONG_LOG" + errorCode.getMessage());

            }
        });
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Set<Map.Entry<Integer, Fragment>> entries = mFragmentMap.entrySet();
        for (Map.Entry<Integer, Fragment> fragmentEntry : entries) {
            Fragment fragment = fragmentEntry.getValue();
            if (fragment.isAdded()) {
                String key = fragment.getClass().getName();
                getSupportFragmentManager().putFragment(outState, key, fragment);
            }
        }

        // 选中的条目
        outState.putInt("SelectedItemId", mNavBottomContainer.getSelectedPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void renderUserDetail(UserDetailEntity entity) {
        LocalConfigStore.getInstance().saveUserInfo(entity);
    }


    @Override
    public void renderAssets(float amount) {

    }

    @Override
    public void onUserAssets(UserAssetEntity userAssetEntity) {

    }

    @Override
    public void onGetH5Base(H5BaseEntity h5BaseEntity) {
        LocalConfigStore.getInstance().setH5Base(h5BaseEntity.getDomain());

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
    public void onGetAdConfig(AdEntity urlConfigEntity) {
        TTAdSdk.init(this,
                new TTAdConfig.Builder()
                        .appId(String.valueOf(urlConfigEntity.getApp_id()))
                        .useTextureView(false) //使用TextureView控件播放视频,默认为SurfaceView,
                        // 当有SurfaceView冲突的场景，可以使用TextureView
                        .allowShowNotify(true) //是否允许sdk展示通知栏提示
                        .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                        .debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                        .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI,
                                TTAdConstant.NETWORK_STATE_3G) //允许直接下载的网络状态集合
                        .supportMultiProcess(false) //是否支持多进程，true支持
                        //.httpStack(new MyOkStack3())//自定义网络库，demo中给出了okhttp3版本的样例，其余请自行开发或者咨询工作人员。
                        .build());
        LocalConfigStore.getInstance().setAdConfirg(String.valueOf(urlConfigEntity.getApp_id()),
                String.valueOf(urlConfigEntity.getAd_ids().get(0)));
    }

    @Override
    public void onGetRongToken(String rongToken) {
        //后台返回融云token为空的时候不要保存，逻辑应该放在后台判断
        if (!TextUtils.isEmpty(rongToken)) {
            LocalConfigStore.getInstance().setRongToken(rongToken);
            connectRongService(rongToken);

        }
    }

    @Override
    public void onRenderCount(int count) {
        LocalConfigStore.getInstance().setJnType(count);
        if (count==0){
            LocalConfigStore.getInstance().setJnType_1(false);
            LocalConfigStore.getInstance().setJnType_2(false);
            LocalConfigStore.getInstance().setJnType_3(false);
            LocalConfigStore.getInstance().setJnType_4(false);
            LocalConfigStore.getInstance().setJnType_5(false);
            LocalConfigStore.getInstance().setJnType_6(false);
            LocalConfigStore.getInstance().setJnType_7(false);
            LocalConfigStore.getInstance().setJnType_8(false);
        }
    }

    private void connectRongService(String rongToken) {

        if (RongIMClient.getInstance().getCurrentConnectionStatus() != RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) {

            if (!TextUtils.isEmpty(rongToken)) {
                connect(rongToken);

            }
        }
    }

    @Override
    public void loading() {

    }

    @Override
    public void failure(Throwable throwable) {

    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            AppToast.showShort(getResources().getString(R.string.tv_exit));
            mExitTime = System.currentTimeMillis();
        } else {

            finish();
            System.exit(0);
        }
    }



}