package com.crysberry.data

import com.crysberry.data.models.Movie
import com.cryssberry.core.Results


abstract class Mapper<in T, out U> private constructor() : (T) -> U {

    fun asListMapper(): Mapper<List<T>?, List<U>> = object : Mapper<List<T>?, List<U>>() {

        override fun invoke(t: List<T>?) = t?.map(this@Mapper) ?: emptyList()
    }

    companion object {

        fun <T, U> build(action: T.() -> U): Mapper<T, U> = object : Mapper<T, U>() {
            override fun invoke(t: T) = action(t)
        }
    }
}


val moviesMapper = Mapper.build<Movie.Results, Results> {
    Results(
        title = title,
        original_title = original_title,
        overview = overview
    )
}

