package jermaine.technews.util

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import jermaine.domain.di.scopes.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Helper class in getting android resources.
 *
 * Note: Added this class to remove context dependencies in ViewModels or other classes
 * that needs access to android resources. To make unit testing easier.
 */
@Singleton
class ResourceManager @Inject constructor(@ApplicationContext private val context: Context) {
    fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    fun getQuantityString(@PluralsRes id: Int, quantity: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(id, quantity, *formatArgs)
    }
}