package com.zgzx.metaphysics.ui.fragments;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.apng.entity.AnimParams;
import com.apng.utils.FileUtils;
import com.apng.view.ApngImageView;
import com.apng.view.ApngLoader;
import com.apng.view.ApngSurfaceView;
import com.github.penfeizhou.animation.apng.APNGAssetLoader;
import com.github.penfeizhou.animation.apng.APNGDrawable;
import com.jaeger.library.StatusBarUtil;

import com.kris520.apngdrawable.ApngDrawable;
import com.kris520.apngdrawable.ApngImageLoadingListener;
import com.kris520.apngdrawable.ApngImageUtils;
import com.kris520.apngdrawable.ApngLoaderStart;
import com.kris520.apngdrawable.ApngPlayListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.controller.FateBookListController;
import com.zgzx.metaphysics.model.entity.FateBooksEntity;
import com.zgzx.metaphysics.model.event.FateBookBuyEvent;
import com.zgzx.metaphysics.model.event.OnFragmentChanged;
import com.zgzx.metaphysics.model.event.RefreshUseINFOEvent;
import com.zgzx.metaphysics.model.event.SupplementInformationEvent;
import com.zgzx.metaphysics.ui.activities.CreateFateBookActivity;
import com.zgzx.metaphysics.ui.activities.LoginActivity;
import com.zgzx.metaphysics.ui.adapters.FateBookPageAdapter;
import com.zgzx.metaphysics.ui.core.BaseRequestFragment;
import com.zgzx.metaphysics.ui.dialogs.FateBookEditDialog;
import com.zgzx.metaphysics.ui.dialogs.SimpleDialog;
import com.zgzx.metaphysics.utils.AndroidUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.OnClick;
import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;

import static android.content.Context.AUDIO_SERVICE;


/**
 * 命书fragment
 */
public class FateBookFragment extends BaseRequestFragment implements
        FateBookListController.View {


    @BindView(R.id.create_fate_book_layout)
    FrameLayout create_fate_book_layout;
    @BindView(R.id.fate_book_layout)
    FrameLayout fate_book_layout;
    @BindView(R.id.layout_title_container)
    LinearLayout layout_title_container;

    @BindView(R.id.edit_img)
    ImageView edit_img;
    @BindView(R.id.delteImg)
    ImageView delteImg;
    @BindView(R.id.add_fate_book)
    ImageView add_fate_book;
    @BindView(R.id.conpleteTextView)
    TextView completeTextView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.kmImgView)
    ImageView kmImgView;
    @BindView(R.id.kmImgView_start)
    ImageView kmImgView_start;

    @BindView(R.id.img_left)
    ImageView img_left;

    @BindView(R.id.img_right)
    ImageView img_right;


    @BindView(R.id.fate_book_viewpage)
    ViewPager fate_book_viewpage;
    @BindView(R.id.tv_zodiac)//生肖
            TextView tv_zodiac;
    @BindView(R.id.tv_zodiac_detail)//合拍生肖
            TextView tv_zodiac_detail;
    @BindView(R.id.tv_character)//八字
            TextView tv_character;
    @BindView(R.id.tv_character_detail)//八字
            TextView tv_character_detail;
    @BindView(R.id.tv_constellation)//星座
            TextView tv_constellation;
    @BindView(R.id.tv_constellation_detail)//合拍星座
            TextView tv_constellation_detail;
    private FateBookListController.Presenter mPresenter;
    private List<FateBooksEntity> mList;
    private int pos = 0;
    private static final String KM_IMAGE_PATH = "assets://icon_km_static.png";
    private static final String KM_IMAGE_PATH_START = "assets://icon_km_static.png";
    private BasePopupView popup;
    private boolean isCreateNew;

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fate_book;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        // 状态栏
        AndroidUtil.addStatusBarHeightPadding(findViewById(R.id.layout_title_container));
        // 请求
        mPresenter = new FateBookListController.Presenter();
        mPresenter.setModelAndView(this);
        getLifecycle().addObserver(mPresenter);
        ApngLoaderStart.init(getActivity());
        img_left.setVisibility(View.GONE);
        fate_book_viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                tv_zodiac.setText(getResources().getString(R.string.signed_zodiac) + mList.get(position).getAbstractX().getZodiac().getValue());
                tv_zodiac_detail.setText(getResources().getString(R.string.signed_similar_zodiac) + TextUtils.join(",", mList.get(position).getAbstractX().getZodiac().getMatch()));
                tv_character.setText(getResources().getString(R.string.signed_character) + mList.get(position).getAbstractX().getBazi().getValue());
                tv_character_detail.setText(getResources().getString(R.string.signed_similar_character) + TextUtils.join(",", mList.get(position).getAbstractX().getBazi().getMatch()));
                tv_constellation.setText(getResources().getString(R.string.signed_constellation) + mList.get(position).getAbstractX().getHoroscope().getValue());
                tv_constellation_detail.setText(getResources().getString(R.string.signed_similar_constellation) + TextUtils.join(",", mList.get(position).getAbstractX().getHoroscope().getMatch()));
                name.setText(mList.get(position).getReal_name());

                if (position == 0) {
                    img_left.setVisibility(View.GONE);
                } else {
                    img_left.setVisibility(View.VISIBLE);
                }
                if (position == mList.size() - 1) {
                    img_right.setVisibility(View.GONE);
                } else {
                    img_right.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        initSoundAndPlayFirst();

        APNGAssetLoader assetLoader = new APNGAssetLoader(getActivity(), "icon_km_static.png");
        APNGDrawable apngDrawable = new APNGDrawable(assetLoader);
        apngDrawable.setLoopLimit(-1);

        kmImgView.setImageDrawable(apngDrawable);
//        File file1 = FileUtils.processApngFile(KM_IMAGE_PATH, getActivity());
//        ApngLoader.getInstance().loadApng(file1.getAbsolutePath(), kmImgView);
        mFragrmentPosition = 1;//fragment创建之后表示在当前页面
    }
