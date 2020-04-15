package com.acorpas.rickmortychallenge.usecases

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.acorpas.rickmortychallenge.data.repository.CharacterRepositoryImpl
import com.acorpas.rickmortychallenge.domain.usecases.GetCharacter
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
class GetCharacterTest {
    @Suppress("unused")
    @get:Rule // used to make all live data calls sync
    val instantExecutor = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var repositoryImpl: CharacterRepositoryImpl

    lateinit var getCharacterByID: GetCharacter

    @Before
    fun setUp() {
        getCharacterByID = GetCharacter(repositoryImpl)
    }

    @Test
    fun getCharacterReturnCharacter() {
        val character = CharacterFactory.makeCharacterModel()
        Mockito.`when`(repositoryImpl.getCharacterById(Mockito.anyInt()))
            .thenReturn(Single.just(character))

        val testObserver = getCharacterByID.buildUseCase(Mockito.anyInt()).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertComplete()
        testObserver.assertNoErrors()

        val characterResult = testObserver.values()[0]
        assert(characterResult == character)
    }

    @Test
    fun getCharacterErrorReturnError() {
        Mockito.`when`(repositoryImpl.getCharacterById(Mockito.anyInt()))
            .thenReturn(Single.error { throw RuntimeException() })

        val testObserver = getCharacterByID.buildUseCase(Mockito.anyInt()).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNotComplete()
        testObserver.assertError(RuntimeException::class.java)
    }
}