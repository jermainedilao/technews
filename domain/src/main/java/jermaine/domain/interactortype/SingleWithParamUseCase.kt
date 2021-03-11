package jermaine.domain.interactortype

import io.reactivex.Single


interface SingleWithParamUseCase <in T, R> {
    fun execute(t: T): Single<R>
}