package jermaine.technews.di.module

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jermaine.technews.application.App

@InstallIn(SingletonComponent::class)
@EntryPoint
interface AggregatorEntryPoint {
    fun inject(app: App)
}