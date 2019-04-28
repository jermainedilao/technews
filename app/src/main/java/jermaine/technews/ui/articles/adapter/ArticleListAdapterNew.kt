package jermaine.technews.ui.articles.adapter

import android.arch.paging.PagedListAdapter
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


class ArticleListAdapterNew : PagedListAdapter<ArticleViewObject, RecyclerView.ViewHolder>(ArticleViewObject.diffCallback) {
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

    private var paginateState = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_LOADER ->
                LoaderViewHolder(inflater.inflate(R.layout.loader, parent, false))

            VIEW_TYPE_ATTRIBUTION ->
                NewsApiAttributionViewHolder(
                    inflater.inflate(
                        R.layout.view_holder_attribution, parent, false
                    )
                )

            else ->
                ArticleViewHolder(
                    ViewHolderArticleBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_ARTICLE -> {
                val item = getItem(position)!!
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
            }
            VIEW_TYPE_ATTRIBUTION -> {
                val item = getItem(position)!!
                holder.itemView.setOnClickListener {
                    clickEvent.onNext(item)
                }
            }
        }
    }

    /**
     * Sets the article inside the list into loading state.
     *
     * @param position Position of the item inside the list.
     */
    fun setLoadingState(position: Int) {
        getItem(position)!!.containerAlpha = ArticleViewObject.LOADING_STATE
        notifyItemChanged(position)
    }

    /**
     * Sets the article inside the list into its default state.
     *
     * @param position Position of the item inside the list.
     */
    fun setDefaultState(position: Int) {
        getItem(position)!!.containerAlpha = ArticleViewObject.DEFAULT_STATE
        notifyItemChanged(position)
    }

    /**
     * Adds bookmark indicator to the article inside the list.
     *
     * @param position Position of the item inside the list.
     */
    fun bookmarkArticle(position: Int): Completable {
        getItem(position)!!.apply {
            bookmarked = true
            bookmarkDrawableResId = R.drawable.ic_bookmark_blue_24dp
            bookmarkButtonTextColorResId = R.color.blue
        }
        notifyItemChanged(position)
        return Completable.complete()
    }

    /**
     * Removes bookmark indicator from the article inside the list.
     *
     * @param position Position of the item inside the list.
     */
    fun removeBookmarkedArticle(position: Int): Completable {
        getItem(position)!!.apply {
            bookmarked = false
            bookmarkDrawableResId = R.drawable.ic_bookmark_border_gray_24dp
            bookmarkButtonTextColorResId = R.color.light_gray
        }
        notifyItemChanged(position)
        return Completable.complete()
    }

    fun setPaginateState(state: Boolean) {
        if (!state && this.paginateState) {
            // If previous paginate state is true and the new one passed is false.
            // We assume that loading next page is already done.
            this.paginateState = state
            notifyItemRemoved(super.getItemCount())
        } else {
            this.paginateState = state
            notifyItemInserted(super.getItemCount())
        }
    }

    override fun getItemCount(): Int {
        if (paginateState) {
            return super.getItemCount() + 1  // Add extra 1 count for the paginator.
        }
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        if (paginateState && position == itemCount - 1) {
            return VIEW_TYPE_LOADER
        }
        return getItem(position)!!.viewType
    }
}