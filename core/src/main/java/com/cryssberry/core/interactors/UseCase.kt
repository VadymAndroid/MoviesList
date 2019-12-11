package com.cryssberry.core.interactors

import com.cryssberry.core.internal.SingleUseCase
import com.cryssberry.core.Results
import io.reactivex.Single
import javax.inject.Inject


class MoviesUseCase @Inject constructor() : SingleUseCase.ParametrizedUseCase<MoviesUseCase, List<Results>>() {

    override fun buildSingle(params: MoviesUseCase): Single<List<Results>> =
        networkFacade.getAllMoviesList()

}
