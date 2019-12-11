package com.cryssberry.core

import java.io.Serializable

data class Results(
    val title: String,
    val original_title: String,
    val overview: String
) : Serializable