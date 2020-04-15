package com.acorpas.rickmortychallenge.viemodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.acorpas.rickmortychallenge.domain.model.Character
import com.acorpas.rickmortychallenge.domain.model.Resource
import com.acorpas.rickmortychallenge.domain.model.Status
import com.acorpas.rickmortychallenge.domain.usecases.base.GetCharactersList
import com.acorpas.rickmortychallenge.ui.characterList.CharacterListViewModel
import com.acorpas.rickmortychallenge.utils.RxImmediateSchedulerRule
import com.acorpas.rickmortychallenge.utils.factory.CharacterFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterListViewModelTest {
    companion object {
        private const val NUMBER_CHARACTERS = 5
    }


    @get:Rule // used to make all live data calls sync
    val instantExecutor = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var getCharacterList: GetCharactersList

    @Mock
    lateinit var observer: Observer<Resource<List<Character>>>

    lateinit var viewModel: CharacterListViewModel

    @Before
    fun setUp() {
        viewModel = CharacterListViewModel(getCharacterList)
    }


    @Test
    fun loadCharacterListLoadingState() {
        var characterList = CharacterFactory.makeCharacterList(NUMBER_CHARACTERS)

        Mockito.`when`(getCharacterList.buildUseCase())
            .thenReturn(Single.just(characterList))
        viewModel.resource.observeForever(observer)

        viewModel.loadCharacterList()

        Mockito.verify(observer).onChanged(Resource(Status.LOADING))
    }


    @Test
    fun loadCharacterListSuccessState() {
        var characterList = CharacterFactory.makeCharacterList(NUMBER_CHARACTERS)

        Mockito.`when`(getCharacterList.buildUseCase(Mockito.anyBoolean()))
            .thenReturn(Single.just(characterList))

        viewModel.loadCharacterList()

        assert(viewModel.resource.value?.status == Status.SUCCESS)
    }


    @Test
    fun loadCharacterListFailedState() {
        Mockito.`when`(getCharacterList.buildUseCase(Mockito.anyBoolean()))
            .thenReturn(Single.error { throw RuntimeException() })

        viewModel.loadCharacterList()

        assert(viewModel.resource.value?.status == Status.FAILED)
    }


    @Test
    fun loadCharacterListReturnsCharacters() {
        var characterList = CharacterFactory.makeCharacterList(NUMBER_CHARACTERS)

        Mockito.`when`(getCharacterList.buildUseCase(Mockito.anyBoolean()))
            .thenReturn(Single.just(characterList))

        viewModel.loadCharacterList()

        assert(viewModel.resource.value?.data == characterList)
    }

    @Test
    fun loadCharacterListForcingFromRemoteSourceReturnsCharacters() {
        var characterList = CharacterFactory.makeCharacterList(NUMBER_CHARACTERS)

        Mockito.`when`(getCharacterList.buildUseCase(true))
            .thenReturn(Single.just(characterList))

        viewModel.loadCharacterList(true)

        assert(viewModel.resource.value?.data == characterList)
    }
}