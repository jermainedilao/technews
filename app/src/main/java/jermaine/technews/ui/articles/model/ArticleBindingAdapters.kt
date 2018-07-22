package jermaine.technews.ui.articles.model

import android.databinding.BindingAdapter
import android.support.v4.content.ContextCompat
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import jermaine.technews.ui.util.GlideApp


@BindingAdapter("imageUrl", requireAll = false)
fun loadImage(view: ImageView, url: String?) {
    if (url == null || url.isEmpty()) return

    GlideApp.with(view.context)
            .load(url)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
}

@BindingAdapter("android:drawableStart", requireAll = false)
fun setBookmarkIcon(view: Button, drawableResId: Int) {
    view.setCompoundDrawablesWithIntrinsicBounds(
            drawableResId,
            0,
            0,
            0
    )
}

@BindingAdapter("android:textColor")
fun setTextColor(view: Button, colorResId: Int) {
    view.setTextColor(ContextCompat.getColor(view.context, colorResId))
}