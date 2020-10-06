package com.zgzx.metaphysics.utils.item;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 平均
 */
public class EvenItemDecoration extends RecyclerView.ItemDecoration {

    private int orientation; // 方向
    private int space, column;

    public EvenItemDecoration(RecyclerView recyclerView, @DimenRes int id) {
        this.space = recyclerView.getContext().getResources().getDimensionPixelSize(id);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            column = ((GridLayoutManager) layoutManager).getSpanCount();

        } else if (layoutManager instanceof LinearLayoutManager){
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            // 每个span分配的间隔大小
            int spanSpace = space * (column + 1) / column;

            // 列索引
            int colIndex = position % column;

            // 列左、右间隙
            outRect.left = space * (colIndex + 1) - spanSpace * colIndex;
            outRect.right = spanSpace * (colIndex + 1) - space * (colIndex + 1);

            // 行间距
            if (position >= column) {
                outRect.top = space;
            }

        } else if (layoutManager instanceof LinearLayoutManager){
            RecyclerView.Adapter adapter = parent.getAdapter();

            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.top = space;

                if (adapter.getItemCount() - 1 == position) {
                    outRect.bottom = space;
                }

            } else if (orientation == LinearLayoutManager.HORIZONTAL){
                outRect.left = space;

                if (adapter.getItemCount() - 1 == position) {
                    outRect.right = space;
                }
            }
        }
    }
}
