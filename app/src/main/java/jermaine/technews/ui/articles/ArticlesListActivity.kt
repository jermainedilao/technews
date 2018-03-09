package jermaine.technews.ui.articles

import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import jermaine.domain.articles.model.Article
import jermaine.technews.R
import jermaine.technews.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_articles_list.*
import javax.inject.Inject

class ArticlesListActivity : BaseActivity() {

    companion object {
        val TAG = "ArticlesListActivity"
    }

    @Inject
    lateinit var viewModel: ArticlesViewModel

    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        setContentView(R.layout.activity_articles_list)

        compositeDisposable = CompositeDisposable()

        fetchArticles()
        setSwipeRefreshListener()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun setSwipeRefreshListener() {
        swipe_refresh_layout.setOnRefreshListener {
            fetchArticles()
        }
    }

    private fun fetchArticles() {
        viewModel.fetchArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    swipe_refresh_layout.isRefreshing = true
                }
                .doOnSuccess {
                    swipe_refresh_layout.isRefreshing = false
                }
                .doOnError {
                    swipe_refresh_layout.isRefreshing = false
                }
                .subscribe({
                    initializeList(it)
                }, {
                    Log.e(TAG, "onCreate", it)
                    throw it
                })
    }

    private fun initializeList(articles: List<Article>) {
        val adapter = ArticlesListAdapter(articles)
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

    private fun startBrowser(url: String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))

        val intent = builder.build()
        intent.intent.`package` = "com.android.chrome"
        intent.launchUrl(this, Uri.parse(url))
    }
}
