package jermaine.technews.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import jermaine.technews.di.scopes.ActivityContext


/**
 * We require passing the activity so dagger would know which activity to provide.
 */
@Module
abstract class BaseActivityModule<T: AppCompatActivity> {
    @Provides
    @ActivityContext
    fun providesActivityContext(activity: T): Context {
        return activity
    }
}