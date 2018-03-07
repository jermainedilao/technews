package jermaine.domain.interactortype

import io.reactivex.Single


interface SingleUseCase<T> {
    fun execute(): Single<T>
}