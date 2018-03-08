package jermaine.technews.ui.articles

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import jermaine.technews.ui.util.GlideApp
import kotlinx.android.synthetic.main.view_holder_article.view.*

class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun setImage(activity: Activity, url: String) {
        GlideApp.with(activity)
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
}