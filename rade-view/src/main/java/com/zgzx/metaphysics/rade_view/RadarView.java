package com.zgzx.metaphysics.rade_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * 自定义雷达图
 */
public class RadarView extends View {

    private List<RadarData> dataList;

    private int count = 6; // 雷达网圈数
    private float angle; // 多边形弧度
    private float radius;
    private float maxValue = 100f;
    private Paint mainPaint; // 雷达区画笔
    private Paint valuePaint; // 数据区画笔
    private Paint textPaint; // 文本画笔
    private Paint mPointPaint; // 圆点画笔
    private Paint mLinePaint; // 线性画笔
    private TextPaint mCenterTextPaint;


    private float lineWidth; // 雷达网线宽度dp
    private float pointRadius; // 数据区圆点半径dp

    private int mWidth, mHeight;
    private float textHeight;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.radarViewStyle);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 属性值
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RadarView, defStyleAttr, R.style.radarViewStyle);
        int textColor = a.getColor(R.styleable.RadarView_textColor, 0);
        int primaryColor = a.getColor(R.styleable.RadarView_primaryColor, 0);
        int pointColor = a.getColor(R.styleable.RadarView_pointColor, 0);
        int textSize = a.getDimensionPixelOffset(R.styleable.RadarView_textSize, 0);
        pointRadius = a.getDimensionPixelSize(R.styleable.RadarView_point_radius, 0);
        lineWidth = a.getDimensionPixelSize(R.styleable.RadarView_line_width, 0);

        // 字体
        Typeface fontTypeface;
        String fontFamilyName = a.getString(R.styleable.RadarView_fontFamily);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fontTypeface = a.getFont(R.styleable.RadarView_fontFamily);
        } else {
            fontTypeface = Typeface.create(fontFamilyName, Typeface.NORMAL);
        }

        a.recycle();

        // 画笔
        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(primaryColor);
        mainPaint.setStyle(Paint.Style.STROKE);

        // 主要
        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(Color.parseColor("#B5B68D3A"));
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // 圆点
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(pointColor);
        mPointPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mainPaint.setAntiAlias(true);
        mLinePaint.setColor(pointColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        // 文字
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(fontTypeface);

        // 中间文字
        mCenterTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint.setColor(Color.WHITE);
        mCenterTextPaint.setTextSize(dip2px(getContext(), 16));
        mCenterTextPaint.setTextAlign(Paint.Align.CENTER);
        mCenterTextPaint.setTypeface(Typeface.create(fontTypeface, Typeface.BOLD));
        Paint.FontMetrics fontMetrics = mCenterTextPaint.getFontMetrics();
        textHeight = fontMetrics.descent - fontMetrics.ascent;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(h, w) / 2 * 0.7f;
        mWidth = w / 2;
        mHeight = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画面移到中间
        // canvas.translate(mWidth >> 1, mHeight >> 1);

        if (isDataListValid()) {

            drawSpiderweb(canvas);

            drawText(canvas);
            drawRegion(canvas);
            //drawCenterText(canvas);
        }
    }

