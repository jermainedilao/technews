package jermaine.technews.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import jermaine.data.di.module.DatabaseModule
import jermaine.data.di.module.NetworkModule
import jermaine.technews.application.App
import jermaine.technews.di.module.*
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
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
                .build()
    }

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}