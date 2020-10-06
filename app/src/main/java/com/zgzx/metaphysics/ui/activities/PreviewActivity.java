package com.zgzx.metaphysics.ui.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.OrderResultEntity;
import com.zgzx.metaphysics.ui.adapters.OrderAdapter;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.AppNotificationJava;
import com.zgzx.metaphysics.widgets.CountdownTextview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviewActivity extends BaseActivity implements OnRefreshListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.tv_first)
    CountdownTextview tvFirst;

    @Override
    protected int getContentLayoutId() {
        return R.layout.previews;
    }

    private TestAdapter testAdapter;

    @Override
    protected void initialize(Bundle savedInstanceState) {

        tvFirst.setTime(100);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        smartRefreshLayout.setOnRefreshListener(this);
        List<bean> beanList = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
//            date.add(new TestEntity("--" + i + "--", 10));
            beanList.add(new bean("倒计时开始于" + i * 30, i * 30));
        }
         testAdapter = new TestAdapter();
//        RecyclerViewAdapter testAdapter = new RecyclerViewAdapter(this, null);
        recyclerView.setAdapter(testAdapter);
//        testAdapter.setData(date);
        testAdapter.setNewData(beanList);

        button.setOnClickListener(new View.OnClickListener() {
            /**
             */
            @Override
            public void onClick(View v) {
//                发送通知
                Intent intent = new Intent(PreviewActivity.this, ImageViewActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(PreviewActivity.this, 0, intent, 0);
                AppNotificationJava.sendNotification(PreviewActivity.this, AppNotificationJava.msgChannelId,
                        "title", "text", R.mipmap.ic_launcher, R.drawable.icon_logo_bg, pendingIntent);
//                startActivity(new Intent(PreviewActivity.this, ImageViewActivity.class));
            }
        });

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        List<bean> beanList = new ArrayList<>();

        for (int i = 1; i < 50; i++) {
//            date.add(new TestEntity("--" + i + "--", 10));
            beanList.add(new bean("刷新之后的倒计时" + i * 50, i * 50));
        }
        testAdapter.setNewData(beanList);
    }

    class bean {
        String title;
        int countDowntime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCountDowntime() {
            return countDowntime;
        }

        public void setCountDowntime(int countDowntime) {
            this.countDowntime = countDowntime;
        }

        public bean(String title, int countDowntime) {
            this.title = title;
            this.countDowntime = countDowntime;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (testAdapter!=null) {
            testAdapter.clear();
        }
    }

    class TestAdapter extends BaseQuickAdapter<bean, BaseViewHolder> {


        public TestAdapter() {
            super(R.layout.recycle_item_test);
            handler.sendEmptyMessageDelayed(0, 1000);

        }


        /**
         * 用完记得remove掉message，handler优化
         */
        public void clear(){
            handler.removeMessages(0);

        }

        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                System.out.println("countdown-->" + "TestAdapter handler handing message");
                for (bean obj : TestAdapter.this.getData()) {
                    if (obj.getCountDowntime() > 0)
                        --obj.countDowntime;
                }
                notifyDataSetChanged();
                sendEmptyMessageDelayed(0, 1000);
            }
        };

        @Override
        protected void convert(@NonNull BaseViewHolder helper, bean item) {
            System.out.println("countdown-->" + "convert");

            helper.setText(R.id.tv_title, item.getTitle());
            helper.setText(R.id.tv_time, item.getCountDowntime()+"");

        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }
}
