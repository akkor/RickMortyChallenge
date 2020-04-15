package com.acorpas.rickmortychallenge.viemodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.acorpas.rickmortychallenge.domain.model.Character
import com.acorpas.rickmortychallenge.domain.model.Resource
import com.acorpas.rickmortychallenge.domain.model.Status
import com.acorpas.rickmortychallenge.domain.usecases.GetCharacter
import com.acorpas.rickmortychallenge.ui.characterDetail.CharacterDetailVewModel
import com.acorpas.rickmortychallenge.utils.RxImmediateSchedulerRule
import com.acorpas.rickmortychallenge.utils.factory.CharacterFactory
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterDetailVewModelTest {

    @Suppress("unused")
    @get:Rule // used to make all live data calls sync
    val instantExecutor = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var getCharacterDetail: GetCharacter

    @Mock
    lateinit var observer: Observer<Resource<Character>>

    lateinit var viewModel: CharacterDetailVewModel

    @Before
    fun setUp() {
        viewModel = CharacterDetailVewModel(getCharacterDetail)
    }

    @Test
    fun loadCharacterDetailLoadingState() {
        val character = CharacterFactory.makeCharacterModel()
        Mockito.`when`(getCharacterDetail.buildUseCase(Mockito.anyInt()))
            .thenReturn(Single.just(character))
        viewModel.resource.observeForever(observer)

        viewModel.loadCharacterDetail(Mockito.anyInt())

        Mockito.verify(observer).onChanged(Resource(Status.LOADING))
    }

    @Test
    fun loadCharacterDetailSuccessState() {
        val character = CharacterFactory.makeCharacterModel()
        Mockito.`when`(getCharacterDetail.buildUseCase(Mockito.anyInt()))
            .thenReturn(Single.just(character))

        viewModel.loadCharacterDetail(Mockito.anyInt())

        assert(viewModel.resource.value?.status == Status.SUCCESS)
    }

    @Test
    fun loadCharacterDetailErrorState() {
        Mockito.`when`(getCharacterDetail.buildUseCase(Mockito.anyInt()))
            .thenReturn(Single.error { throw RuntimeException() })

        viewModel.loadCharacterDetail(Mockito.anyInt())

        assert(viewModel.resource.value?.status == Status.FAILED)
    }

    @Test
    fun loadCharacterDetailReturnsCharacter() {
        val character = CharacterFactory.makeCharacterModel()
        Mockito.`when`(getCharacterDetail.buildUseCase(Mockito.anyInt()))
            .thenReturn(Single.just(character))

        viewModel.loadCharacterDetail(Mockito.anyInt())

        assert(viewModel.resource.value?.data == character)
    }

}