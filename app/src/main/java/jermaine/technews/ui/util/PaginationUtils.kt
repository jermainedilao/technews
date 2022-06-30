package jermaine.technews.ui.util

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates

object PaginationUtils {
    /**
     * Called when paging adapter's loading state has changed.
     *
     * @param loadStates [LoadStates]
     * @param isListEmpty
     * @param isFirstLoad Set to true when it is first time to load the list.
     */
    fun onLoadStateChanged(
        loadStates: CombinedLoadStates,
        isListEmpty: Boolean,
        isFirstLoad: Boolean
    ): PaginationUtilStates {
        if (loadStates.append is LoadState.Error || loadStates.refresh is LoadState.Error) {
            return PaginationUtilStates.Error
        }

        return if (isListEmpty) {
            val shouldShowLoading = loadStates.refresh is LoadState.Loading
            val shouldShowEmptyView = loadStates.refresh is LoadState.NotLoading && !isFirstLoad

            when {
                shouldShowLoading -> {
                    PaginationUtilStates.ShowLoadingFirstLoad
                }
                shouldShowEmptyView -> {
                    PaginationUtilStates.HideLoadingShowEmptyView
                }
                else -> {
                    PaginationUtilStates.HideLoading
                }
            }
        } else {
            when {
                loadStates.refresh is LoadState.Loading -> {
                    PaginationUtilStates.ShowLoading
                }
                loadStates.append is LoadState.Loading -> {
                    PaginationUtilStates.ShowLoadingPagination
                }
                else -> {
                    PaginationUtilStates.HideAllLoadingAndEmptyView
                }
            }
        }
    }

    sealed class PaginationUtilStates {
        object ShowLoading : PaginationUtilStates()

        object ShowLoadingFirstLoad : PaginationUtilStates()

        object HideLoading : PaginationUtilStates()

        object HideLoadingShowEmptyView : PaginationUtilStates()

        object ShowLoadingPagination : PaginationUtilStates()

        object HideAllLoadingAndEmptyView : PaginationUtilStates()

        object Error : PaginationUtilStates()
    }
}
