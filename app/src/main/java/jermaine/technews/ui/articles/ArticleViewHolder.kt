package jermaine.technews.ui.articles

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import jermaine.technews.BR

// https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
class ArticleViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: Any,
        onArticleClick: View.OnClickListener,
        onBookmarkClick: View.OnClickListener,
        onSourceClick: View.OnClickListener
    ) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.articleClick, onArticleClick)
        binding.setVariable(BR.bookmarkClick, onBookmarkClick)
        binding.setVariable(BR.sourceClick, onSourceClick)
        binding.executePendingBindings()
    }
}