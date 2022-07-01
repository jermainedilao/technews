package jermaine.technews.application

import android.app.Application
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import jermaine.technews.di.module.AggregatorEntryPoint

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    fun component(): AggregatorEntryPoint {
        return EntryPoints.get(this, AggregatorEntryPoint::class.java)
    }
}