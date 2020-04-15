package com.acorpas.rickmortychallenge.utils.factory

import com.acorpas.rickmortychallenge.domain.model.Character

class CharacterFactory {

    companion object {
        fun makeCharacterList(count: Int) : List<Character> {
            val characterList = mutableListOf<Character>()

            repeat(count) {
                characterList.add(makeCharacterModel())
            }

            return characterList
        }

        fun makeCharacterModel() : Character {
            return Character(id = 0,
                name = "",
                status = "",
                species = "",
                type = "",
                gender = "",
                image = "")
        }
    }
}