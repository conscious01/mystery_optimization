package com.zgzx.metaphysics.ui.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimplePagerAdapter extends PagerAdapter {

    private List<View> mViewList;
    private List<CharSequence> mTitleList;

    public SimplePagerAdapter() {
        mViewList = new ArrayList<>();
        mTitleList = new ArrayList<>();
    }

    public SimplePagerAdapter put(CharSequence title, View view){
        mViewList.add(view);
        mTitleList.add(title);
        return this;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }


    @Override
    public int getCount() {
        return mViewList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }

}
