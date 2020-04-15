package com.acorpas.rickmortychallenge.domain.usecases.base

import io.reactivex.Completable

abstract class CompletableUseCase<in T> :
        RxUseCase<T, Completable>()