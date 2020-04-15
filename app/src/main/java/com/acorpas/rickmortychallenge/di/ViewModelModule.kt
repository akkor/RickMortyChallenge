package com.acorpas.rickmortychallenge.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.acorpas.rickmortychallenge.ui.base.viewmodel.ViewModelFactory
import com.acorpas.rickmortychallenge.ui.characterDetail.CharacterDetailVewModel
import com.acorpas.rickmortychallenge.ui.characterList.CharacterListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CharacterListViewModel::class)
    abstract fun bindCharacterListViewModel(viewModel: CharacterListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailVewModel::class)
    abstract fun bindCharacterDetailViewModel(viewModel: CharacterDetailVewModel): ViewModel

}