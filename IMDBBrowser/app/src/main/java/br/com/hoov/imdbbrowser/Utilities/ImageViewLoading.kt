package br.com.hoov.imdbbrowser.Utilities

import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v4.widget.CircularProgressDrawable
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.transition.ViewPropertyTransition
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlin.math.roundToInt

////////////////////// Setup required to use GlideApp //////////////////////
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class AppGlideModule : AppGlideModule()
////////////////////////////////////////////////////////////////////////////

val ImageView.defaultLoader: Drawable get() {
    fun toPx(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
    val result = CircularProgressDrawable(context)
    result.strokeWidth = toPx(4f)
    result.centerRadius = toPx(16f)
    result.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
    result.start()
    return result
}

fun ImageView.loadImage(url: String?, placeholderImage: Drawable? = defaultLoader, fallbackImage: Drawable? = ColorDrawable(Color.LTGRAY), cornerRadius: Float = 0f, imageTransform: Transformation<Bitmap> = CenterCrop(), diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC) {
    loadImageFromURLOrURI(url, placeholderImage, fallbackImage, cornerRadius, cornerRadius, cornerRadius, cornerRadius, imageTransform, diskCacheStrategy)
}

private fun ImageView.loadImageFromURLOrURI(url: String? = null, placeholderImage: Drawable? = defaultLoader, fallbackImage: Drawable? = ColorDrawable(Color.LTGRAY), topLeftCornerRadius: Float = 0f, topRightCornerRadius: Float = 0f, bottomRightCornerRadius: Float = 0f, bottomLeftCornerRadius: Float = 0f, imageTransform: Transformation<Bitmap> = CenterCrop(), diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.AUTOMATIC) {
    val animator = ViewPropertyTransition.Animator { view ->
        view.alpha = 0f
        val fadeInAnimation = ObjectAnimator.ofFloat(view, "alpha", 1f)
        fadeInAnimation.duration = 150
        fadeInAnimation.start()
    }
    GlideApp.with(context).load(url).placeholder(placeholderImage).error(fallbackImage).fallback(fallbackImage)
        .diskCacheStrategy(diskCacheStrategy)
        .transition(GenericTransitionOptions.with(animator))
        .transforms(
            imageTransform,
            RoundedCornersTransformation(topLeftCornerRadius.roundToInt(), 0, RoundedCornersTransformation.CornerType.TOP_LEFT),
            RoundedCornersTransformation(topRightCornerRadius.roundToInt(), 0, RoundedCornersTransformation.CornerType.TOP_RIGHT),
            RoundedCornersTransformation(bottomRightCornerRadius.roundToInt(), 0, RoundedCornersTransformation.CornerType.BOTTOM_RIGHT),
            RoundedCornersTransformation(bottomLeftCornerRadius.roundToInt(), 0, RoundedCornersTransformation.CornerType.BOTTOM_LEFT)
        ).into(this)
}
