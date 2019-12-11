package com.crysberry.data.net

import com.crysberry.data.models.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface AppServer {
    @GET("/3/movie/popular?api_key=$API_KAY")
    fun getAllMoviesList(): Single<Response<Movie>>

    companion object {
        const val API_KAY = "41bebc121c2f186a94016815345a425f"
    }
}