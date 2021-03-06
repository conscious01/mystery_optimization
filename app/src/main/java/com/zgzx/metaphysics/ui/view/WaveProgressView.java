package com.zgzx.metaphysics.ui.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.zgzx.metaphysics.R;

public class WaveProgressView extends View {
    private int width;
    private int height;

    private Bitmap backgroundBitmap;

    private Path mPath;
    private Paint mPathPaint;

    private float mWaveHight = 30f;
    private float mWaveWidth = 100f;
    private String mWaveColor = "#FFFFFF";
    private  int  mWaveSpeed = 30;

    private Paint mTextPaint;
    private String currentText = "";
    private String mTextColor = "#FFFFFF";
    private int mTextSize = 80;

    private int maxProgress = 100;
    private int currentProgress = 0;
    private float currentY;

    private float distance = 0;
    private int RefreshGap = 10;

    private static final int INVALIDATE = 0X777;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INVALIDATE:
                    invalidate();
                    sendEmptyMessageDelayed(INVALIDATE,RefreshGap);
                    break;
            }
        }
    };

    public WaveProgressView(Context context) {
        this(context,null,0);
    }
    public WaveProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public WaveProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setCurrent(int currentProgress, String currentText) {
        this.currentProgress = currentProgress;
        this.currentText = currentText;
    }
    public void setWaveColor(String mWaveColor){
        this.mWaveColor = mWaveColor;
    }

    private void init() {

        if(null==getBackground()){
            throw new IllegalArgumentException(String.format("background is null."));
        }else{
            backgroundBitmap = getBitmapFromDrawable(getBackground());
        }

        mPath = new Path();
        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.FILL);
        AssetManager mgr = getContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(mgr, "din_bold.otf");
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setTypeface(typeface);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        handler.sendEmptyMessageDelayed(INVALIDATE,100);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        currentY = height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(backgroundBitmap!=null){

            canvas.drawBitmap(createImage(), 0, 0, null);
        }
        Bitmap bmp=Bitmap.createScaledBitmap(getBitmapFromDrawable(getResources().getDrawable(R.drawable.icon_dragon_bg_1)),width,height,false);
        canvas.drawBitmap(bmp, 0, 0, null);
    }
    private Bitmap createImage()
    {
      //  mPathPaint.setColor(Color.parseColor(mWaveColor));
        LinearGradient linearGradient = new LinearGradient(width/2, height, width/2, 0, new int[]{Color.parseColor("#FFFF7F2A"), Color.parseColor("#FFFFDD88")}, null, Shader.TileMode.CLAMP);

        mPathPaint.setShader(linearGradient);
        mTextPaint.setColor(Color.parseColor(mTextColor));
        mTextPaint.setTextSize(mTextSize);

        mPathPaint.setColor(Color.parseColor(mWaveColor));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap bmp = Bitmap.createBitmap(backgroundBitmap.getWidth(),backgroundBitmap.getHeight() , Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmp);

        float currentMidY = height*(maxProgress-currentProgress)/maxProgress;
        if(currentY>currentMidY){
            currentY = currentY - (currentY-currentMidY)/10;
        }
        mPath.reset();
        //之所以0-distance是因为有原点向上增加的
        mPath.moveTo(0-distance,currentY);
        //显示的区域内的水波纹的数量
        int waveNum = width/((int)mWaveWidth);
        int num = 0;
        for(int i =0;i<waveNum;i++){
            mPath.quadTo(mWaveWidth*(num+1)-distance,currentY-mWaveHight,mWaveWidth*(num+2)-distance,currentY);
            mPath.quadTo(mWaveWidth*(num+3)-distance,currentY+mWaveHight,mWaveWidth*(num+4)-distance,currentY);
            num+=4;
        }
        distance +=mWaveWidth/mWaveSpeed;
        distance = distance%(mWaveWidth*4);
        mPath.lineTo(width,height);
        mPath.lineTo(0,height);
        mPath.close();
        canvas.drawPath(mPath, mPathPaint);
        int min = Math.min(width,height);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.icon_dragon_bg,options);
        options.inSampleSize=1;
        //获取图片的宽高
        int destheight = options.outHeight;
        int destwidth = options.outWidth;
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap,destwidth,destheight,false);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));


       // canvas.drawBitmap(backgroundBitmap,0,0,paint);

        canvas.drawText(currentText, width/2, height/2+140, mTextPaint);
        return bmp;
    }


    public static int dip2px(float dip, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + 0.5f);// 4.9->4, 4.1->4, 四舍五入
        return px;
    }
    /**
     * px转dp
     * @param px        px
     * @param context   上下文
     * @return
     */
    public static float px2dip(int px, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        float dp = px / density;
        return dp;
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(),canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }
}
