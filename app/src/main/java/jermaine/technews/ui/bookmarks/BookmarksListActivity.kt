package jermaine.technews.ui.bookmarks

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import jermaine.technews.R
import jermaine.technews.ui.articles.ArticlesViewModel
import jermaine.technews.ui.articles.adapter.ArticlesListAdapter
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.ui.base.BaseActivity
import jermaine.technews.util.callbacks.OnLastItemCallback
import kotlinx.android.synthetic.main.activity_articles_list.*
import javax.inject.Inject


class BookmarksListActivity : BaseActivity(), OnLastItemCallback {

    companion object {
        val TAG = "BookmarksListActivity"
    }

    @Inject
    lateinit var viewModel: ArticlesViewModel

    /**
     * Page for pagination.
     **/
    private var page: Int = 1

    private lateinit var adapter: ArticlesListAdapter
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_list)
        getComponent().inject(this)
        setSupportActionBar(toolbar)

        compositeDisposable = CompositeDisposable()

        initializeToolbar()
        initializeList()
        fetchBookmarkedArticles()
    }

    private fun initializeToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.bookmarks_text)
    }

    private fun fetchBookmarkedArticles() {
        viewModel.fetchBookmarkedArticles(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateList(it)
                }, {
                    Log.e(TAG, "onCreate: ", it)
                })
    }

    /**
     * Initializes the recycler view boiler plate.
     **/
    private fun initializeList() {
        adapter = ArticlesListAdapter(arrayListOf(), this)
        val manager = LinearLayoutManager(this)

        val itemClick = adapter.clickEvent
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    startBrowser(it.url)
                }
        val bookmark = adapter.bookmarkEvent
                .observeOn(AndroidSchedulers.mainThread())
                .concatMapCompletable {
                    removeBookmarkedArticle(it)
                }
                .subscribe {
                    Log.d(TAG, "Done bookmarking/removing bookmark article.")
                }

        compositeDisposable.addAll(itemClick, bookmark)

        recycler_view.layoutManager = manager
        recycler_view.adapter = adapter
    }

    /**
     * Removes bookmarked article.
     *
     * @param pair Pair of position of the item from the list and the item itself.
     * @return Completable - emits when bookmark or removing bookmark is finished.
     **/
    private fun removeBookmarkedArticle(pair: Pair<Int, ArticleViewObject>): Completable {
        val position = pair.first
        val item = pair.second

        return viewModel.removeBookmarkedArticle(item)
                .andThen(adapter.remove(position))
    }

    override fun onLastItem() {

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}