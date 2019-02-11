package jermaine.technews.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {
    @Singleton
    @Provides
    fun providesApplicationContext(app: Application): Context = app

    @Singleton
    @Provides
    fun providesGson(): Gson = Gson()
}