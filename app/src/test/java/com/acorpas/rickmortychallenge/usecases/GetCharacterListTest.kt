package com.acorpas.rickmortychallenge.usecases

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.acorpas.rickmortychallenge.data.repository.CharacterRepositoryImpl
import com.acorpas.rickmortychallenge.domain.usecases.base.GetCharactersList
import com.acorpas.rickmortychallenge.utils.RxImmediateSchedulerRule
import com.acorpas.rickmortychallenge.utils.factory.CharacterFactory
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

@RunWith(MockitoJUnitRunner::class)
class GetCharacterListTest {

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
    lateinit var repositoryImpl: CharacterRepositoryImpl

    lateinit var getListCharacters: GetCharactersList

    @Before
    fun setUp() {
        getListCharacters = GetCharactersList(repositoryImpl)
    }

    @Test
    fun getListCharacterReturnCharacters() {
        val characterList = CharacterFactory.makeCharacterList(NUMBER_CHARACTERS)
        Mockito.`when`(repositoryImpl.getListCharacter())
            .thenReturn(Single.just(characterList))

        val testObserver = getListCharacters.buildUseCase(false).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val listResult = testObserver.values()[0]
        MatcherAssert.assertThat(listResult.size, CoreMatchers.`is`(NUMBER_CHARACTERS))
        assert(listResult == characterList)
    }

    @Test
    fun getListCharacterEmptyReturn() {
        val characterList = CharacterFactory.makeCharacterList(0)
        Mockito.`when`(repositoryImpl.getListCharacter())
            .thenReturn(Single.just(characterList))

        val testObserver = getListCharacters.buildUseCase(false).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val listResult = testObserver.values()[0]
        MatcherAssert.assertThat(listResult.size, CoreMatchers.`is`(0))
        assert(listResult == characterList)
    }

    @Test
    fun getListCharactersReturnCharactersForceFromRemoteSource() {
        val characterList = CharacterFactory.makeCharacterList(NUMBER_CHARACTERS)
        Mockito.`when`(repositoryImpl.getListCharacter())
            .thenReturn(Single.just(characterList))

        val testObserver = getListCharacters.buildUseCase(true).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val listResult = testObserver.values()[0]
        MatcherAssert.assertThat(listResult.size, CoreMatchers.`is`(NUMBER_CHARACTERS))
        assert(listResult == characterList)
    }

    @Test
    fun getListCharacterErrorReturnError() {
        Mockito.`when`(repositoryImpl.getListCharacter())
            .thenReturn(Single.error { throw RuntimeException() })

        val testObserver = getListCharacters.buildUseCase(false).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(RuntimeException::class.java)
    }
}