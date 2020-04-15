package com.acorpas.rickmortychallenge.data.remote.mapper

import com.acorpas.rickmortychallenge.data.remote.model.CharacterTO
import com.acorpas.rickmortychallenge.domain.model.Character
import javax.inject.Inject

class CharacterToMapper @Inject constructor() {

    fun toEntity(value: CharacterTO): Character {
        return Character(
            id = value.id,
            name = value.name,
            status = value.status,
            species = value.species,
            type = value.type,
            gender = value.gender,
            image = value.imageURL)

    }

    fun toEntity(values: List<CharacterTO>): List<Character> = values.map { toEntity(it) }

}