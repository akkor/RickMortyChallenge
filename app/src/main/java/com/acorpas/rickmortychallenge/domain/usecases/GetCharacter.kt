package com.acorpas.rickmortychallenge.domain.usecases

import com.acorpas.rickmortychallenge.data.repository.CharacterRepositoryImpl
import com.acorpas.rickmortychallenge.domain.model.Character
import com.acorpas.rickmortychallenge.domain.usecases.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetCharacter @Inject constructor(
    private val characterRepositoryImpl: CharacterRepositoryImpl
) : SingleUseCase<Int, Character>() {

    override fun buildUseCase(params: Int?): Single<Character> {
        return characterRepositoryImpl.getCharacterById(params!!)
    }
}