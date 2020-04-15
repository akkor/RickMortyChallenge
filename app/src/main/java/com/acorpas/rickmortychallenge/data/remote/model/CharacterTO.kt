package com.acorpas.rickmortychallenge.data.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterTO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("species")
    val species: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("image")
    val imageURL: String


)