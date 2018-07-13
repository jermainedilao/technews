package jermaine.technews.application

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import jermaine.technews.di.component.AppComponent


class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = AppComponent.initialize(this)
        AndroidThreeTen.init(this)
    }
}