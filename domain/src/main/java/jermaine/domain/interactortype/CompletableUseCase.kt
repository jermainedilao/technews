package jermaine.domain.interactortype

import io.reactivex.Completable


interface CompletableUseCase {
    fun execute(): Completable
}