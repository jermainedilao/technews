package jermaine.technews.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import jermaine.data.articles.service.ArticlesService
import jermaine.data.articles.service.ArticlesServiceImpl


@Module
class ServiceModule(private val app: Application) {
    @Provides
    fun providesArticlesService(articlesService: ArticlesServiceImpl): ArticlesService = articlesService
}