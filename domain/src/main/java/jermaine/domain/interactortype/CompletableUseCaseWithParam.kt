package jermaine.domain.interactortype

import io.reactivex.Completable


interface CompletableUseCaseWithParam<in T> {
    fun execute(t: T): Completable
}