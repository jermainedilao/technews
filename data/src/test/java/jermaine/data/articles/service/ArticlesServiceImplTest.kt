package jermaine.data.articles.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import jermaine.data.articles.ApiService
import jermaine.data.articles.service.model.FetchArticlesApiResponse
import jermaine.domain.articles.model.Article
import jermaine.domain.articles.model.Source
import org.junit.Before
import org.junit.Test

class ArticlesServiceImplTest {

    private val apiResponse = FetchArticlesApiResponse(
        "200",
        2,
        listOf(
            Article(
                "1",
                Source("1", "source 1"),
                "author1",
                "title 1",
                "description 1",
                "url 1",
                "urlToImage 1",
                "publishedAt 1",
                false
            ),
            Article(
                "2",
                Source("2", "source 2"),
                "author 2",
                "title 2",
                "description 2",
                "url 2",
                "urlToImage 2",
                "publishedAt 2",
                false
            )
        )
    )

    var apiService: ApiService = mock()

    lateinit var articlesService: ArticlesServiceImpl

    @Before
    fun setUp() {
        articlesService = ArticlesServiceImpl(apiService)
    }
//
//    @Test
//    fun testShouldCompleteArticles() {
//        mockApiServiceFetchArticles()
//
//        articlesService.fetchArticles(0)
//            .test()
//            .assertComplete()
//    }
//
//    @Test
//    fun testShouldReturnArticlesFromResponse() {
//        mockApiServiceFetchArticles()
//
//        articlesService.fetchArticles(0)
//            .test()
//            .assertValue(apiResponse.articles)
//    }
//
//    private fun mockApiServiceFetchArticles() {
//        whenever(apiService.fetchArticles(any(), any()))
//            .thenReturn(Single.just(apiResponse))
//    }
}