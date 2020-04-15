package com.acorpas.rickmortychallenge.domain.usecases.base

import com.acorpas.rickmortychallenge.data.repository.CharacterRepositoryImpl
import com.acorpas.rickmortychallenge.domain.model.Character
import io.reactivex.Single
import javax.inject.Inject

class GetCharactersList @Inject constructor(
    private val characterRepositoryImpl: CharacterRepositoryImpl
) : SingleUseCase<Boolean, List<Character>>() {


    override fun buildUseCase(params: Boolean?): Single<List<Character>> {
        if (params!!) {
            characterRepositoryImpl.invalidateCache()
        }
        return characterRepositoryImpl.getListCharacter()
    }

}