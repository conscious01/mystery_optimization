package com.zgzx.metaphysics.ui.activities;

import android.os.Bundle;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseRequestActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.image.GlideEngine;

import java.util.List;
import java.util.stream.Collectors;

import and.fast.widget.image.add.AddImageLayout;
import and.fast.widget.image.add.OnAddClickListener;
import butterknife.BindView;

/**
 * 编辑师傅照片
 */
public class EditMasterPhotoActivity extends BaseRequestActivity implements OnAddClickListener {

    @BindView(R.id.add_image_view) AddImageLayout mAddImageView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_edit_master_photo;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        ActivityTitleHelper.setTitle(this, getString(R.string.photo_show), getString(R.string.edit));

        // 添加照片
        mAddImageView.setOnAddClickListener(this);
    }

    @Override
    public void add() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(Constants.MASTER_PHOTO_SIZE - mAddImageView.obtainData().size())
                .isCompress(true)
                .isPreviewVideo(false)
                .forResult(new OnResultCallbackListener<LocalMedia>() {

                    @Override
                    public void onResult(List<LocalMedia> result) {
                        mAddImageView.addPath(result.stream().map(LocalMedia::getRealPath).collect(Collectors.toList()));
                    }

                    @Override
                    public void onCancel() {
                        // 取消
                    }

                });
    }
}
