package com.zgzx.metaphysics.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.ui.fragments.OrderMyFragment;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的问题
 */
public class MyOrderActivity extends BaseActivity {


    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.stl)
    SlidingTabLayout stl;
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    public int getContentLayoutId() {
        return R.layout.my_order_activity;
    }


    private int mPosition = 0;


    @Override
    protected void initialize(Bundle savedInstanceState) {
        String title;

        Intent intent = getIntent();
        if (intent != null) {
            mPosition = intent.getIntExtra(Constants.KEY, 0);
        }

        String[] titles;
        ArrayList<Fragment> fragments = new ArrayList<>();
        if (LocalConfigStore.getInstance().ifMaster()) {
            title = getString(R.string.my_answer);
            titles = new String[]{
                    getString(R.string.master_order_status_not_answered),
                    getString(R.string.master_order_status_all)
            };
            fragments.add(OrderMyFragment.createInstance(Constants.MASTER_ORDER_STATUS_WAIT_ANSWER));
            fragments.add(OrderMyFragment.createInstance(Constants.MASTER_ORDER_STATUS_ALL));

        } else {
            title = getString(R.string.my_questions_answer);

            titles = new String[]{
                    getString(R.string.order_status_not_paied),
                    getString(R.string.order_status_wait_answer),
                    getString(R.string.order_status_wait_comment),
                    getString(R.string.master_order_status_all)
            };
            fragments.add(OrderMyFragment.createInstance(Constants.USER_ORDER_STATUS_NOT_PAID));
            fragments.add(OrderMyFragment.createInstance(Constants.USER_ORDER_STATUS_WAIT_ANSWER));
            fragments.add(OrderMyFragment.createInstance(Constants.USER_ORDER_STATUS_WAIT_COMMENT));
            fragments.add(OrderMyFragment.createInstance(Constants.USER_ORDER_STATUS_ALL));
        }
        ActivityTitleHelper.setTitle(this, title);
        stl.setViewPager(vp, titles, this, fragments);
        stl.setCurrentTab(mPosition);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
