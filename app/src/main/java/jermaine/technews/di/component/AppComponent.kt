package jermaine.technews.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import jermaine.data.di.module.DatabaseModule
import jermaine.data.di.module.NetworkModule
import jermaine.technews.di.module.*
import jermaine.technews.ui.articles.ArticlesListActivity
import jermaine.technews.ui.bookmarks.BookmarksListActivity
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AppModule::class,
            RepositoryModule::class,
            ServiceModule::class,
            UseCaseModule::class,
            ViewModelModule::class,
            NetworkModule::class,
            DatabaseModule::class,
            DaoModule::class
        ]
)
interface AppComponent {
    companion object {
        fun initialize(app: Application): AppComponent =
                DaggerAppComponent.builder()
                        .app(app)
                        .repositoryModule(RepositoryModule())
                        .serviceModule(ServiceModule())
                        .useCaseModule(UseCaseModule())
                        .networkModule(NetworkModule())
                        .databaseModule(DatabaseModule())
                        .daoModule(DaoModule())
                        .build()
    }

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder

        fun build(): AppComponent
        fun repositoryModule(module: RepositoryModule): Builder
        fun serviceModule(module: ServiceModule): Builder
        fun useCaseModule(module: UseCaseModule): Builder
        fun networkModule(module: NetworkModule): Builder
        fun databaseModule(module: DatabaseModule): Builder
        fun daoModule(module: DaoModule): Builder
    }

    fun inject(articlesListActivity: ArticlesListActivity)

    fun inject(articlesListActivity: BookmarksListActivity)
}