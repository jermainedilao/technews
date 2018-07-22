package jermaine.technews.ui.articles.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import jermaine.technews.R
import jermaine.technews.databinding.ViewHolderArticleBinding
import jermaine.technews.ui.articles.ArticleViewHolder
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.ui.widgets.listwidgets.LoaderViewHolder
import jermaine.technews.ui.widgets.listwidgets.NewsApiAttributionViewHolder
import jermaine.technews.util.callbacks.OnLastItemCallback


class ArticlesListAdapter(
        private var articles: MutableList<ArticleViewObject>,
        private val onLastItemCallback: OnLastItemCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_ARTICLE = 0
        const val VIEW_TYPE_LOADER = 1
        const val VIEW_TYPE_ATTRIBUTION = 2
    }

    /**
     * Emits the item being clicked from the list view.
     **/
    val clickEvent: PublishSubject<ArticleViewObject> = PublishSubject.create()

    /**
     * Emits pair of position and ArticleViewObject of the item being bookmarked.
     **/
    val bookmarkEvent: PublishSubject<Pair<Int, ArticleViewObject>> = PublishSubject.create()

    override fun getItemViewType(position: Int): Int =
            articles[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_LOADER ->
                LoaderViewHolder(inflater.inflate(R.layout.loader, parent, false))
            VIEW_TYPE_ATTRIBUTION ->
                NewsApiAttributionViewHolder(inflater.inflate(
                        R.layout.view_holder_attribution, parent, false
                ))
            else ->
                ArticleViewHolder(ViewHolderArticleBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                ))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = articles[position]

        when (getItemViewType(position)) {
            VIEW_TYPE_ARTICLE -> {
                (holder as ArticleViewHolder).apply {
                    bind(
                            item,
                            onArticleClick = View.OnClickListener {
                                clickEvent.onNext(item)
                            },
                            onBookmarkClick = View.OnClickListener {
                                bookmarkEvent.onNext(Pair(position, item))
                            }
                    )
                }

                if (position == articles.size - 1) {
                    onLastItemCallback.onLastItem()
                }
            }
            VIEW_TYPE_ATTRIBUTION -> {
                holder.itemView.setOnClickListener {
                    clickEvent.onNext(item)
                }
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
        val loader = ArticleViewObject(viewType = VIEW_TYPE_LOADER)
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
        item.bookmarkDrawableResId = R.drawable.ic_bookmark_blue_24dp
        item.bookmarkButtonTextColorResId = R.color.blue
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
        item.bookmarkDrawableResId = R.drawable.ic_bookmark_border_gray_24dp
        item.bookmarkButtonTextColorResId = R.color.light_gray
        articles[position] = item
        notifyItemChanged(position)

        return Completable.complete()
    }

    /**
     * Removes item from the list.
     *
     * @param position Position of the item inside the list.
     * @param item The item itself.
     **/
    fun remove(position: Int): Completable {
        articles.removeAt(position)
        notifyItemRemoved(position)

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

    /**
     * Returns the currently displayed list.
     **/
    fun getItems(): List<ArticleViewObject> = articles
}