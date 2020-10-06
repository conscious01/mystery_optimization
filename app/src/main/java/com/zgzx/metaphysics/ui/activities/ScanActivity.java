package com.zgzx.metaphysics.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.core.BaseActivity;
import com.zgzx.metaphysics.utils.ActivityTitleHelper;
import com.zgzx.metaphysics.utils.AppToast;
import com.zgzx.metaphysics.utils.zxing.camera.CameraManager;
import com.zgzx.metaphysics.utils.zxing.decoding.CaptureActivityHandler;
import com.zgzx.metaphysics.utils.zxing.decoding.InactivityTimer;
import com.zgzx.metaphysics.utils.zxing.decoding.RGBLuminanceSource;
import com.zgzx.metaphysics.utils.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import androidx.annotation.NonNull;
import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class ScanActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks,
        SurfaceHolder.Callback {

    private static final int REQUEST_CODE_SCAN = 1;
    private static final int REQUEST_CODE_SCAN_GALLERY = 100;

    public static final String KEY_SCAN_RESULT = "key_scan_result";
    @BindView(R.id.iv_arrow_back)
    ImageView ivArrowBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.layout_title_container)
    RelativeLayout layoutTitleContainer;
    @BindView(R.id.scanner_view)
    SurfaceView scannerView;
    @BindView(R.id.viewfinder_content)
    ViewfinderView viewfinderContent;

    private CaptureActivityHandler mCaptureActivityHandler;
    private boolean hasSurface;
    private Vector<BarcodeFormat> mBarcodeFormats;
    private String mCharacterSet;
    private InactivityTimer mInactivityTimer;
    private MediaPlayer mMediaPlayer;
    private boolean mPlayBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean mVibrate;
    private ProgressDialog mProgress;
    private String photo_path;
    private Bitmap mScanBitmap;
    //	private Button cancelScanButton;
    private boolean mInited;

    private final String[] PERMISSIONS_SCAN = new String[]{Manifest.permission.CAMERA};
    private String TAG = "ScanActivity";

    public static void start(Context context) {
        Intent intent = new Intent(context, ScanActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startForResult(Activity context, int requestCode) {
        Intent intent = new Intent(context, ScanActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTitleHelper.setTitle(this, R.string.scan_qr);

        Bundle args = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        if (!EasyPermissions.hasPermissions(this, PERMISSIONS_SCAN)) {
            EasyPermissions.requestPermissions(this,
                    getString(R.string.app_need_permission),
                    REQUEST_CODE_SCAN,
                    PERMISSIONS_SCAN);
        }
        initCamera();
    }


    private void initCamera() {
        CameraManager.init(getApplication());
        hasSurface = false;
        mInactivityTimer = new InactivityTimer(this);
        mInited = true;
    }


    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN_GALLERY:

                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 扫描二维码图片的方法
     *
     * @param path
     * @return
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //设置二维码内容的编码

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        mScanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0) sampleSize = 1;
        options.inSampleSize = sampleSize;
        mScanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(mScanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mInited) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.scanner_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            if (hasSurface) {
                initCamera(surfaceHolder);
            } else {
                surfaceHolder.addCallback(this);
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            }
            mBarcodeFormats = null;
            mCharacterSet = null;

            mPlayBeep = true;
            AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
                mPlayBeep = false;
            }
            initBeepSound();
            mVibrate = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mInited) {
            if (mCaptureActivityHandler != null) {
                mCaptureActivityHandler.quitSynchronously();
                mCaptureActivityHandler = null;
            }
            CameraManager.get().closeDriver();
        }
    }

    @Override
    protected void onDestroy() {
        if (mInited) {
            mInactivityTimer.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.scan_activity;
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        mInactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (TextUtils.isEmpty(resultString)) {
            AppToast.showShort(getString(R.string.scan_failed));
            finish();
        } else {
            parseScanResult(resultString);
        }
    }

    /**
     * 解析扫描之后的字符串
     */
    private void parseScanResult(String result) {
        LogUtils.aTag(TAG, "parseScanResult--> " + result);
        if (result != null && !result.isEmpty()) {
            if (result.contains("invite_code")) {
                String inviteCode = result.substring(result.length() - 4, result.length());
                LogUtils.aTag(TAG, "inviteCode--> " + inviteCode);
                finish(Activity.RESULT_OK, new Intent().putExtra(Constants.EXTRA_AREA_CODE, inviteCode));

            }

        }


    }


    /**
     * //需要将扫描的字符串返回（钱包地址）
     */
    private void returnResultString(String result) {

        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SCAN_RESULT, result);
        // 不能使用Intent传递大于40kb的bitmap，可以使用一个单例对象存储这个bitmap
        //            bundle.putParcelable("bitmap", barcode);
        resultIntent.putExtras(bundle);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (mCaptureActivityHandler == null) {
            mCaptureActivityHandler = new CaptureActivityHandler(this,
                    mBarcodeFormats,
                    mCharacterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderContent;
    }

    public Handler getCaptureActivityHandler() {
        return mCaptureActivityHandler;
    }

    public void drawViewfinder() {
        viewfinderContent.drawViewfinder();
    }

    private void initBeepSound() {
        if (mPlayBeep && mMediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
           setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mMediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(),
                        file.getLength());
                file.close();
                mMediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                mMediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (mPlayBeep && mMediaPlayer != null) {
            mMediaPlayer.start();
        }
        if (mVibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_SCAN) {
            if (perms.size() != PERMISSIONS_SCAN.length) {
                finish();
            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_SCAN) {
            finish();
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}