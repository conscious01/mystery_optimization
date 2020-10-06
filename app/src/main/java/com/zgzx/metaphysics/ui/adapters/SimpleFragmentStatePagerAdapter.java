package com.zgzx.metaphysics.ui.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<String> titleList;
    private List<Fragment> fragmentList;

    public SimpleFragmentStatePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        return super.instantiateItem(container, position);
    }

    public SimpleFragmentStatePagerAdapter add(Fragment fragment, String title){
        titleList.add(title);
        fragmentList.add(fragment);
        return this;
    }

}
