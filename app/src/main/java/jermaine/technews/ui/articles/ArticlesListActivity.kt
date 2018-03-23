package jermaine.technews.ui.articles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import jermaine.technews.R
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.ui.base.BaseActivity
import jermaine.technews.ui.bookmarks.BookmarksListActivity
import jermaine.technews.util.callbacks.OnLastItemCallback
import kotlinx.android.synthetic.main.activity_articles_list.*
import javax.inject.Inject

class ArticlesListActivity : BaseActivity(), OnLastItemCallback {
    companion object {
        val TAG = "ArticlesListActivity"
    }

    @Inject
    lateinit var viewModel: ArticlesViewModel

    private lateinit var compositeDisposable: CompositeDisposable

    /**
     * Emits new list of articles.
     * Requires "page" (used for pagination) as parameter.
     **/
    private lateinit var fetchArticles: PublishSubject<Int>

    /**
     * Page for pagination.
     **/
    private var page: Int = 1

    /**
     * True, if we should fetch next page. False if not.
     **/
    private var shouldFetchNextPage: Boolean = true

    private lateinit var adapter: ArticlesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        setContentView(R.layout.activity_articles_list)

        compositeDisposable = CompositeDisposable()
        fetchArticles = PublishSubject.create()

        initializeList()
        setSwipeRefreshListener()
        setLoadingIndicators()

        initializeFetchArticles()

        // Proceed with fetching first page.
        fetchArticles.onNext(1)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    /**
     * Initializes the recycler view boiler plate.
     **/
    private fun initializeList() {
        adapter = ArticlesListAdapter(this, arrayListOf(), this)
        val manager = LinearLayoutManager(this)

        val itemClick = adapter.clickEvent
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    startBrowser(it.url)
                }
        val bookmark = adapter.bookmarkEvent
                .observeOn(AndroidSchedulers.mainThread())
                .concatMapCompletable {
                    bookmarkOrUnBookmarkArticle(it)
                }
                .subscribe {
                    Log.d(TAG, "Done bookmarking/removing bookmark article.")
                }

        compositeDisposable.addAll(itemClick, bookmark)

        recycler_view.layoutManager = manager
        (recycler_view.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recycler_view.adapter = adapter
    }

    /**
     * Bookmark or removes bookmark from article.
     *
     * If initial bookmark state of article passed is true,
     * this will remove the bookmark from article.
     * Otherwise, it will bookmark the article.
     *
     * @param pair Pair of position of the item from the list and the item itself.
     * @return Completable - emits when bookmark or removing bookmark is finished.
     **/
    private fun bookmarkOrUnBookmarkArticle(pair: Pair<Int, ArticleViewObject>): Completable {
        val position = pair.first
        val item = pair.second
        return if (item.bookmarked) {
            viewModel.removeBookmarkedArticle(item)
                    .andThen(adapter.removeBookmarkedArticle(position, item))
                    .doOnComplete {
                        Log.d(TAG, "Done removing bookmark from article.")
                    }
        } else {
            viewModel.bookmarkArticle(item)
                    .andThen(adapter.bookmarkArticle(position, item))
                    .doOnComplete {
                        Log.d(TAG, "Done bookmarking article.")
                    }
        }
    }

    /**
     * Sets swipe refresh listener to swipe refresh layout.
     **/
    private fun setSwipeRefreshListener() {
        swipe_refresh_layout.setOnRefreshListener {
            // Reset page to 1.
            page = 1
            fetchArticles.onNext(page)
        }
    }

    private fun setLoadingIndicators() {
        /**
         * Responsible for showing the refresh indicator.
         **/
        val refreshIndicator = viewModel.refreshIndicator
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    swipe_refresh_layout.isRefreshing = it
                }

        /**
         * Responsible for showing the pagination indicator.
         **/
        val paginateIndicator = viewModel.paginateIndicator
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it) {
                        val lastItemViewType = adapter.getItemViewType(adapter.itemCount - 1)
                        if (lastItemViewType == ArticlesListAdapter.VIEW_TYPE_ARTICLE) {
                            adapter.showPaginateIndicator()
                        }
                    } else {
                        adapter.hidePaginateIndicator()
                    }
                }

        compositeDisposable.addAll(refreshIndicator, paginateIndicator)
    }

    override fun onLastItem() {
        if (shouldFetchNextPage) {
            Log.d(TAG, "onLastItem: ")
            fetchArticles.onNext(++page)
        }
    }

    /**
     * Subscribes fetch articles subject. This method is responsible
     * for displaying the emitted lists to the view.
     **/
    private fun initializeFetchArticles() {
        val disposable = fetchArticles
                .flatMapSingle {
                    viewModel.fetchArticles(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    // Wait until the current request is done.
                    shouldFetchNextPage = false
                }
                .doOnNext {
                    shouldFetchNextPage = !it.isEmpty()
                }
                .subscribe({
                    updateList(it)
                }, {
                    Log.e(TAG, "onCreate", it)
                    throw it
                })

        compositeDisposable.add(disposable)
    }

    /**
     * Updates the currently displayed list.
     * If page == 1, replaces the currently displayed list with the new list.
     * Else, appends the new list at the end of the currently displayed list.
     **/
    private fun updateList(articles: List<ArticleViewObject>) {
        if (page == 1) {
            adapter.newList(articles)
        } else {
            adapter.append(articles)
        }
    }

    /**
     * Starts browser.
     *
     * @param url Url to be opened in the browser.
     **/
    private fun startBrowser(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))

        val intent = builder.build()
        intent.intent.`package` = "com.android.chrome"
        intent.launchUrl(this, Uri.parse(url))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bookmarks, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.view_bookmarks -> {
                val intent = Intent(this, BookmarksListActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
