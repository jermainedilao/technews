package jermaine.technews.ui.articles

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View
import jermaine.technews.BR

// https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
class ArticleViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Any, onArticleClick: View.OnClickListener, onBookmarkClick: View.OnClickListener) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.articleClick, onArticleClick)
        binding.setVariable(BR.bookmarkClick, onBookmarkClick)
        binding.executePendingBindings()
    }
}