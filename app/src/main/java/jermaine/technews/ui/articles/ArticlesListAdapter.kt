package jermaine.technews.ui.articles

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import jermaine.domain.articles.model.Article
import jermaine.technews.R


class ArticlesListAdapter(private var articles: List<Article>) : RecyclerView.Adapter<ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder?, position: Int) {
        val item = articles[position]

        holder?.setTitle(item.title)
        holder?.setDescription(item.description)
    }
}