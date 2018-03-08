package jermaine.technews.di.component

import android.app.Application
import dagger.Component
import jermaine.data.di.module.NetworkModule
import jermaine.technews.di.module.*
import jermaine.technews.ui.articles.ArticlesListActivity
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AppModule::class,
            RepositoryModule::class,
            ServiceModule::class,
            UseCaseModule::class,
            ViewModelModule::class,
            NetworkModule::class
        ]
)
interface AppComponent {
    companion object {
        fun initialize(app: Application): AppComponent =
                DaggerAppComponent.builder()
                        .appModule(AppModule())
                        .repositoryModule(RepositoryModule())
                        .serviceModule(ServiceModule())
                        .useCaseModule(UseCaseModule())
                        .networkModule(NetworkModule(app))
                        .build()
    }

    fun inject(articlesListActivity: ArticlesListActivity)
}