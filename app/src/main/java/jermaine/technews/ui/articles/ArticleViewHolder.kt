package jermaine.technews.ui.articles

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.view_holder_article.view.*

class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun setTitle(title: String) {
        itemView.title.text = title
    }

    fun setDescription(description: String) {
        itemView.description.text = description
    }
}