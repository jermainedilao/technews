package jermaine.technews.application

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import jermaine.technews.di.module.AggregatorEntryPoint

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
    }

    fun component(): AggregatorEntryPoint {
        return EntryPoints.get(this, AggregatorEntryPoint::class.java)
    }
}