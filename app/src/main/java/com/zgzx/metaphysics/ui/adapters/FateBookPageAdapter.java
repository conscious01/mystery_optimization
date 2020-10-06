package com.zgzx.metaphysics.ui.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.FateBookDetailEntity;
import com.zgzx.metaphysics.model.entity.FateBooksEntity;
import com.zgzx.metaphysics.ui.dialogs.FateBookCatePriceDialog;
import com.zgzx.metaphysics.ui.dialogs.FateBookDictoryDialog;
import com.zgzx.metaphysics.utils.image.GlideApp;

import java.util.List;

public class FateBookPageAdapter extends PagerAdapter {
    private List<FateBooksEntity> mData;

    public FateBookPageAdapter(List<FateBooksEntity> data) {
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
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
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.fragment_select_fate_book_layout, container, false);

        FrameLayout mImgBook_onselef_layout = view.findViewById(R.id.mImgBook_onselef_layout);
        FrameLayout mImgBook_love_layout = view.findViewById(R.id.mImgBook_love_layout);
        FrameLayout mImgBook_case_layout = view.findViewById(R.id.mImgBook_case_layout);
        FrameLayout mImgBook_balance_layout = view.findViewById(R.id.mImgBook_balance_layout);
        FrameLayout mImgBook_home_layout = view.findViewById(R.id.mImgBook_home_layout);


        ImageView mImgBook_onselef = view.findViewById(R.id.mImgBook_onselef);
        ImageView mImgBook_love = view.findViewById(R.id.mImgBook_love);
        ImageView mImgBook_case = view.findViewById(R.id.mImgBook_case);
        ImageView mImgBook_balance = view.findViewById(R.id.mImgBook_balance);
        ImageView mImgBook_home = view.findViewById(R.id.mImgBook_home);
        FateBooksEntity bean = mData.get(position);
        if (bean.getCate().get(0).isIs_buy()) {
            GlideApp.with(mImgBook_onselef)
                    .load(R.drawable.icon_book)
                    .into(mImgBook_onselef);
        } else {
            GlideApp.with(mImgBook_onselef)
                    .load(R.drawable.draw_book_lock)
                    .into(mImgBook_onselef);
        }
        if (bean.getCate().get(1).isIs_buy()) {
            GlideApp.with(mImgBook_love)
                    .load(R.drawable.icon_book)
                    .into(mImgBook_love);
        } else {
            GlideApp.with(mImgBook_love)
                    .load(R.drawable.draw_book_lock)
                    .into(mImgBook_love);
        }
        if (bean.getCate().get(2).isIs_buy()) {
            GlideApp.with(mImgBook_case)
                    .load(R.drawable.icon_book)
                    .into(mImgBook_case);
        } else {
            GlideApp.with(mImgBook_case)
                    .load(R.drawable.draw_book_lock)
                    .into(mImgBook_case);
        }

        if (bean.getCate().get(3).isIs_buy()) {
            GlideApp.with(mImgBook_balance)
                    .load(R.drawable.icon_book)
                    .into(mImgBook_balance);
        } else {
            GlideApp.with(mImgBook_balance)
                    .load(R.drawable.draw_book_lock)
                    .into(mImgBook_balance);
        }
        if (bean.getCate().get(4).isIs_buy()) {
            GlideApp.with(mImgBook_home)
                    .load(R.drawable.icon_book)
                    .into(mImgBook_home);
        } else {
            GlideApp.with(mImgBook_home)
                    .load(R.drawable.draw_book_lock)
                    .into(mImgBook_home);
        }

        mImgBook_onselef_layout.setOnClickListener(view1 -> {
            if (bean.getCate().get(0).isIs_buy()) {
                FateBookDictoryDialog.show(container.getContext(), bean.getCate(), bean.getId(), 0);
            } else {
                FateBookCatePriceDialog.show(container.getContext(), bean.getId(), bean.getCate().get(0).getCate_id());
            }
        });
        mImgBook_love_layout.setOnClickListener(view1 -> {
            if (bean.getCate().get(1).isIs_buy()) {
                FateBookDictoryDialog.show(container.getContext(), bean.getCate(), bean.getId(), 1);
            } else {
                FateBookCatePriceDialog.show(container.getContext(), bean.getId(), bean.getCate().get(1).getCate_id());
            }
        });
        mImgBook_case_layout.setOnClickListener(view1 -> {
            if (bean.getCate().get(2).isIs_buy()) {
                FateBookDictoryDialog.show(container.getContext(), bean.getCate(), bean.getId(), 2);
            } else {
                FateBookCatePriceDialog.show(container.getContext(), bean.getId(), bean.getCate().get(2).getCate_id());
            }
        });

        mImgBook_balance_layout.setOnClickListener(view1 -> {

            if (bean.getCate().get(3).isIs_buy()) {
                FateBookDictoryDialog.show(container.getContext(), bean.getCate(), bean.getId(), 3);
            } else {
                FateBookCatePriceDialog.show(container.getContext(), bean.getId(), bean.getCate().get(3).getCate_id());
            }

        });
        mImgBook_home_layout.setOnClickListener(view1 ->{
            if (bean.getCate().get(4).isIs_buy()) {
                FateBookDictoryDialog.show(container.getContext(), bean.getCate(), bean.getId(), 4);
            }else {
                FateBookCatePriceDialog.show(container.getContext(), bean.getId(), bean.getCate().get(4).getCate_id());
            }
        } );
        container.addView(view);
        return view;
    }
}
