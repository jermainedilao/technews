package jermaine.technews.ui.articles

import android.view.View
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import jermaine.technews.R
import jermaine.technews.ui.utils.Capture.Companion.getText
import org.hamcrest.Matcher

class ArticleListScreen : Screen<ArticleListScreen>() {
    private val recycler = KRecyclerView(
        builder = { withId(R.id.recyclerView) },
        itemTypeBuilder = {
            itemType(::ArticleItem)
        }
    )

    private val bookmarkMenu = KButton { withId(R.id.bookmarks) }

    fun assertListSize(size: Int) {
        assertListVisibility() perform {
            hasSize(size)
        }
    }

    fun bookMarkItem(position: Int = 0, callback: (title: String) -> Unit = {}) {
        assertListVisibility() perform {
            childAt<ArticleItem>(position) {
                isVisible()
                btnBookmark.click()
                callback(getText(title))
            }
        }
    }

    fun validateBookmarkedItem(position: Int = 0) {
        assertListVisibility() perform {
            childAt<ArticleItem>(position) {
                isVisible()
                btnBookmark {
                    isVisible()
                    hasTextColor(R.color.blue)
                }
            }
        }
    }

    fun validateUnBookmarkedItemByTitle(title: String) {
        findChildByTitle(title) {
            btnBookmark {
                isVisible()
                hasTextColor(R.color.light_gray)
            }
        }
    }

    fun navigateToBookmarksScreen() {
        bookmarkMenu {
            click()
        }
    }

    private fun findChildByTitle(title: String, func: ArticleItem.() -> Unit) {
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

    class ArticleItem(parent: Matcher<View>) : KRecyclerItem<ArticleItem>(parent) {
        val title = KTextView(parent) {
            withId(R.id.title)
        }
        val btnBookmark = KButton(parent) {
            withId(R.id.bookmark)
        }
    }
}