//    // 中间显示文字
//    private void drawCenterText(Canvas canvas) {
//
//
//        // 文本
//        String str1 = getContext().getString(R.string._radar_today);
//        String str2 = getContext().getString(R.string._radar_luck);
//
//        // 绘制圆
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.parseColor("#B68D3A"));
//        paint.setStyle(Paint.Style.FILL);
//
//        // 画笔
//        Path path = new Path();
//        path.addCircle(0, 0, (int) (textHeight * 1.3), Path.Direction.CW);
//        canvas.drawPath(path, paint);
//
//        // 绘制文字
//        canvas.drawText(str1, 0, -(textHeight / 4), mCenterTextPaint);
//        canvas.drawText(str2, 0, textHeight - textHeight / 4, mCenterTextPaint);
//    }

    /**
     * 绘制蜘蛛网
     */
    private void drawSpiderweb(Canvas canvas) {
        mainPaint.setStrokeWidth(dip2px(getContext(), lineWidth));
        mPointPaint.setStrokeWidth(dip2px(getContext(), lineWidth));
        Path webPath = new Path();
        float r = radius / (count - 1); // 蜘蛛丝之间的间距
        for (int i = 5; i > 0; i--) {//中心点不用绘制
            float curR = r * i;//当前半径
            webPath.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    webPath.moveTo(mWidth + curR, mHeight);
                } else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x = (float) (mWidth + curR * Math.cos(angle * j));
                    float y = (float) (mHeight + curR * Math.sin(angle * j));
                    webPath.lineTo(x, y);
                }
            }
            webPath.close();//闭合路径
            if (i == 5) {//最外层
                drawPointReg(canvas, webPath, "#F3F0EB", "#FFFCF6");
            } else if (i == 4) {
                drawPointReg(canvas, webPath, "#F6F1E7", "#F6F1E7");
            } else if (i == 3) {
                drawPointReg(canvas, webPath, "#EEE7D9", "#EEE7D9");
            } else if (i == 2) {
                drawPointReg(canvas, webPath, "#E8E1D1", "#E8E1D1");
            } else if (i == 1) {//最里层
                drawLine(canvas);
//                mPointPaint.setColor(Color.parseColor("#ECC67C"));
//                mPointPaint.setStyle(Paint.Style.STROKE);
//                canvas.drawPath(webPath, mPointPaint);
//                LinearGradient linearGradient = new LinearGradient(
//                        0,0,1,1,Color.parseColor("#ECC67C"),Color.parseColor("#DFB25E"), Shader.TileMode.CLAMP
//                );
//                mPointPaint.setShader(linearGradient);
//               // mainPaint.setColor(Color.parseColor(s2));
//                // 绘制填充区域
//                mPointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//                canvas.drawPath(webPath, mPointPaint);
                drawPointReg(canvas, webPath, "#E4DCC9", "#E4DCC9");
            }
        }


    }
    /**
     * 绘制零每个图层区域
     */
    private void drawPointReg(Canvas canvas, Path webPath, String s, String s2) {
        mainPaint.setColor(Color.parseColor(s));
        mainPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(webPath, mainPaint);

        mainPaint.setColor(Color.parseColor(s2));
        // 绘制填充区域
        mainPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(webPath, mainPaint);
    }
    /**
     * 绘制零点到文字的图线
     */
    private void drawLine(Canvas canvas) {
        mLinePaint.setStrokeWidth(dip2px(getContext(), lineWidth));
        Path linePath = new Path();
        for (int m = 0; m < count; m++) {
            linePath.reset();
            linePath.moveTo(mWidth, mHeight);
            float x = (float) (mWidth + radius * Math.cos(angle * m));
            float y = (float) (mHeight + radius * Math.sin(angle * m));
            linePath.lineTo(x, y);
            LinearGradient linearGradient = new LinearGradient(
                    0,0,1,1,Color.parseColor("#00000000"),Color.parseColor("#EBDFC6"), Shader.TileMode.CLAMP
            );
            mLinePaint.setShader(linearGradient);
            //mLinePaint.setColor(Color.parseColor("#EBDFC6"));
            canvas.drawPath(linePath, mLinePaint);
        }
    }

    /**
     * 绘制文本
     */
    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;

        for (int i = 0; i < count; i++) {
            float x = (float) (mWidth + (radius + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (mHeight + (radius + fontHeight / 2) * Math.sin(angle * i));

            // 文本长度
            String title = dataList.get(i).getTitle();
            String number = dataList.get(i).getNumber();
            if (angle * i >= 0 && angle * i <= Math.PI / 2) {//第4象限

                if (i==1)
                {
                    canvas.drawText(number, x+fontHeight/2, y, textPaint);
                    canvas.drawText(title, x, y+fontHeight, textPaint);
                }else {
                    canvas.drawText(number, x+fontHeight/2, y-fontHeight, textPaint);
                    canvas.drawText(title, x, y, textPaint);
                }
            } else if (angle * i >= 3 * Math.PI / 2 && angle * i <= Math.PI * 2) {//第3象限
                    canvas.drawText(number,x+fontHeight/2, y-fontHeight, textPaint);
                    canvas.drawText(title, x, y, textPaint);

            } else if (angle * i > Math.PI / 2 && angle * i <= Math.PI) {//第2象限
                float dis = textPaint.measureText(title);//文本长度

                if (i==2){
                    canvas.drawText(number, x- dis+fontHeight/2, y, textPaint);
                    canvas.drawText(title, x - dis, y+fontHeight, textPaint);
                }else {
                    canvas.drawText(number, x- dis+fontHeight/2, y-fontHeight, textPaint);
                    canvas.drawText(title, x - dis, y, textPaint);
                }
            } else if (angle * i >= Math.PI && angle * i < 3 * Math.PI / 2) {//第1象限
                float dis = textPaint.measureText(title);//文本长度
                canvas.drawText(number, x- dis+fontHeight/2 , y-fontHeight, textPaint);
                canvas.drawText(title, x - dis, y, textPaint);
            }
        }
    }

    /**
     * 绘制区域
     */
    private void drawRegion(Canvas canvas) {
        valuePaint.setStrokeWidth(dip2px(getContext(), lineWidth));

        Path path = new Path();
        valuePaint.setAlpha(255);
        path.reset();

        for (int i = 0; i < count; i++) {
            double percent = dataList.get(i).getPercentage() / maxValue;
            float x = (float) (mWidth+radius*Math.cos(angle*i)*percent);
            float y = (float) (mHeight+radius*Math.sin(angle*i)*percent);

            if (i == 0) {
                path.moveTo(x, y);

            } else {
                path.lineTo(x, y);
            }
        }

        path.close();
        valuePaint.setAlpha(150);

        // 绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        LinearGradient linearGradient = new LinearGradient(
                0,0,1,1,Color.parseColor("#ECC67C"),Color.parseColor("#DFB25E"), Shader.TileMode.CLAMP
        );
        valuePaint.setShader(linearGradient);
        canvas.drawPath(path, valuePaint);
    }


    private boolean isDataListValid() {
        return dataList != null && dataList.size() >= 3;
    }

    public void setDataList(List<RadarData> dataList) {
        if (dataList == null || dataList.size() < 3) {
            throw new RuntimeException("The number of data can not be less than 3");
        } else {
            this.dataList = dataList;
            count = dataList.size(); // 圈数等于数据个数，默认为6
            angle = (float) (Math.PI * 2 / count);
            invalidate();
        }
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
