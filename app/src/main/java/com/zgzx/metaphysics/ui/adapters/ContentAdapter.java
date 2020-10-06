package com.zgzx.metaphysics.ui.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zgzx.metaphysics.R;

import java.util.ArrayList;

public class ContentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentList;
    private Context mContext;
    private String[] titles;
    public ContentAdapter(FragmentManager fm, Context mContext,ArrayList<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
        this.mContext=mContext;
        titles= mContext.getResources().getStringArray(R.array.fortune_time);
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
