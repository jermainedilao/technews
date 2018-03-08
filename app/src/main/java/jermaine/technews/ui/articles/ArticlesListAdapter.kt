package jermaine.technews.ui.articles

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import jermaine.domain.articles.model.Article
import jermaine.technews.R


class ArticlesListAdapter(private val activity: Activity, private var articles: List<Article>) : RecyclerView.Adapter<ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.view_holder_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = articles[position]

        holder.setImage(activity, item.urlToImage)
        holder.setTitle(item.title)
        holder.setDescription(item.description)
    }

    override fun getItemCount(): Int = articles.size
}