package jermaine.technews.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import jermaine.domain.di.scopes.ApplicationContext
import javax.inject.Singleton


@Module
class AppModule {

    @ApplicationContext
    @Singleton
    @Provides
    fun providesApplicationContext(app: Application): Context = app

    @Singleton
    @Provides
    fun providesGson(): Gson = Gson()
}