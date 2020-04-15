package com.acorpas.rickmortychallenge.data.repository.utils

import com.acorpas.rickmortychallenge.domain.model.Character
import io.reactivex.Single


interface CharacterRepository {

    /**
     * Gets a list of characters
     *
     * @return a List of the Characters available.
     */
    fun getListCharacter(): Single<List<Character>>


    /**
     * Gets a character by Id
     *
     * @return a character by characterID.
     */
    fun getCharacterById(idCharacter: Int) : Single<Character>


    /**
     * Mark cache as invalid
     */
    fun invalidateCache()
}