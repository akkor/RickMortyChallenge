package com.acorpas.rickmortychallenge.repository

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.acorpas.rickmortychallenge.data.remote.RickMortyClient
import com.acorpas.rickmortychallenge.data.remote.mapper.CharacterToMapper
import com.acorpas.rickmortychallenge.data.remote.model.DefaultResponse
import com.acorpas.rickmortychallenge.data.repository.CharacterRepositoryImpl
import com.acorpas.rickmortychallenge.data.repository.utils.CacheTimer
import com.acorpas.rickmortychallenge.utils.Constants
import com.acorpas.rickmortychallenge.utils.RxImmediateSchedulerRule
import com.acorpas.rickmortychallenge.utils.factory.CharacterToFactory
import com.acorpas.rickmortychallenge.utils.factory.InfoToFactory
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class CharacterRepositoryTest {
    companion object {
        private const val NUMBER_CHARACTERS = 5
    }

    @Suppress("unused")
    @get:Rule // used to make all live data calls sync
    val instantExecutor = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var service: RickMortyClient

    lateinit var mapper: CharacterToMapper

    lateinit var repository: CharacterRepositoryImpl

    @Before
    fun setUp() {
        mapper = CharacterToMapper()
        repository = CharacterRepositoryImpl(service, mapper)
        repository.cacheTimer = CacheTimer(true,
            Constants.TIMEOUT_CACHE_REPOSITORY.toLong(), TimeUnit.MINUTES)
    }

    @Test
    fun getListCharacterReturnCharacters() {
        val characterTOList = CharacterToFactory.makeCharacterList(NUMBER_CHARACTERS)
        val infoTo = InfoToFactory.makeInfoToModel()
        val characterList = characterTOList.map { mapper.toEntity(it) }
        Mockito.`when`(service.getCharactersList())
            .thenReturn(Single.just(DefaultResponse(infoTo, characterTOList)))

        val testObserver = repository.getListCharacter().test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(NUMBER_CHARACTERS))
        assert(result == characterList)
    }


    @Test
    fun getListCharacterErrorReturnError() {
        Mockito.`when`(service.getCharactersList())
            .thenReturn(Single.error { throw RuntimeException() })

        val testObserver = repository.getListCharacter().test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(RuntimeException::class.java)
    }


    @Test
    fun getListCharacterReturnCharactersAfterInvalidateCache() {
        val characterTOList = CharacterToFactory.makeCharacterList(NUMBER_CHARACTERS)
        val infoTo = InfoToFactory.makeInfoToModel()
        val characterList = characterTOList.map { mapper.toEntity(it) }

        Mockito.`when`(service.getCharactersList())
            .thenReturn(Single.just(DefaultResponse(infoTo, characterTOList)))

        var testObserver = repository.getListCharacter().test()
        testObserver.awaitTerminalEvent()

        repository.invalidateCache()
        testObserver = repository.getListCharacter().test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(NUMBER_CHARACTERS))
        assert(result == characterList)
    }





    @Test
    fun getCharacterByIDReturnCharacter() {
        val characterTO = CharacterToFactory.makeCharacterModel()
        val character = mapper.toEntity(characterTO)

        Mockito.`when`(service.getCharacterFromId(Mockito.anyInt()))
            .thenReturn(Single.just(characterTO))

        val testObserver = repository.getCharacterById(Mockito.anyInt()).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        assert(result == character)
    }


    @Test
    fun getCharacterByIDErrorReturnError() {
        Mockito.`when`(service.getCharactersList())
            .thenReturn(Single.error { throw RuntimeException() })

        val testObserver = repository.getListCharacter().test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(RuntimeException::class.java)



    }


    @Test
    fun getListCharacterByIDReturnCharacterAfterInvalidateCache() {
        val characterTO = CharacterToFactory.makeCharacterModel()
        val character = mapper.toEntity(characterTO)

        Mockito.`when`(service.getCharacterFromId(Mockito.anyInt()))
            .thenReturn(Single.just(characterTO))

        var testObserver = repository.getCharacterById(Mockito.anyInt()).test()
        testObserver.awaitTerminalEvent()

        repository.invalidateCache()
        testObserver = repository.getCharacterById(Mockito.anyInt()).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val result = testObserver.values()[0]
        assert(result == character)
    }


}