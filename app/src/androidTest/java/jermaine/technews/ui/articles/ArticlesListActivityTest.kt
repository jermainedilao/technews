package jermaine.technews.ui.articles

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.github.kakaocup.kakao.screen.Screen.Companion.onScreen
import jermaine.data.PAGE_SIZE
import jermaine.technews.ui.BaseTestCase
import jermaine.technews.ui.bookmarks.BookmarkListScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ArticlesListActivityTest : BaseTestCase() {

    @get:Rule
    val rule = ActivityScenarioRule(ArticlesListActivity::class.java)

    @Test
    fun testListIsVisibleAndCorrectPageSize() {
        run {
            onScreen<ArticleListScreen> {
                assertListSize(PAGE_SIZE)
            }
        }
    }

    @Test
    fun testBookmarkButtonInList() {
        run {
            onScreen<ArticleListScreen> {
                bookMarkItem()
                validateBookmarkedItem()
                bookMarkItem() // Un-bookmark item
            }
        }
    }

    @Test
    fun testBookmark() {
        run {
            var title = ""
            step("Bookmark item from articles list") {
                onScreen<ArticleListScreen> {
                    bookMarkItem { title = it }
                    validateBookmarkedItem()
                }
            }
            step("Navigate to bookmark list") {
                onScreen<ArticleListScreen> {
                    navigateToBookmarksScreen()
                }
            }
            step("Validate bookmarked item in bookmarks list") {
                onScreen<BookmarkListScreen> {
                    assertItemIsVisibleByTitle(title)
                    device.exploit.pressBack(true)
                }
            }
            step("Un-bookmark item") {
                onScreen<ArticleListScreen> {
                    bookMarkItem()
                }
            }
        }
    }

// Needs refactoring. (Observable queries)
//    @Test
//    fun testUnBookmarkFromBookmarkListScreen() {
//        run {
//            var title = ""
//            step("Bookmark item from articles list") {
//                onScreen<ArticleListScreen> {
//                    bookMarkItem { title = it }
//                    validateBookmarkedItem()
//                }
//            }
//            step("Navigate to bookmark list") {
//                onScreen<ArticleListScreen> {
//                    navigateToBookmarksScreen()
//                }
//            }
//            step("Validate bookmarked item in bookmarks list") {
//                onScreen<BookmarkListScreen> {
//                    assertItemIsVisibleByTitle(title)
//                }
//            }
//            step("Un-bookmark item from bookmarks list") {
//                onScreen<BookmarkListScreen> {
//                    unBookmarkItemByTitle(title)
//                    device.exploit.pressBack()
//                }
//            }
//            step("Validate un-bookmarked item from articles list") {
//                onScreen<ArticleListScreen> {
//                    validateUnBookmarkedItemByTitle(title)
//                }
//            }
//        }
//    }
}