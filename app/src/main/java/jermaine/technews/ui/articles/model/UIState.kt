package jermaine.technews.ui.articles.model

import androidx.annotation.StringRes


sealed class UIState {
    object Loading : UIState()

    object HasData : UIState()

    object NoData : UIState()

    class Error(@StringRes val errorMessageId: Int) : UIState()
}