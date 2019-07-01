package jermaine.technews.ui.util

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import jermaine.technews.ui.articles.model.UIState


@BindingAdapter("app:uiState")
fun uiState(view: View, uiState: UIState) {
    view.visibility = when (uiState) {
        UIState.HasData -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("app:uiState")
fun uiState(view: SwipeRefreshLayout, uiState: UIState) {
    view.isRefreshing = when (uiState) {
        UIState.Loading -> true
        else -> false
    }
}