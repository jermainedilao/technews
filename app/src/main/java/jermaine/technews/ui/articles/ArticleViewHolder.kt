package jermaine.technews.ui.articles

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import jermaine.technews.ui.util.GlideApp
import kotlinx.android.synthetic.main.view_holder_article.view.*

class ArticleViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    fun setImage(url: String) {
        GlideApp.with(context)
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.image_view)
    }

    fun setTitle(title: String) {
        itemView.title.text = title
    }

    fun setDescription(description: String) {
        itemView.description.text = description
    }

    fun setSource(source: String) {
        val sourceText = "source:$source"
        itemView.source.text = sourceText
    }
}