//    private void playAnim(){
//        File file = FileUtils.processApngFile(KM_IMAGE_PATH, getActivity());
//        if(file == null) return;
//        AnimParams animItem = new AnimParams();
//         animItem.scaleType=AnimParams.WIDTH_OR_HEIGHT_SCALE_TYPE;
//        animItem.loopCount = AnimParams.PLAY_4_LOOP;
//        animItem.imagePath = file.getAbsolutePath();
//        animItem.isHasBackground=false;
//        animItem.align=2;
//
//        kmImgView.addApngForPlay(animItem);
//
//    }
    private MediaPlayer mMediePlayer;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean mPlayControl;


    private boolean getPlayControl() {
        mPlayControl = true;
        AudioManager audioService = (AudioManager) getContext().getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            mPlayControl = false;
        }
        return mPlayControl;
    }

    private void initSoundAndPlayFirst() {

        if (mMediePlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

            mMediePlayer = new MediaPlayer();
            mMediePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediePlayer.setOnCompletionListener(firstPlayListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.mingshu);
            try {
                mMediePlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(),
                        file.getLength());
                file.close();
                mMediePlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mMediePlayer.prepare();
            } catch (IOException e) {
                mMediePlayer = null;
            }
        }
    }

    private final MediaPlayer.OnCompletionListener firstPlayListener =
            new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    playBackGround();
                }
            };

    private final MediaPlayer.OnCompletionListener secondPlayListener =
            new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.seekTo(0);
                }
            };

    @Override
    public void onResume() {
        super.onResume();
        if (mFragrmentPosition == 1) {
            startPlay();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        pausePlay();
    }


    private void pausePlay() {
        if (mMediePlayer != null) {
            mMediePlayer.pause();
            mMediePlayer.setOnCompletionListener(null);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediePlayer != null) {
            mMediePlayer.stop();
            mMediePlayer = null;
        }
    }


    private void playBackGround() {
        ifFirstFinished = true;
        mMediePlayer = null;
        mMediePlayer = new MediaPlayer();
        mMediePlayer.setOnCompletionListener(secondPlayListener);

        AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.back);
        try {
            mMediePlayer.setDataSource(file.getFileDescriptor(),
                    file.getStartOffset(),
                    file.getLength());
            file.close();
            mMediePlayer.setLooping(true);
            mMediePlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
            mMediePlayer.prepare();
            startPlay();
        } catch (IOException e) {
            mMediePlayer = null;
        }
    }


    boolean ifFirstFinished = false;

    private void startPlay() {
        if (getPlayControl() && mMediePlayer != null) {
            if (!ifFirstFinished) {
                mMediePlayer.setOnCompletionListener(firstPlayListener);
            } else {
                mMediePlayer.setOnCompletionListener(secondPlayListener);

            }
            mMediePlayer.start();
        }
    }


    @OnClick({R.id.edit_img, R.id.add_fate_book, R.id.delteImg, R.id.conpleteTextView,
            R.id.img_right, R.id.img_left,R.id.kmImgView})
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.edit_img:


                view.setTag(R.id.edit_img,
                        view.getTag(R.id.edit_img) == null ?
                                1 : Integer.valueOf(view.getTag(R.id.edit_img).toString()) + 1);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(edit_img, "rotation",
                        360f * Integer.valueOf(view.getTag(R.id.edit_img).toString()));
                objectAnimator.setDuration(1000);
                objectAnimator.start();
                popup = FateBookEditDialog.show(getActivity(), edit_img,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //  fate_book_layout.setVisibility(View.GONE);
                                edit_img.setVisibility(View.INVISIBLE);
                                layout_title_container.setVisibility(View.VISIBLE);
                                popup.dismiss();

                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                createFateBook_noAnmation();
                                popup.dismiss();
                            }
                        });

                break;

            case R.id.delteImg:
                SimpleDialog dialog = new SimpleDialog(getContext());
                dialog.setMessage(R.string.tv_delete_fate_book);
                dialog.setNegative(R.string.tv_think, v -> dialog.dismiss());
                dialog.setPositive(R.string.confirm, v -> {
                    dialog.dismiss();
                    deleteFateBook();

                });
                new XPopup.Builder(getContext())
                        .isDestroyOnDismiss(true)
                        .enableShowWhenAppBackground(false)
                        .asCustom(dialog)
                        .show();
                break;
            case R.id.conpleteTextView:
                edit_img.setVisibility(View.VISIBLE);
                layout_title_container.setVisibility(View.GONE);
                break;
            case R.id.add_fate_book:
                if (LocalConfigStore.getInstance().isLogin()) {

                    createFateBook();
                } else {
                    startActivityForResult(LoginActivity.newIntent(getActivity(), 1), 0);

                }
                break;
            case R.id.img_left:
                fate_book_viewpage.setCurrentItem(pos - 1);
                break;
            case R.id.img_right:
                fate_book_viewpage.setCurrentItem(pos + 1);
                break;
        }
    }

    private void deleteFateBook() {
        mPresenter.delete(mList.get(pos).getId(), pos);
    }

    // 创建命书
    private void createFateBook() {

        kmImgView.setVisibility(View.GONE);
        kmImgView_start.setVisibility(View.VISIBLE);



        ApngLoaderStart.loadImage(ApngImageUtils.Scheme.ASSETS.wrap("icon_start_create.png"),
                kmImgView_start, new ApngImageLoadingListener(new ApngPlayListener() {
                    @Override
                    public void onAnimationStart(ApngDrawable drawable) {

                    }

                    @Override
                    public void onAnimationEnd(ApngDrawable drawable) {

                    }

                    @Override
                    public void onAnimationRepeat(ApngDrawable drawable) {
                        startActivityForResult(CreateFateBookActivity.newIntent(getActivity(), 1)
                                , 0);
                        kmImgView_start.setVisibility(View.GONE);
                        drawable.stop();
                        kmImgView.setVisibility(View.VISIBLE);
                    }
                }));

    }



    // 创建命书
    private void createFateBook_noAnmation() {
        if (mList.size() >= Constants.MAX_FATE_BOOK_NUMBER) {
            SimpleDialog dialog = new SimpleDialog(getContext());
            dialog.setTitle(R.string.create_fate_book_error);
            dialog.setPositive(R.string.understood, v -> dialog.dismiss());
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true)
                    .enableShowWhenAppBackground(false)
                    .asCustom(dialog)
                    .show();
        } else {
            startActivityForResult(CreateFateBookActivity.newIntent(getActivity(), 2), 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            isCreateNew = true;
            // 重新获取命书数据
            mPresenter.init();

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FateBookBuyEvent event) {
        mPresenter.init();
        fate_book_viewpage.setCurrentItem(pos);
    }

    int mFragrmentPosition;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFragmentChanged(OnFragmentChanged onFragmentChanged) {
        mFragrmentPosition = onFragmentChanged.getPosition();
        if (isAdded() && !isDetached()) {
            if (onFragmentChanged.getPosition() == 1) { //选中命数页面
                startPlay();
            } else { //在其他页面
                pausePlay();
            }
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarUtil.setDarkMode(getActivity());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshUserINFO(RefreshUseINFOEvent event) {
        mPresenter.init();

    }

    @Override
    public void renderFateBooks(List<FateBooksEntity> list) {

        mList = list;
        if (list.size() > 0) {
            if (list.size()==1){
                img_right.setVisibility(View.GONE);
            }else {
                img_right.setVisibility(View.VISIBLE);
            }
            if (isCreateNew) {
                fate_book_layout.setVisibility(View.VISIBLE);
                create_fate_book_layout.setVisibility(View.GONE);
                tv_zodiac.setText(getResources().getString(R.string.signed_zodiac) + list.get(mList.size() - 1).getAbstractX().getZodiac().getValue());
                tv_zodiac_detail.setText(getResources().getString(R.string.signed_similar_zodiac) + TextUtils.join(",", mList.get(mList.size() - 1).getAbstractX().getZodiac().getMatch()));
                tv_character.setText(getResources().getString(R.string.signed_character) + list.get(mList.size() - 1).getAbstractX().getBazi().getValue());

                tv_character_detail.setText(getResources().getString(R.string.signed_similar_character) + TextUtils.join(",", mList.get(mList.size() - 1).getAbstractX().getBazi().getMatch()));

                tv_constellation.setText(getResources().getString(R.string.signed_constellation) + list.get(mList.size() - 1).getAbstractX().getHoroscope().getValue());

                tv_constellation_detail.setText(getResources().getString(R.string.signed_similar_constellation) + TextUtils.join(",", mList.get(mList.size() - 1).getAbstractX().getHoroscope().getMatch()));

                name.setText(list.get(mList.size() - 1).getReal_name());
                fate_book_viewpage.setAdapter(new FateBookPageAdapter(list));
                fate_book_viewpage.setCurrentItem(mList.size() - 1);
                pos=mList.size() - 1;
            } else {
                fate_book_layout.setVisibility(View.VISIBLE);
                create_fate_book_layout.setVisibility(View.GONE);
                tv_zodiac.setText(getResources().getString(R.string.signed_zodiac) + list.get(0).getAbstractX().getZodiac().getValue());
                tv_zodiac_detail.setText(getResources().getString(R.string.signed_similar_zodiac) + TextUtils.join(",", mList.get(0).getAbstractX().getZodiac().getMatch()));
                tv_character.setText(getResources().getString(R.string.signed_character) + list.get(0).getAbstractX().getBazi().getValue());

                tv_character_detail.setText(getResources().getString(R.string.signed_similar_character) + TextUtils.join(",", mList.get(0).getAbstractX().getBazi().getMatch()));

                tv_constellation.setText(getResources().getString(R.string.signed_constellation) + list.get(0).getAbstractX().getHoroscope().getValue());

                tv_constellation_detail.setText(getResources().getString(R.string.signed_similar_constellation) + TextUtils.join(",", mList.get(0).getAbstractX().getHoroscope().getMatch()));

                name.setText(list.get(0).getReal_name());
                fate_book_viewpage.setAdapter(new FateBookPageAdapter(list));
                fate_book_viewpage.setCurrentItem(pos);

            }

        } else {
            fate_book_layout.setVisibility(View.GONE);
            create_fate_book_layout.setVisibility(View.VISIBLE);
            Animation();
//            playAnim();
//            File file1 = FileUtils.processApngFile(KM_IMAGE_PATH, getActivity());
//            ApngLoader.getInstance().loadApng(file1.getAbsolutePath(), kmImgView);



        }


    }

    private void Animation() {

        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                // X轴的开始位置
                android.view.animation.Animation.RELATIVE_TO_SELF, 0f,
                // X轴的结束位置
                android.view.animation.Animation.RELATIVE_TO_SELF, 0f,
                // Y轴的开始位置
                android.view.animation.Animation.RELATIVE_TO_SELF, 0f,
                // Y轴的结束位置
                android.view.animation.Animation.RELATIVE_TO_SELF, -0.1f);
        translateAnimation.setDuration(2000);
        translateAnimation.setRepeatCount(Animation.INFINITE);  //  设置动画重复次数

        translateAnimation.setRepeatMode(android.view.animation.Animation.REVERSE);
        //translateAnimation.setRepeatMode(Animation.RESTART);    //重新从头执行
        //translateAnimation.setRepeatMode(Animation.REVERSE);  //反方向执行

        animationSet.addAnimation(translateAnimation);
        add_fate_book.setAnimation(animationSet);

    }


    @Override
    public void deleteSuccessful(int position) {
        mPresenter.init();
        edit_img.setVisibility(View.VISIBLE);
        layout_title_container.setVisibility(View.GONE);
        pos=position;
    }

    @Subscribe
    public void onEvent(SupplementInformationEvent event) {
        mPresenter.init();
    }

    @Override
    public void loading() {

    }

    @Override
    public void failure(Throwable throwable) {

    }



    public static class ScaleTransformer implements GalleryLayoutManager.ItemTransformer {

        private boolean isScale;

        public ScaleTransformer(boolean isScale) {
            this.isScale = isScale;
        }

        @Override
        public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {

            if (isScale) {
                float scale = 1 - 0.3f * Math.abs(fraction);
                item.setScaleY(scale);
//                item.setScaleX(scale);

            } else {

                item.setScaleY(1F);
                item.setScaleX(1F);
            }
        }
    }

}
