package jermaine.technews.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jermaine.domain.di.scopes.ApplicationContext
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
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