package jermaine.technews.di.module

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import jermaine.technews.application.App

@InstallIn(ApplicationComponent::class)
@EntryPoint
interface AggregatorEntryPoint {
    fun inject(app: App)
}