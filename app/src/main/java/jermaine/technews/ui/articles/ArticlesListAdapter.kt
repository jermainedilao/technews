package jermaine.technews.ui.articles

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.subjects.PublishSubject
import jermaine.domain.articles.model.Article
import jermaine.technews.R
import jermaine.technews.util.callbacks.OnLastItemCallback


class ArticlesListAdapter(
        private var articles: MutableList<Article>,
        private val onLastItemCallback: OnLastItemCallback
) : RecyclerView.Adapter<ArticleViewHolder>() {
    companion object {
        const val LOADER = "LOADER"

        const val VIEW_TYPE_ARTICLE = 0
        const val VIEW_TYPE_LOADER = 1
    }

    /**
     * Emits the item being clicked from the list view.
     **/
    val clickEvent: PublishSubject<Article> = PublishSubject.create()

    override fun getItemViewType(position: Int): Int {
        return if (articles[position].title.contentEquals(LOADER)) {
            VIEW_TYPE_LOADER
        } else {
            VIEW_TYPE_ARTICLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = if (viewType == VIEW_TYPE_LOADER) {
            LayoutInflater.from(parent.context).inflate(R.layout.loader, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_article, parent, false)
        }

        return ArticleViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_ARTICLE) {
            val item = articles[position]

            holder.setImage(item.urlToImage)
            holder.setTitle(item.title)
            holder.setDescription(item.description)
            holder.setSource(item.source.name)
            holder.itemView.setOnClickListener {
                clickEvent.onNext(item)
            }

            if (position == articles.size - 1) {
                onLastItemCallback.onLastItem()
            }
        }
    }

    override fun getItemCount(): Int = articles.size

    /**
     * Replaces the entire list displayed in the list view.
     **/
    fun newList(articles: List<Article>) {
        this.articles = arrayListOf()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    /**
     * Appends the new list at the end of the currently displayed list.
     **/
    fun append(articles: List<Article>) {
        val startPosition = this.articles.size

        this.articles.addAll(articles)
        notifyItemRangeInserted(startPosition, articles.size)
    }

    /**
     * Appends the new article at the end of the currently displayed list.
     **/
    fun append(article: Article) {
        this.articles.add(article)
        this.notifyItemInserted(this.articles.size - 1)
    }

    /**
     * Shows pagination indicator at the end of the list.
     **/
    fun showPaginateIndicator() {
        val loader = Article(title = ArticlesListAdapter.LOADER)
        append(loader)
    }

    /**
     * Hides the pagination indicator at the end of the list.
     **/
    fun hidePaginateIndicator() {
        val lastItemPos = articles.size - 1
        if (getItemViewType(lastItemPos) == VIEW_TYPE_LOADER) {
            articles.removeAt(lastItemPos)
        }
        notifyItemRemoved(lastItemPos)
    }
}