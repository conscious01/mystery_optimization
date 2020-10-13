package com.zgzx.metaphysics.ui.view.itemDecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;

public class SpaceSilkBagItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace= ConvertUtils.dp2px(6.0f);

//    public SpaceSilkBagItemDecoration(float mSpace) {
//        this.mSpace = ConvertUtils.dp2px(mSpace);
//    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) % 6 == 0) {
            outRect.left = 0;
            outRect.right = mSpace;
        } else if (parent.getChildAdapterPosition(view) % 6 == 1) {
            outRect.left = mSpace;
            outRect.right = mSpace;
        } else if (parent.getChildAdapterPosition(view) % 6 == 2) {
            outRect.left = mSpace;
            outRect.right = mSpace;
        } else if (parent.getChildAdapterPosition(view) % 6 == 3) {
            outRect.left = mSpace;
            outRect.right = mSpace;
        } else if (parent.getChildAdapterPosition(view) % 6 == 4) {
            outRect.left = mSpace;
            outRect.right = mSpace;
        } else if (parent.getChildAdapterPosition(view) % 6 == 5) {
            outRect.left = mSpace;
            outRect.right = 0;
        }

    }
}
