package com.zgzx.metaphysics.utils.image;

import androidx.annotation.NonNull;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.zgzx.metaphysics.R;

import static com.bumptech.glide.request.RequestOptions.decodeTypeOf;

@GlideExtension
public class GlideAppExtension {

    // 指定图片最小尺寸
    private static final int MINI_THUMB_SIZE = 250;

    private static final RequestOptions DECODE_TYPE_GIF = decodeTypeOf(GifDrawable.class).lock();

    private GlideAppExtension() {
    } // utility class

    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> normal(BaseRequestOptions<?> options) {
        return options
                .dontAnimate()
                .placeholder(R.drawable.ic_placeholder_image)
                .error(R.drawable.ic_placeholder_image);
    }

    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> miniThumb(BaseRequestOptions<?> options) {
        return normal(options)
                .fitCenter()
                //.transform(new CenterCrop(), new RoundedCorners(ConvertUtils.dp2px(8)))
                .override(MINI_THUMB_SIZE);
    }


    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> avatar(BaseRequestOptions<?> options) {
        return miniThumb(options)
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_default_avatar)
                .circleCrop();
    }

    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> squareAvatar(BaseRequestOptions<?> options) {
        return miniThumb(options)
                .placeholder(R.drawable.ic_default_square_avatar)
                .error(R.drawable.ic_default_square_avatar);
    }

//    @NonNull
//    @GlideType(GifDrawable.class)
//    public static RequestBuilder<GifDrawable> asGif(RequestBuilder<GifDrawable> requestBuilder) {
//        return requestBuilder
//                .transition(new DrawableTransitionOptions())
//                .apply(DECODE_TYPE_GIF);
//    }

}
