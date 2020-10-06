package com.zgzx.metaphysics.utils.image;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GifLoadOneTimeGif {
    /**
     * Gif 加载 可以设置次数，监听播放完成回调
     * @param context  上下文对象
     * @param model 传入的gif地址，可以是网络，也可以是本地，（https://raw.githubusercontent.com/Jay-YaoJie/KotlinDialogs/master/diagram/test.gif）
     * @param imageView 要显示的imageView
     * @param loopCount 播放次数
     * @param gifListener  Gif播放完毕回调
     */
  public static void loadOneTimeGif(Context context, int model, final ImageView imageView , int loopCount, final GifListener gifListener){
      Glide.with(context).asGif().load(model).listener(new RequestListener<GifDrawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
              return false;
          }

          @Override
          public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
              try {
                  Field gifStateField = GifDrawable.class.getDeclaredField("state");
                  gifStateField.setAccessible(true);
                  Class gifStateClass = Class.forName("com.bumptech.glide.load.resource.gif.GifDrawable$GifState");
                  Field gifFrameLoaderField = gifStateClass.getDeclaredField("frameLoader");
                  gifFrameLoaderField.setAccessible(true);
                  Class gifFrameLoaderClass = Class.forName("com.bumptech.glide.load.resource.gif.GifFrameLoader");
                  Field gifDecoderField = gifFrameLoaderClass.getDeclaredField("gifDecoder");
                  gifDecoderField.setAccessible(true);
                  Class gifDecoderClass = Class.forName("com.bumptech.glide.gifdecoder.GifDecoder");
                  Object gifDecoder = gifDecoderField.get(gifFrameLoaderField.get(gifStateField.get(resource)));
                  Method getDelayMethod = gifDecoderClass.getDeclaredMethod("getDelay", int.class);
                  getDelayMethod.setAccessible(true);
                  ////设置播放次数
                  resource.setLoopCount(loopCount);
                  //获得总帧数
                  int count = resource.getFrameCount();
                  int delay = 0;
                  for (int i = 0; i < count; i++) {
                      //计算每一帧所需要的时间进行累加
                      delay += (int) getDelayMethod.invoke(gifDecoder, i);
                  }
                  imageView.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          if (gifListener != null) {
                              gifListener.gifPlayComplete();
                          }
                      }
                  }, delay);
              } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                  e.printStackTrace();
              }
              return false;
          }

      }).into(imageView);
  }
    /**
     * Gif播放完毕回调
     */
    public interface GifListener {
        void gifPlayComplete();
    }
}
