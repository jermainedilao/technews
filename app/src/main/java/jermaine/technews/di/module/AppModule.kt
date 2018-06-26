package jermaine.technews.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val app: Application) {
    @Singleton
    @Provides
    fun providesApplicationContext(): Context = app

    @Singleton
    @Provides
    fun providesGson(): Gson = Gson()
}