package jermaine.technews.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import jermaine.data.articles.ApiService
import jermaine.data.articles.service.ArticlesService
import jermaine.data.articles.service.ArticlesServiceImpl
import javax.inject.Singleton


@Module
class ServiceModule(private val app: Application) {
    @Provides
    @Singleton
    fun providesArticlesService(apiService: ApiService): ArticlesService = ArticlesServiceImpl(apiService, app)
}