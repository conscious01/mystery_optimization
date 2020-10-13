package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.AddfortuneDataEntity;
import com.zgzx.metaphysics.model.entity.FortuneEntity;
import com.zgzx.metaphysics.model.entity.FortuneGradeEntity;
import com.zgzx.metaphysics.model.entity.FortuneListImageEntity;
import com.zgzx.metaphysics.ui.adapters.FortuneBagAdapter;
import com.zgzx.metaphysics.ui.adapters.FortuneBagShareAdapter;
import com.zgzx.metaphysics.ui.adapters.FortuneGradeAdapter;
import com.zgzx.metaphysics.utils.image.QRCodeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShareFortuneDialog  extends BottomPopupView {
    private Context mContext;

    private String message;
    private Bitmap path;
    private OnDialogFortuneClickListener onDialogClickListener;
    private  List<FortuneEntity.FortuneBean.Bean> fortuneEntityList;
    private String[] fortuneArrayList;
    private FortuneEntity fortuneEntity;


    public static void show(Context context, OnDialogFortuneClickListener onDialogClickListener, String message, Bitmap path, List<FortuneEntity.FortuneBean.Bean> fortuneEntityList,
                            String[] fortuneArrayList, FortuneEntity fortuneEntity) {
        new XPopup.Builder(context)
                .enableDrag(true)
                .dismissOnTouchOutside(true)
                .isDestroyOnDismiss(true)
                .asCustom(new ShareFortuneDialog(context, onDialogClickListener, message, path,fortuneEntityList,fortuneArrayList,fortuneEntity))
                .show();
    }


    public ShareFortuneDialog(Context context, OnDialogFortuneClickListener onDialogClickListener, String message, Bitmap path,List<FortuneEntity.FortuneBean.Bean> fortuneEntityList,
                              String[] fortuneArrayList, FortuneEntity fortuneEntity) {
        super(context);
        this.mContext = context;
        this.onDialogClickListener = onDialogClickListener;
        this.message = message;
        this.path = path;
        this.fortuneArrayList=fortuneArrayList;
        this.fortuneEntity=fortuneEntity;

        this.fortuneEntityList=fortuneEntityList;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_share_fortune_bottom_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tvMessage = findViewById(R.id.share_title);
        tvMessage.setText(message);

        ImageView icon_qrcode = findViewById(R.id.icon_qrcode);

        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(LocalConfigStore.getInstance().getRegisterPage(), 48, 48);
        icon_qrcode.setImageBitmap(mBitmap);
        RecyclerView recycle_fortune_grade=findViewById(R.id.recycle_fortune_grade);

        RecyclerView recycle_fortune_bag=findViewById(R.id.recycle_fortune_bag);


        TextView tv_fortune=findViewById(R.id.tv_fortune);
        TextView tv_lucky_color=findViewById(R.id.tv_lucky_color);

        TextView tv_lucky_number=findViewById(R.id.tv_lucky_number);
        TextView tv_fortune_tips=findViewById(R.id.tv_fortune_tips);
        TextView tv_fortune_score=findViewById(R.id.tv_fortune_score);
        tv_fortune.setText(getResources().getText(R.string.tv_fortune) + fortuneEntity.getPosition().getFinance());//财运位
        tv_lucky_color.setText(getResources().getText(R.string.tv_lucky_color) + fortuneEntity.getColor());//幸运色
        tv_lucky_number.setText(getResources().getText(R.string.tv_lucky_number) + fortuneEntity.getNumber());//幸运数
        tv_fortune_tips.setText(fortuneEntity.getGeneral_comment().getTips());//幸运提示
        List<FortuneGradeEntity> fortuneGradeEntityList = new ArrayList<>();
        List<FortuneListImageEntity> fortuneListImageEntityList = new ArrayList<>();


        tv_fortune_score.setText(fortuneEntity.getGeneral_comment().getAverage()+fortuneEntity.getGeneral_comment().getAdd_fortune_score()+"");
        for (int i = 0; i < fortuneArrayList.length; i++) {
            FortuneGradeEntity entity = new FortuneGradeEntity(fortuneEntityList.get(i).getScore(),
                    fortuneArrayList[i]);
            fortuneGradeEntityList.add(entity);

            FortuneListImageEntity fortuneListImageEntity = new FortuneListImageEntity(fortuneArrayList[i], fortuneEntityList.get(i).getIcon());
            fortuneListImageEntityList.add(fortuneListImageEntity);
        }
        recycle_fortune_grade.setAdapter(new FortuneGradeAdapter(fortuneGradeEntityList,
                mContext));

        FortuneBagShareAdapter fortuneBagAdapter =
                new FortuneBagShareAdapter(fortuneListImageEntityList);
        recycle_fortune_bag.setAdapter(fortuneBagAdapter);



        // 微信
        findViewById(R.id.share_wx_layout).setOnClickListener(view -> {
            onDialogClickListener.onDialogFortuneClick(0, findViewById(R.id.share_layout));
            dialog.dismiss();
        });

        // 朋友圈
        findViewById(R.id.share_pyq_layout).setOnClickListener(view -> {
            onDialogClickListener.onDialogFortuneClick(1, findViewById(R.id.share_layout));
            dialog.dismiss();
        });

        // 保存相册
        findViewById(R.id.save_xc_layout).setOnClickListener(view -> {
            onDialogClickListener.onDialogFortuneClick(2, findViewById(R.id.share_layout));
            dialog.dismiss();
        });

        findViewById(R.id.tv_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        findViewById(R.id.tv_btn_1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    public interface OnDialogFortuneClickListener {

        void onDialogFortuneClick(int i, View v);
    }
}
