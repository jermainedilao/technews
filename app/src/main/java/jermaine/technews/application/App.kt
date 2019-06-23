package jermaine.technews.application

import android.app.Activity
import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import jermaine.technews.di.component.AppComponent
import javax.inject.Inject


class App : Application(), HasActivityInjector {
    lateinit var component: AppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        component = AppComponent
            .initialize(this)
            .apply {
                inject(this@App)
            }
        AndroidThreeTen.init(this)
    }
}