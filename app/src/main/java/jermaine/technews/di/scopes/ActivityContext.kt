package jermaine.technews.di.scopes

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class ActivityContext