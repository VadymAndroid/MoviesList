package com.cryssberry.core.internal

import com.cryssberry.core.Results
import io.reactivex.Single

interface LocalStorage {

    var onBoardingShown: Boolean

    var movies: ExpirableValue<List<Results>>

    data class ExpirableValue<T>(
        val value: T,
        val expired: Boolean = true
    )
}


interface NetworkFacade {

    fun getAllMoviesList(): Single<List<Results>>
}
