package com.acorpas.rickmortychallenge.data.repository

import android.util.ArrayMap
import androidx.core.util.arrayMapOf
import com.acorpas.rickmortychallenge.data.remote.RickMortyClient
import com.acorpas.rickmortychallenge.data.remote.mapper.CharacterToMapper
import com.acorpas.rickmortychallenge.data.repository.utils.CacheTimer
import com.acorpas.rickmortychallenge.data.repository.utils.CharacterRepository
import com.acorpas.rickmortychallenge.domain.model.Character
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val service: RickMortyClient,
    private val mapper: CharacterToMapper
) : CharacterRepository {


    @Inject
    lateinit var cacheTimer: CacheTimer
    private val cache: ArrayMap<Int, Character> = arrayMapOf()


    override fun getListCharacter(): Single<List<Character>> {
        if (cacheTimer.isCacheDirty) {
            cache.clear()
        }

        return when (cacheTimer.isCacheDirty) {
            true ->
                service.getCharactersList()
                    .map { mapper.toEntity(it.data) }
                    .doOnSuccess { listCharacter ->
                        listCharacter.forEach { character ->
                            cache.put(character.id, character)
                        }
                        cacheTimer.markValid()
                    }
            false ->
                Single.just(cache.values.toList())
        }
    }

    override fun getCharacterById(idCharacter: Int): Single<Character> {
        if (cacheTimer.isCacheDirty) {
            cache.clear()
        }

        return when (cacheTimer.isCacheDirty || !cache.containsKey(idCharacter)) {
            true ->
                service.getCharacterFromId(idCharacter)
                    .map { mapper.toEntity(it) }
                    .doOnSuccess { character ->
                        cache.put(idCharacter, character)
                        cacheTimer.markValid()
                    }
            false ->
                Single.just(cache.getValue(idCharacter))
        }
    }


    override fun invalidateCache() {
        cacheTimer.markInvalid()
        cache.clear()

    }
}