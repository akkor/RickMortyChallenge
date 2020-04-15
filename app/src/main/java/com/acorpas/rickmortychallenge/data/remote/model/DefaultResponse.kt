package com.acorpas.rickmortychallenge.data.remote.model

import com.google.gson.annotations.SerializedName

data class DefaultResponse<T>(
    @SerializedName("info")
    val info: InfoTO,

    @SerializedName("results")
    val data: T
)