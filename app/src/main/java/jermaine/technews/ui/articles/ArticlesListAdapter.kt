package jermaine.technews.ui.articles

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.subjects.PublishSubject
import jermaine.domain.articles.model.Article
import jermaine.technews.R


class ArticlesListAdapter(private var articles: List<Article>) : RecyclerView.Adapter<ArticleViewHolder>() {
    val clickEvent: PublishSubject<Article> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_article, parent, false)
        return ArticleViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = articles[position]

        holder.setImage(item.urlToImage)
        holder.setTitle(item.title)
        holder.setDescription(item.description)
        holder.setSource(item.source.name)
        holder.itemView.setOnClickListener {
            clickEvent.onNext(item)
        }
    }

    override fun getItemCount(): Int = articles.size
}