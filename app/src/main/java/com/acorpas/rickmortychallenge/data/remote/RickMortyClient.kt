package com.acorpas.rickmortychallenge.data.remote

import com.acorpas.rickmortychallenge.data.remote.model.CharacterTO
import com.acorpas.rickmortychallenge.data.remote.model.DefaultResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RickMortyClient {
    @GET("character")
    fun getCharactersList(): Single<DefaultResponse<List<CharacterTO>>>

    @GET("character/{characterId}")
    fun getCharacterFromId(@Path("characterId") characterId: Int): Single<CharacterTO>
}