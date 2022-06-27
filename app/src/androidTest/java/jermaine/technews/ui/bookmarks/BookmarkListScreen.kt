package jermaine.technews.ui.bookmarks

import android.view.View
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import jermaine.technews.R
import org.hamcrest.Matcher

class BookmarkListScreen : Screen<BookmarkListScreen>() {
    val recycler = KRecyclerView(
        builder = { withId(R.id.recyclerView) },
        itemTypeBuilder = {
            itemType(::BookmarkItem)
        }
    )

    fun assertItemIsVisibleByTitle(title: String) {
        findChildByTitle(title) {
            txtTitle.isDisplayed()
        }
    }

    fun unBookmarkItemByTitle(title: String) {
        findChildByTitle(title) {
            btnBookmark {
                isVisible()
                click()
            }
        }
    }

    private fun findChildByTitle(title: String, func: BookmarkItem.() -> Unit) {
        assertListVisibility() perform {
            func(
                childWith {
                    withDescendant { withText(title) }
                }
            )
        }
    }

    private fun assertListVisibility(): KRecyclerView {
        return recycler.apply {
            isVisible()
        }
    }

    private class BookmarkItem(parent: Matcher<View>) : KRecyclerItem<BookmarkItem>(parent) {
        val txtTitle = KTextView(parent) {
            withId(R.id.title)
        }
        val btnBookmark = KButton(parent) {
            withId(R.id.bookmark)
        }
    }
}