package jermaine.domain.interactortype


interface WithParamUseCase<in T, R> {
    suspend fun execute(t: T): R
}