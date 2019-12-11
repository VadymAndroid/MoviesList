package com.crysberry.data.models

import com.google.gson.annotations.SerializedName

class Movie(
    @SerializedName("results") val results: List<Results>?
){
    class Results(
        @SerializedName("title") val title: String,
        @SerializedName("original_title") val original_title: String,
        @SerializedName("overview") val overview: String
    )
}
