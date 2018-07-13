package jermaine.technews.ui.articles

import android.content.Context
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import jermaine.technews.ui.util.GlideApp
import kotlinx.android.synthetic.main.view_holder_article.view.*

class ArticleViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    fun setImage(url: String?) {
        url?.let {
            GlideApp.with(context)
                    .load(url)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.image_view)
        }
    }

    fun setTitle(title: String?) {
        title?.let {
            itemView.title.text = title
        }
    }

    fun setDescription(description: String?) {
        description?.let{
            itemView.description.text = description
        }
        itemView.description.paintFlags = Paint.ANTI_ALIAS_FLAG
    }

    fun setSource(source: String?) {
        source?.let{
            val sourceText = "$source"
            itemView.source.text = sourceText
        }
    }

    fun setPublishedAt(publishedAt: String) {
        itemView.published_at.text = publishedAt
    }

    fun setBookmarkListener(listener: View.OnClickListener) {
        itemView.bookmark.setOnClickListener(listener)
    }

    fun setBookmarkIcon(drawableResId: Int) {
        itemView.bookmark.setCompoundDrawablesWithIntrinsicBounds(
                drawableResId,
                0,
                0,
                0
        )
    }

    fun setBookMarkButtonText(textResId: Int) {
        itemView.bookmark.text = context.getText(textResId)
        itemView.bookmark.paintFlags = Paint.ANTI_ALIAS_FLAG
    }

    fun setBookmarkButtonTextColor(colorResId: Int) {
        itemView.bookmark.setTextColor(ContextCompat.getColor(context, colorResId))
    }

    fun setContainerAlpha(alpha: Float) {
        itemView.cardview.alpha = alpha
    }
}