package jermaine.technews.ui.articles

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import jermaine.technews.ui.BaseTestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ArticlesListActivityTest : BaseTestCase() {

    @get:Rule
    val rule = ActivityScenarioRule(ArticlesListActivity::class.java)

    @Test
    fun listShouldBeVisible() {
        run {
            onScreen<ArticleListScreen> {
                flakySafely(20_000) {
                    assertListIsVisible()
                }
            }
        }
    }

    @Test
    fun testBookmarkButtonInList() {
        run {
            onScreen<ArticleListScreen> {
                flakySafely(20_000) {
                    assertListIsVisible()
                    bookMarkItem()
                    validateBookmarkedItem()
                    bookMarkItem() // Un-bookmark item
                }
            }
        }
    }
}