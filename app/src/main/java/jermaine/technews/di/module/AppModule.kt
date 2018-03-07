package jermaine.technews.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {
    @Singleton
    @Provides
    fun providesGson(): Gson = Gson()
}