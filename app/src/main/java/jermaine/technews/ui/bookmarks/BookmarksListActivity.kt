package jermaine.technews.ui.bookmarks

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import jermaine.technews.R
import jermaine.technews.base.BaseActivity
import jermaine.technews.databinding.ActivityBookmarksListBinding
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.util.callbacks.OnLastItemCallback

@AndroidEntryPoint
class BookmarksListActivity : BaseActivity<ActivityBookmarksListBinding, BookmarksViewModel>(),
    OnLastItemCallback {
    companion object {
        private const val TAG = "BookmarksListActivity"
    }

    override val layoutId: Int
        get() = R.layout.activity_bookmarks_list

    /**
     * Page for pagination.
     **/
    private var page: Int = 1

    private lateinit var adapter: BookmarksListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        setSupportActionBar(binding.toolbar)
        initializeToolbar()
        initializeList()
        fetchBookmarkedArticles()
    }

    private fun initializeToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.bookmarks)
    }

    private fun fetchBookmarkedArticles() {
        viewModel.compositeDisposable.add(
            viewModel.fetchBookmarkedArticles(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateList(it)
                }, {
                    Log.e(TAG, "onCreate: ", it)
                })
        )
    }

    /**
     * Initializes the recycler view boiler plate.
     **/
    private fun initializeList() {
        adapter = BookmarksListAdapter(arrayListOf(), this)
        val manager = LinearLayoutManager(this)

        val itemClick = adapter.clickEvent
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startBrowser(it.url)
            }

        val itemSourceClick = adapter
            .sourceClickEvent
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

        viewModel.compositeDisposable.addAll(itemClick, bookmark, itemSourceClick)

        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}