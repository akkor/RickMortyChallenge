package com.acorpas.rickmortychallenge.utils.factory

import com.acorpas.rickmortychallenge.data.remote.model.CharacterTO

class CharacterToFactory {

    companion object {
        fun makeCharacterList(count: Int) : List<CharacterTO> {
            val characterList = mutableListOf<CharacterTO>()

            repeat(count) {
                characterList.add(makeCharacterModel())
            }

            return characterList
        }

        fun makeCharacterModel() : CharacterTO {
            return CharacterTO(id = 0,
                name = "",
                status = "",
                species = "",
                type = "",
                gender = "",
                imageURL = "")
        }
    }
}