package jermaine.technews.ui.articles

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        setContentView(R.layout.activity_articles_list)

        viewModel.fetchArticles()
                .observeOn(AndroidSchedulers.mainThread())
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

        recycler_view.layoutManager = manager
        recycler_view.adapter = adapter
    }
}
