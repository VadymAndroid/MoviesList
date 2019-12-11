package com.crysberry.data.impl

import android.content.Context
import com.crysberry.data.models.Movie
import com.crysberry.data.moviesMapper
import com.crysberry.data.net.AppServer
import com.crysberry.data.safeUnwrap
import com.cryssberry.core.Results
import com.cryssberry.core.internal.NetworkFacade
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkFacadeImpl @Inject constructor(
    private val api: AppServer,
    private val context: Context
) : NetworkFacade {

    override fun getAllMoviesList(): Single<List<Results>> =
        api.getAllMoviesList()
            .safeUnwrap()
            .map(Movie::results)
            .map(moviesMapper.asListMapper())
}
