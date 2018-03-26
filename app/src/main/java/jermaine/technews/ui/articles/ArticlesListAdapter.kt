package jermaine.technews.ui.articles

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import jermaine.technews.R
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.util.callbacks.OnLastItemCallback


class ArticlesListAdapter(
        private var context: Context,
        private var articles: MutableList<ArticleViewObject>,
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
    val clickEvent: PublishSubject<ArticleViewObject> = PublishSubject.create()

    /**
     * Emits pair of position and ArticleViewObject of the item being bookmarked.
     **/
    val bookmarkEvent: PublishSubject<Pair<Int, ArticleViewObject>> = PublishSubject.create()

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
            holder.setBookmarkListener(View.OnClickListener {
                bookmarkEvent.onNext(Pair(position, item))
            })

            holder.setBookmarkIcon(item.bookmarkDrawableResId)
            holder.setBookMarkButtonText(item.bookmarkButtonTextResId)
            holder.setContainerAlpha(item.containerAlpha)

            if (position == articles.size - 1) {
                onLastItemCallback.onLastItem()
            }
        }
    }

    override fun getItemCount(): Int = articles.size

    /**
     * Replaces the entire list displayed in the list view.
     **/
    fun newList(articles: List<ArticleViewObject>) {
        this.articles = arrayListOf()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    /**
     * Appends the new list at the end of the currently displayed list.
     **/
    fun append(articles: List<ArticleViewObject>) {
        val startPosition = this.articles.size

        this.articles.addAll(articles)
        notifyItemRangeInserted(startPosition, articles.size)
    }

    /**
     * Appends the new article at the end of the currently displayed list.
     **/
    fun append(article: ArticleViewObject) {
        this.articles.add(article)
        this.notifyItemInserted(this.articles.size - 1)
    }

    /**
     * Shows pagination indicator at the end of the list.
     **/
    fun showPaginateIndicator() {
        // safe to put 0 value to bookmarkDrawableResId since we only display LOADER
        val loader = ArticleViewObject(title = ArticlesListAdapter.LOADER, bookmarkDrawableResId = 0)
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

    /**
     * Adds bookmark indicator to the article inside the list.
     *
     * @param position Position of the item inside the list.
     * @param item The item itself.
     **/
    fun bookmarkArticle(position: Int, item: ArticleViewObject): Completable {
        item.bookmarked = true
        item.bookmarkDrawableResId = R.drawable.ic_bookmark_red_24dp
        item.bookmarkButtonTextResId = R.string.remove_text
        articles[position] = item
        notifyItemChanged(position)

        return Completable.complete()
    }

    /**
     * Removes bookmark indicator from the article inside the list.
     *
     * @param position Position of the item inside the list.
     * @param item The item itself.
     **/
    fun removeBookmarkedArticle(position: Int, item: ArticleViewObject): Completable {
        item.bookmarked = false
        item.bookmarkDrawableResId = R.drawable.ic_bookmark_border_red_24dp
        item.bookmarkButtonTextResId = R.string.add_text
        articles[position] = item
        notifyItemChanged(position)

        return Completable.complete()
    }

    /**
     * Sets the article inside the list into loading state.
     *
     * @param position Position of the item inside the list.
     * @param item The item itself.
     **/
    fun setLoadingState(position: Int, item: ArticleViewObject) {
        item.containerAlpha = ArticleViewObject.LOADING_STATE
        articles[position] = item
        notifyItemChanged(position)
    }

    /**
     * Sets the article inside the list into its default state.
     *
     * @param position Position of the item inside the list.
     * @param item The item itself.
     **/
    fun setDefaultState(position: Int, item: ArticleViewObject) {
        item.containerAlpha = ArticleViewObject.DEFAULT_STATE
        articles[position] = item
        notifyItemChanged(position)
    }
}