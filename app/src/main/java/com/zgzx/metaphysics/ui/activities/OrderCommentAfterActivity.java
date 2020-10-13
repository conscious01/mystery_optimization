package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.willy.ratingbar.ScaleRatingBar;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.event.Go2HomePageEvent;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class OrderCommentAfterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar simpleRatingBar;
    @BindView(R.id.tv_points)
    TextView tvPoints;
    @BindView(R.id.tv_go_2_home)
    TextView tvGo2Home;

    @Override
    protected int getContentLayoutId() {
        return R.layout.order_comment_after_activity;
    }

//    @Override
//    protected boolean useEventBus() {
//        return true;
//    }

    public static void start(Context context, float points) {
        Intent intent = new Intent(context, OrderCommentAfterActivity.class);
        intent.putExtra(Constants.EXT_TYPE, points);
        context.startActivity(intent);

    }

    float mPoints;

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, R.string.comment_detail);
        mPoints = getIntent().getFloatExtra(Constants.EXT_TYPE, 5.0f);
        tvPoints.setText((int) mPoints + StringUtil.getScoreDes((int) mPoints, this));
        simpleRatingBar.setRating(mPoints);
        tvGo2Home.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new Go2HomePageEvent());
        startActivityAndFinish(new Intent(OrderCommentAfterActivity.this, MainActivity.class));

    }
}
