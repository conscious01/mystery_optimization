package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.presenters.FateBookCatePresenter;
import com.zgzx.metaphysics.controller.presenters.PayFateBookPresenter;
import com.zgzx.metaphysics.controller.views.core.ICallback;
import com.zgzx.metaphysics.controller.views.core.ICallbackResult;
import com.zgzx.metaphysics.controller.views.impl.ToastRequestStatusView;
import com.zgzx.metaphysics.model.RemoteDataSource;
import com.zgzx.metaphysics.model.entity.CatePriceEntity;
import com.zgzx.metaphysics.model.event.BuyFateBookEvent;
import com.zgzx.metaphysics.model.event.FateBookBuyEvent;
import com.zgzx.metaphysics.network.WebApiConstants;
import com.zgzx.metaphysics.ui.activities.PayMethodActivity;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.NumberUtil;
import com.zgzx.metaphysics.utils.SpannableStringHelper;
import com.zgzx.metaphysics.utils.encrypt.HMacMD5Util;

import org.greenrobot.eventbus.EventBus;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 购买命书
 */
public class FateBookCatePriceDialog extends BottomPopupView implements
        ICallbackResult<CatePriceEntity>,
        ICallback,
        BaseQuickAdapter.OnItemChildClickListener {

    public static void show(Context context, int fateBookId, int chapterId) {
        new XPopup.Builder(context)
                .enableDrag(false)
                .isDestroyOnDismiss(true)
                .asCustom(new FateBookCatePriceDialog(context, fateBookId, chapterId))
                .show();
    }


    @BindView(R.id.tv_full_number) TextView mTvFullNumber;
    @BindView(R.id.tv_single_number) TextView mTvSingleNumber;
    @BindView(R.id.dialog_iv_close) ImageView mDialogIvClose;
    @BindView(R.id.dialog_but_confirm) Button mDialogButConfirm;
    @BindView(R.id.dialog_recycler_view) RecyclerView mDialogRecyclerView;
    @BindView(R.id.layout_single_times) RelativeLayout mLayoutSingleTimes;
    @BindView(R.id.layout_full_times) RelativeLayout mLayoutFullTimes;
    @BindView(R.id.layout_remaining_times) LinearLayout mLayoutRemainingTimes;

    private int onlyOneFreeNum, memberFreeNum;

    private int mChapterId, mFateBookId;
    //    private int mSelectedPrice;
    private FateBookPriceAdapter mAdapter;
    private FateBookCatePresenter mPresenter;
    private PayFateBookPresenter mPayFateBookPresenter;
    private int[] idArray;
    private ToastRequestStatusView mStatusView;

    public FateBookCatePriceDialog(@NonNull Context context, int fateBookId, int chapterId) {
        super(context);
        this.mChapterId = chapterId;
        this.mFateBookId = fateBookId;

        // 列表
        mAdapter = new FateBookPriceAdapter();
        mAdapter.setOnItemChildClickListener(this);

        // 请求
        mPresenter = new FateBookCatePresenter();
        mPresenter.setModelAndView(this);
        mPresenter.request(fateBookId);

        mPayFateBookPresenter = new PayFateBookPresenter();
        mPayFateBookPresenter.setModelAndView(this);

        mStatusView = new ToastRequestStatusView(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_fate_book;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ButterKnife.bind(this);

        // 取消
        mDialogIvClose.setOnClickListener(v -> dismiss());
        mDialogRecyclerView.setAdapter(mAdapter);

        // 支付
        mDialogButConfirm.setOnClickListener(v -> {
            dismiss();

            int isAll = 1;
            ArrayList<Integer> list = new ArrayList<>();
            List<CatePriceEntity.PayDataBean> data = mAdapter.getData();
            for (CatePriceEntity.PayDataBean datum : data) {
                if (datum.getId() == 0 && datum.isSelected()) {
                    isAll = 2;
                    break;
                }

                if (datum.isSelected() && datum.getId() != 0) {
                    list.add(datum.getId());
                }
            }
          long  time = new Date().getTime() / 1000;
            if (mLayoutSingleTimes.isSelected()) {
                // password=12345, 后台特殊要求
                Map<String, Object> map = new HashMap<>();
                map.put("lifebook_id", mFateBookId);
                map.put("pay_pass", "12345");
                map.put("cate_id", setArray(list));
                map.put("is_all", 1);
                map.put("pay_type", WebApiConstants.PAY_TYPE_SINGLE);

                map.put("timestamp", time+ LocalConfigStore.getInstance().getTimestamp());
                String str= HMacMD5Util.getMapToString(map);
                String sign = null;
                try {
                    sign = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                mPayFateBookPresenter.buy(mFateBookId, "12345", 1, WebApiConstants.PAY_TYPE_SINGLE,  setArray(list),LocalConfigStore.getInstance().getAk(),time+ LocalConfigStore.getInstance().getTimestamp(),sign);

            } else if (mLayoutFullTimes.isSelected()) {
                // password=12345, 后台特殊要求
                Map<String, Object> map = new HashMap<>();
                map.put("lifebook_id", mFateBookId);
                map.put("pay_pass", "12345");
                map.put("cate_id", setArray(list));
                map.put("is_all", 1);
                map.put("pay_type", WebApiConstants.PAY_TYPE_ALL);

                map.put("timestamp", time+ LocalConfigStore.getInstance().getTimestamp());
                String str= HMacMD5Util.getMapToString(map);
                String sign = null;
                try {
                    sign = HMacMD5Util.bytesToHexString(HMacMD5Util.getHmacMd5Bytes(LocalConfigStore.getInstance().getKey().getBytes(), str.getBytes()));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                mPayFateBookPresenter.buy(mFateBookId, "12345", 1, WebApiConstants.PAY_TYPE_ALL, setArray(list),LocalConfigStore.getInstance().getAk(),time+ LocalConfigStore.getInstance().getTimestamp(),sign);

            } else {
                getContext().startActivity(PayMethodActivity.newIntent(
                        getContext(),
                        mAdapter.getSelectedPrice(),
                        mFateBookId,
                        isAll,
                        list,1
                ));

            }
        });
    }

    private String setArray(ArrayList<Integer> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i==list.size()-1){
                stringBuilder.append(list.get(i));
            }else {
                stringBuilder.append(list.get(i)).append(",");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void successful(CatePriceEntity result) {
//        dialog.dismiss();
//        EventBus.getDefault().post(new BuyFateBookEvent());
        requestLayout();
        List<CatePriceEntity.PayDataBean> payData = result.getPay_data();

        // 单项解锁
        onlyOneFreeNum = result.getOnlyone_free_num();

        // 全部解锁
        memberFreeNum = result.getMember_free_num();

        mTvSingleNumber.setText(String.format(getResources().getString(R.string.placeholder_remaining_times), onlyOneFreeNum));
        mLayoutSingleTimes.setOnClickListener(v -> {
            mLayoutFullTimes.setSelected(false);
            mLayoutSingleTimes.setSelected(!mLayoutSingleTimes.isSelected());

            // 是否有选择价格
            int count = 0;
            boolean hasSelected = false;
            for (CatePriceEntity.PayDataBean bean : payData) {
                if (bean.isSelected()) {
                    hasSelected = true;
                    count += 1;
                }
            }

            // 根据单项选择, 购买
            if (mLayoutSingleTimes.isSelected() && !hasSelected) {
                for (int i = 0; i < payData.size(); i++) {
                    CatePriceEntity.PayDataBean bean = payData.get(i);
                    if (i < onlyOneFreeNum - 1) {
                        mAdapter.setSelected(bean.getId());
                    }
                }

                mDialogButConfirm.setEnabled(true);
            }

            // 选择大于可用
            if (count > onlyOneFreeNum) {
                for (int i = onlyOneFreeNum; i < payData.size(); i++) {
                    mAdapter.getItem(i).setSelected(false);
                }

                mAdapter.notifyDataSetChanged();
            }

            if (mLayoutSingleTimes.isSelected()) {
                mDialogButConfirm.setText(String.format(getResources().getString(R.string.placeholder_to_pay), 0));

            } else {
                mDialogButConfirm.setText(String.format(getResources().getString(R.string.placeholder_to_pay),
                        mAdapter.getSelectedPrice()));
            }

        });

        // 全部解锁
        mTvFullNumber.setText(String.format(getResources().getString(R.string.placeholder_remaining_times), memberFreeNum));
        mLayoutFullTimes.setOnClickListener(v -> {
            mLayoutSingleTimes.setSelected(false);
            mLayoutFullTimes.setSelected(!mLayoutFullTimes.isSelected());

            if (mLayoutFullTimes.isSelected()) {
                mAdapter.setSelected(0);
                mDialogButConfirm.setText(String.format(getResources().getString(R.string.placeholder_to_pay), 0));

            } else {
                mAdapter.remove(0);
            }
        });

        // 单项
        if (onlyOneFreeNum <= 0) {
            mLayoutSingleTimes.setVisibility(View.GONE);
        }

        // 全部
        if (memberFreeNum <= 0) {
            mLayoutFullTimes.setVisibility(View.GONE);
        }

        if (memberFreeNum <= 0 && onlyOneFreeNum <= 0) {
            mLayoutRemainingTimes.setVisibility(View.GONE);
        }


        int totalPrice = 0;

        // 选择价格
        for (CatePriceEntity.PayDataBean bean : payData) {

            if (bean.getId() != 0) {
                totalPrice += bean.getPrice();
            }
        }

        mAdapter.setTotalPrice(totalPrice);
        mAdapter.setNewData(payData);
        mAdapter.setSelected(mChapterId);

        for (CatePriceEntity.PayDataBean bean : payData) {
            if (bean.getId() == mChapterId) {
                mDialogButConfirm.setText(String.format(getResources().getString(R.string.placeholder_to_pay), mAdapter.getSelectedPrice()));
                break;
            }
        }


    }


    @Override
    public void loading() {
        mStatusView.loading();
    }

    @Override
    public void failure(Throwable throwable) {
        mStatusView.failure(throwable);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mPresenter.clear();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.check_box) {

            CheckBox checkBox = (CheckBox) view;
            CatePriceEntity.PayDataBean item = mAdapter.getItem(position);

            if (checkBox.isChecked()) {
                mAdapter.setSelected(item.getId());

            } else {
                mAdapter.remove(item.getId());
            }

            // 是否选择价格
            mDialogButConfirm.setEnabled(mAdapter.getSelectedPrice() > 0);

            // 如果全部处于点击状态
            if (mLayoutFullTimes.isSelected()) {
                mLayoutFullTimes.setSelected(false);
            }

            // 如果单次免费处于点击状态
            if (mLayoutSingleTimes.isSelected()) {
                int count = 0;
                boolean hasSelected = false;
                List<CatePriceEntity.PayDataBean> data = mAdapter.getData();
                for (CatePriceEntity.PayDataBean bean : data) {
                    if (bean.isSelected()) {
                        count += 1;
                        hasSelected = true;
                    }
                }

                // 如果超过则取消,单次免费, 或没有选中的价格
                if (count > onlyOneFreeNum || !hasSelected) {
                    mLayoutSingleTimes.setSelected(false);
                }
            }

            // 价格
            if (mLayoutFullTimes.isSelected() || mLayoutSingleTimes.isSelected()){
                mDialogButConfirm.setText(String.format(getResources().getString(R.string.placeholder_to_pay), 0));

            } else {
                mDialogButConfirm.setText(String.format(getResources().getString(R.string.placeholder_to_pay), mAdapter.getSelectedPrice()));
            }
        }
    }


    @Override
    public void successful() {
        EventBus.getDefault().post(new FateBookBuyEvent());
        AppToast.showShort(getResources().getString(R.string.successful));
        dialog.dismiss();
    }


    // 命书价格
    public static class FateBookPriceAdapter extends BaseQuickAdapter<CatePriceEntity.PayDataBean, BaseViewHolder> {

        private int totalPrice;

        public FateBookPriceAdapter() {
            super(R.layout.recycle_item_dialog_fate_book_price);
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, CatePriceEntity.PayDataBean item) {
            helper.setText(R.id.check_box, item.getName())
//                    .setText(R.id.tv_price, NumberUtil.format(item.getPrice()))
                    .setChecked(R.id.check_box, item.isSelected())
                    .addOnClickListener(R.id.check_box);

            // TODO 未国际化
            if (item.getId() == 0) {
                helper.setText(R.id.tv_price, SpannableStringHelper
                        .newBuilder(totalPrice + "元")
                        .striketrough()
                        .append("\t\t")
                        .append(NumberUtil.format(item.getPrice()) + "元")
                        .foregroundColor(helper.itemView.getResources().getColor(R.color.colorAccent))
                        .build()
                );
            } else {
                helper.setText(R.id.tv_price, NumberUtil.format(item.getPrice()) + "元");
            }
        }

        public void setSelected(int chapterId) {
            List<CatePriceEntity.PayDataBean> data = getData();

            if (chapterId != 0) { // 不为全部
                for (CatePriceEntity.PayDataBean datum : data) {
                    if (datum.getId() == chapterId) {
                        datum.setSelected(true);
                        break;
                    }
                }

                // 是否除全部外，全部选中
                //boolean isAllSelected = false;
                boolean hasAllSelected = false;
                int selectedCount = 0;
                for (CatePriceEntity.PayDataBean datum : data) {
                    if (datum.getId() != 0 && datum.isSelected()) {
                        selectedCount += 1;
                    }

                    if (datum.getId() == 0) {
                        hasAllSelected = true;
                    }
                }

                // 选中全部
                if (hasAllSelected && selectedCount == data.size() - 1) {
                    for (CatePriceEntity.PayDataBean datum : data) {
                        datum.setSelected(true);
                    }
                }


            } else { // 点击全部时，选中全部
                for (CatePriceEntity.PayDataBean datum : data) {
                    datum.setSelected(true);
                }
            }

            notifyDataSetChanged();
        }

        public void remove(int chapterId) {
            List<CatePriceEntity.PayDataBean> data = getData();

            // 反选全部
            if (chapterId == 0) {
                for (CatePriceEntity.PayDataBean datum : data) {
                    datum.setSelected(false);
                }


            } else {

                for (CatePriceEntity.PayDataBean datum : data) {
                    if (datum.getId() == chapterId || datum.getId() == 0) {
                        datum.setSelected(false);
                    }
                }

            }


            notifyDataSetChanged();
        }

        public int getSelectedPrice() {
            int price = 0;

            List<CatePriceEntity.PayDataBean> data = getData();
            for (CatePriceEntity.PayDataBean datum : data) {
                if (datum.getId() == 0 && datum.isSelected()) {
                    return datum.getPrice();
                }

                if (datum.getId() != 0 && datum.isSelected()) {
                    price += datum.getPrice();
                }
            }

            return price;
        }
    }

}
