package com.crysberry.data

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.Response

class ErrorBody(
        @SerializedName("Error") val error: String?,
        @SerializedName("Message") val message: String?,
        @SerializedName("InfoMessage") val infoMessage: String?
)

fun <T> Single<Response<T>>.safeUnwrap(): Single<T> =
        flatMap {
            when {
                it.errorBody() != null -> {
                    Gson()
                            .fromJson<ErrorBody?>(it.errorBody()!!.string(), ErrorBody::class.java)
                            ?.let { errorBody ->
                                (errorBody.error?.takeIf { it.isNotEmpty() }
                                        ?: errorBody.message?.takeIf { it.isNotEmpty() }
                                        ?: errorBody.infoMessage)
                                        .let { Exception(it) }
                                        .let { Single.error<T>(it) }
                            } ?: Single.error(Exception("unknown error"))
                }

                else -> Single.just(it.body())
            }
        }


