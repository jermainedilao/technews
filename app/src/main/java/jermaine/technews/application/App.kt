package jermaine.technews.application

import android.app.Application
import jermaine.technews.di.component.AppComponent


class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = AppComponent.initialize()
    }
}