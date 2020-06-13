package jermaine.technews.ui.articles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import jermaine.technews.R
import jermaine.technews.base.BaseActivity
import jermaine.technews.databinding.ActivityArticlesListBinding
import jermaine.technews.ui.articles.adapter.ArticlesListAdapterNew
import jermaine.technews.ui.articles.model.ArticleViewObject
import jermaine.technews.ui.bookmarks.BookmarksListActivity
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class ArticlesListActivity : BaseActivity<ActivityArticlesListBinding, ArticlesViewModel>() {
    companion object {
        private const val TAG = "ArticlesListActivity"
    }

    override val layoutId: Int
        get() = R.layout.activity_articles_list

    private lateinit var adapter: ArticlesListAdapterNew

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        setSupportActionBar(binding.toolbar)
        initializeList()
        setSwipeRefreshListener()
        setLoadingIndicators()

        // Fetch articles
        viewModel.articlesListLiveData.observe(this, Observer {
            adapter.submitList(it)
        })

        createDailyNotification()
    }

    private fun createDailyNotification() {
        val createDailyNotification = viewModel.createDailyNotifications()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "Successfully created daily notifications.")
            }, {
                Log.e(TAG, "Error on creating daily notifications.", it)
            })
        viewModel.compositeDisposable.add(createDailyNotification)
    }

    /**
     * Initializes the recycler view boiler plate.
     **/
    private fun initializeList() {
        adapter = ArticlesListAdapterNew()
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

        viewModel.compositeDisposable.addAll(itemClick, bookmark)

        binding.recyclerView.layoutManager = manager
        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding.recyclerView.adapter = adapter
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

        // If emitted value is true, display item into its loading state.
        // If false, display item into its default state.
        val loadingState = PublishSubject.create<Boolean>()

        // If bookmarking takes less than 200 milliseconds
        // don't mind setting its item to its loading state.
        val disposable = loadingState
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "loading state: $it")
                if (it) {
                    adapter.setLoadingState(position)
                } else {
                    adapter.setDefaultState(position)
                }
            }
        viewModel.compositeDisposable.add(disposable)

        loadingState.onNext(true)

        return if (item.bookmarked) {
            viewModel.removeBookmarkedArticle(item)
                .andThen(adapter.removeBookmarkedArticle(position))
                .doOnComplete {
                    loadingState.onNext(false)
                    Log.d(TAG, "Done removing bookmark from article.")
                }
        } else {
            viewModel.bookmarkArticle(item)
                .andThen(adapter.bookmarkArticle(position))
                .doOnComplete {
                    loadingState.onNext(false)
                    Log.d(TAG, "Done bookmarking article.")
                }
        }
    }

    /**
     * Sets swipe refresh listener to swipe refresh layout.
     **/
    private fun setSwipeRefreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.articlesDataSourceFactory.articlesDataSourceLiveData.value!!.invalidate()
        }
    }

    /**
     * Responsible for showing the paginate indicator in UI.
     **/
    private fun setLoadingIndicators() {
        // Responsible for showing the paginate indicator.
        Transformations.switchMap(
            viewModel.articlesDataSourceFactory.articlesDataSourceLiveData
        ) {
            it.paginateState
        }.observe(this, Observer { paginate ->
            adapter.setPaginateState(paginate!!)
        })
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.view_bookmarks -> {
                val intent = Intent(this, BookmarksListActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
