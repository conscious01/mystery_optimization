package com.zgzx.metaphysics.ui.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.UserController;
import com.zgzx.metaphysics.controller.presenters.VersionUpdatePresenter;
import com.zgzx.metaphysics.model.entity.AdEntity;
import com.zgzx.metaphysics.model.entity.H5BaseEntity;
import com.zgzx.metaphysics.model.entity.NotificationEntity;
import com.zgzx.metaphysics.model.entity.UrlConfigEntity;
import com.zgzx.metaphysics.model.entity.UserAssetEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.model.event.Go2HomePageEvent;
import com.zgzx.metaphysics.model.event.OnFragmentChanged;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.ui.dialogs.VersionUpdateDialog;
import com.zgzx.metaphysics.ui.fragments.CalculationFragment;
import com.zgzx.metaphysics.ui.fragments.FateBookFragment;
import com.zgzx.metaphysics.ui.fragments.FindFragment;
import com.zgzx.metaphysics.ui.fragments.MasterFragment;
import com.zgzx.metaphysics.ui.fragments.MineFragment;
import com.zgzx.metaphysics.ui.fragments.V2HomeFragment;
import com.zgzx.metaphysics.utils.AppNotificationJava;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import butterknife.BindView;
import io.rong.imlib.RongIMClient;

/**
 * 程序主activity，由BottomNavigationView+FrameLayout组成
 * V2HomeFragment：主页面
 * FateBookFragment：命书
 * FindFragment：发现
 * MineFragment：我的
 */
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, UserController.View {

    private static final String TAG = "MainActivity";
    public BottomNavigationView mBottomNavigation;
    private boolean isFirst = true;
    private SharedPreferences sharedPreferences;
    private Map<Integer, Fragment> mFragmentMap = new HashMap<>();


    @BindView(R.id.main_viewpager)
    ViewPager main_viewpager;

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        StatusBarUtil.setLightMode(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGotoMain(Go2HomePageEvent event) {
        mBottomNavigation.getMenu().getItem(0).setChecked(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGotPushMsg(NotificationEntity notiMsg) {
        //opentype 跳转类型:1,订单列表;2,订单详情
        Intent intent = null;
        if (notiMsg.getOpenType() == 1) {
            intent = new Intent(this, MyOrderActivity.class);
        } else if (notiMsg.getOpenType() == 2) {
            intent = new Intent(this, OrderDetailActivity.class);
            intent.putExtra(Constants.KEY, notiMsg.getOrderId());
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        AppNotificationJava.sendNotification(this, AppNotificationJava.msgChannelId,
                notiMsg.getTitle(),
                notiMsg.getContent(), R.mipmap.ic_launcher,
                R.drawable.icon_logo_bg, pendingIntent);

    }

    @Override
    protected int getBackgroundColor() {
        return getColorForRes(R.color.backgroundColor);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initialize(Bundle savedInstanceState) {

        sharedPreferences = getSharedPreferences("FirstLaunch", MODE_PRIVATE);
        isFirst = sharedPreferences.getBoolean("FirstValue", true);
        if (isFirst) {
            main_viewpager.setAdapter(new GalleryPagerAdapter());
            main_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset,
                                           int positionOffsetPixels) {
                    if (position == 3) {
                        main_viewpager.setVisibility(View.GONE);
                        sharedPreferences.edit().putBoolean("FirstValue", false).commit();
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 4) {
                        main_viewpager.setVisibility(View.GONE);
                        sharedPreferences.edit().putBoolean("FirstValue", false).commit();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            main_viewpager.setVisibility(View.GONE);
        }

        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.setItemIconTintList(null);
        mBottomNavigation.setOnNavigationItemSelectedListener(this);


        // 初始化页面
        if (savedInstanceState != null) {
            restoreFragment(savedInstanceState);

        } else {
            mFragmentMap.put(R.id.nav_main, new V2HomeFragment());
            mFragmentMap.put(R.id.nav_book, new FateBookFragment());
            mFragmentMap.put(R.id.nav_find, new MasterFragment());
            mFragmentMap.put(R.id.nav_calculation, new CalculationFragment());
            mFragmentMap.put(R.id.nav_mine, new MineFragment());
            // 默认选中位置
            mBottomNavigation.setSelectedItemId(R.id.nav_main);

            List<Integer> ids = new ArrayList<>();

            ids.add(R.id.nav_main);
            ids.add(R.id.nav_book);
            ids.add(R.id.nav_find);
            ids.add(R.id.nav_calculation);
            ids.add(R.id.nav_mine);
            clearToast(mBottomNavigation, ids);


        }


        // 版本更新
        VersionUpdatePresenter presenter = new VersionUpdatePresenter();
        presenter.setModelAndView(new VersionUpdateDialog(this));
        getLifecycle().addObserver(presenter);
        presenter.checkVersion();

        UserController.Presenter presenter2 = new UserController.Presenter();
        presenter2.setModelAndView(this);
        getLifecycle().addObserver(presenter2);
        if (!LocalConfigStore.getInstance().isCompletedInfo()) {
            LocalConfigStore.getInstance().saveUserToken("");
        }

        if (LocalConfigStore.getInstance().isLogin()) {
            if (!AppNotificationJava.isNotificationEnabled(this)) {
                AppNotificationJava.openNotification(this);
            }
        }

    }

    public static void clearToast(BottomNavigationView bottomNavigationView, List<Integer> ids) {

        ViewGroup bottomNavigationMenuView = (ViewGroup) bottomNavigationView.getChildAt(0);

        //遍历子View,重写长按点击事件    

        for (int position = 0; position < ids.size(); position++) {

            bottomNavigationMenuView.getChildAt(position).findViewById(ids.get(position)).setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
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

        // 选择
        mBottomNavigation.setSelectedItemId(savedInstanceState.getInt("SelectedItemId"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        String tempRongToken = "pflRp042wq2xhYbaeNKJLGLfWgpVjGXMrSUwhkmWtPI=@84mk.cn.rongnav.com;" +
                "84mk.cn.rongcfg.com";
        if (!LocalConfigStore.getInstance().getRongToken().isEmpty()) {
            connect(LocalConfigStore.getInstance().getRongToken());
        }
//        startActivity(new Intent(this, PreviewActivity.class));


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

                System.out.println(TAG + userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                System.out.println(TAG + errorCode.getMessage());

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment targetFragment = mFragmentMap.get(item.getItemId());

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

            LogUtils.i(TAG + "--> onNavigationItemSelected --> " + item.getTitle() + " ," + item.getTitle().toString().equals(getString(R.string.nav_fate_book)));
            EventBus.getDefault().post(new OnFragmentChanged(item.getTitle().toString().equals(getString(R.string.nav_fate_book))));


            return true;
        }

        return true;
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
        outState.putInt("SelectedItemId", mBottomNavigation.getSelectedItemId());
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
    public void loading() {

    }

    @Override
    public void failure(Throwable throwable) {

    }


}