package com.zgzx.metaphysics.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jaeger.library.StatusBarUtil;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.SystemUtils;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * 引导页
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.start_main)
    Button start_main;

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        StatusBarUtil.setLightMode(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_guide_layout;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        viewpager.setAdapter(new GalleryPagerAdapter());
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    start_main.setVisibility(View.VISIBLE);
                } else {
                    start_main.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LocalConfigStore.getInstance().isLogin()) {
                    startActivityAndFinish(new Intent(GuideActivity.this, MainActivity.class));
                } else {
                    startActivityAndFinish(new Intent(GuideActivity.this, LoginActivity.class));
                }
            }
        });

        SystemUtils.leadOpenNotification(GuideActivity.this);

    }

    class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;
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
                    item = View.inflate(GuideActivity.this, R.layout.guide_layout_1, null);
                    break;
                case 1:
                    item = View.inflate(GuideActivity.this, R.layout.guide_layout_2, null);
                    break;
                case 2:
                    item = View.inflate(GuideActivity.this, R.layout.guide_layout_3, null);
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


}
