package jermaine.technews.ui.articles

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import jermaine.domain.articles.model.Article
import jermaine.technews.R
import jermaine.technews.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_articles_list.*
import javax.inject.Inject

class ArticlesListActivity : BaseActivity(), LoadNextPageCallback {
    companion object {
        val TAG = "ArticlesListActivity"
    }

    @Inject
    lateinit var viewModel: ArticlesViewModel

    private lateinit var compositeDisposable: CompositeDisposable

    /**
     * Subject responsible for fetching lists. Requires "page" as parameter.
     **/
    private lateinit var fetchArticles: PublishSubject<Int>

    /**
     * Page for pagination.
     **/
    private var page: Int = 1
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

        fetchArticles()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun initializeList() {
        adapter = ArticlesListAdapter(arrayListOf(), this)
        val manager = LinearLayoutManager(this)

        val itemClick = adapter.clickEvent
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    startBrowser(it.url)
                }
        compositeDisposable.add(itemClick)

        recycler_view.layoutManager = manager
        recycler_view.adapter = adapter
    }

    private fun setSwipeRefreshListener() {
        swipe_refresh_layout.setOnRefreshListener {
            // Reset page to 1.
            page = 1
            fetchArticles.onNext(page)
        }
    }

    private fun setLoadingIndicators() {
        val refreshIndicator = viewModel.refreshIndicator
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    swipe_refresh_layout.isRefreshing = it
                }

        val paginateIndicator = viewModel.paginateIndicator
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it) {
                        val lastItemViewType = adapter.getItemViewType(adapter.itemCount - 1)
                        if (lastItemViewType == ArticlesListAdapter.VIEW_TYPE_ARTICLE) {
                            val loader = Article(title = ArticlesListAdapter.LOADER)
                            adapter.append(loader)
                        }
                    } else {
                        adapter.removeLoader()
                    }
                }

        compositeDisposable.addAll(refreshIndicator, paginateIndicator)
    }

    override fun onLoadNextPage() {
        if (shouldFetchNextPage) {
            Log.d(TAG, "onLoadNextPage: ")
            fetchArticles.onNext(++page)
        }
    }

    private fun fetchArticles() {
        val disposable = fetchArticles
                .flatMap {
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

        // Proceed with fetching first page.
        fetchArticles.onNext(page)
    }

    private fun updateList(articles: List<Article>) {
        if (page == 1) {
            adapter.newList(articles)
        } else {
            adapter.append(articles)
        }
    }

    private fun startBrowser(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))

        val intent = builder.build()
        intent.intent.`package` = "com.android.chrome"
        intent.launchUrl(this, Uri.parse(url))
    }
}
