package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.FateBooksEntity;
import com.zgzx.metaphysics.ui.activities.FateBookDetailActivity;
import com.zgzx.metaphysics.ui.adapters.FateBookDialogDirectoryAdapter;

import java.util.List;

public class FateBookDictoryDialog extends CenterPopupView implements BaseQuickAdapter.OnItemClickListener {
    private List<FateBooksEntity.CateBean> mData;
    private Context context;
    private int mBookId;
    private int postion;
   private FateBookDialogDirectoryAdapter fateBookDialogDirectoryAdapter;
    public static void show(Context context, List<FateBooksEntity.CateBean> mData,int mBookId,int postion) {
        new XPopup.Builder(context)
                .dismissOnTouchOutside(false)
                .maxWidth(1024)
                .asCustom(new FateBookDictoryDialog(context,mData,mBookId,postion))
                .show();
    }
    public FateBookDictoryDialog(@NonNull Context context,List<FateBooksEntity.CateBean> mData,int mBookId,int postion) {
        super(context);
        this.mData=mData;
        this.context=context;
        this.mBookId=mBookId;
        this.postion=postion;
    }
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_fate_book_layout;
    }
    @Override
    protected void onCreate() {
        super.onCreate();
       TextView tv_name=findViewById(R.id.tv_name);
        tv_name.setText(mData.get(postion).getName());
        findViewById(R.id.close_img).setOnClickListener(view -> dialog.dismiss());
        RecyclerView recyclerView=findViewById(R.id.recycle_fate_book);
        fateBookDialogDirectoryAdapter=new FateBookDialogDirectoryAdapter(mData.get(postion).getSub_cate());
        recyclerView.setAdapter(fateBookDialogDirectoryAdapter);
        fateBookDialogDirectoryAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        getContext().startActivity(FateBookDetailActivity.newIntent(context,mBookId,mData.get(postion).getCate_id(),position));
      //  dialog.dismiss();
    }
}
