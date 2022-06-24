package jermaine.technews.ui.articles

import android.view.View
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import jermaine.technews.R
import org.hamcrest.Matcher

class ArticleListScreen : Screen<ArticleListScreen>() {
    val recycler = KRecyclerView(
        builder = { withId(R.id.recyclerView) },
        itemTypeBuilder = {
            itemType(::ArticleItem)
        }
    )

    fun assertListIsVisible() {
        recycler {
            isVisible()
            hasSize(10)
        }
    }

    fun bookMarkItem(position: Int = 0) {
        recycler {
            childAt<ArticleItem>(position) {
                isVisible()
                btnBookmark.click()
            }
        }
    }

    fun validateBookmarkedItem(position: Int = 0) {
        recycler {
            childAt<ArticleItem>(position) {
                isVisible()
                btnBookmark {
                    isVisible()
                    hasTextColor(R.color.blue)
                }
            }
        }
    }

    class ArticleItem(parent: Matcher<View>) : KRecyclerItem<ArticleItem>(parent) {
        val btnBookmark = KButton(parent) {
            withId(R.id.bookmark)
        }
    }
}