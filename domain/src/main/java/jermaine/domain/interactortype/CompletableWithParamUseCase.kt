package jermaine.domain.interactortype

import io.reactivex.Completable


interface CompletableWithParamUseCase<in T> {
    fun execute(t: T): Completable
}