package com.zgzx.metaphysics.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.image.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewActivity extends BaseActivity {
    @BindView(R.id.photo_view)
    PhotoView photoView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.iamge_view_activity;
    }


    public static void start(Context context, String url) {
        Intent intent = new Intent(context, ImageViewActivity.class);
        intent.putExtra(Constants.EXT_TYPE, url);
        context.startActivity(intent);
    }

    String url;

    @Override
    protected void initialize(Bundle savedInstanceState) {
        url = getIntent().getStringExtra(Constants.EXT_TYPE);
        GlideApp.with(photoView).load(url).into(photoView);
        photoView.setOnClickListener(v -> finish());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
