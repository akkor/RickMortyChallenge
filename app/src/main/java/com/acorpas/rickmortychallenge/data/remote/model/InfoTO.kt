package com.acorpas.rickmortychallenge.data.remote.model

import com.google.gson.annotations.SerializedName

data class InfoTO (
    @SerializedName("count")
    val count: Int,

    @SerializedName("pages")
    val pages: Int,

    @SerializedName("next")
    val nextPage: String,

    @SerializedName("prev")
    val prevPage: String
)