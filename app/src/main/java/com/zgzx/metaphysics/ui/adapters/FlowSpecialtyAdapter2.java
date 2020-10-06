package com.zgzx.metaphysics.ui.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.zgzx.metaphysics.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.DrawableRes;
import androidx.core.content.res.ResourcesCompat;

/**
 * 师傅特长，流式布局
 */
public class FlowSpecialtyAdapter2 extends TagAdapter<String> {

    private @DrawableRes int mResId;
    private List<String> mData;

    public FlowSpecialtyAdapter2(List<String> data) {
        this(data, R.drawable.light_green);
    }

    public FlowSpecialtyAdapter2(List<String> data, @DrawableRes int resId) {
        super(data);
        this.mData = data;
        this.mResId = resId;
    }

    @Override
    public View getView(FlowLayout parent, int position, String entity) {
        Context context = parent.getContext();
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.font_family));
        textView.setMinHeight(0);
        textView.setMinWidth(0);
        textView.setTextSize(14);
        textView.setPadding(ConvertUtils.dp2px(7), ConvertUtils.dp2px(1), ConvertUtils.dp2px(7), ConvertUtils.dp2px(1));
        textView.setText(entity);
        textView.setBackgroundResource(mResId);
//        textView.setBackgroundResource(R.drawable.selector_specialty);
        textView.setTextColor(context.getResources().getColor(R.color.color_4d5259));
        return textView;
    }

    // 选中所有
    public void selectedAll() {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < mData.size(); i++) {
            set.add(i);
        }

        setSelectedList(set);
    }

}
