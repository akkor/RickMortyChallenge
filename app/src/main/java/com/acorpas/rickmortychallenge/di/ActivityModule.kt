package com.acorpas.rickmortychallenge.di


import com.acorpas.rickmortychallenge.ui.characterDetail.CharacterDetailActivity
import com.acorpas.rickmortychallenge.ui.characterList.MainActivity
import com.acorpas.rickmortychallenge.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [(ViewModelModule::class)])
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeSplashActivity(): SplashActivity


    @ContributesAndroidInjector
    internal abstract fun contributeCharacterListActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeCharacterDetailActivity(): CharacterDetailActivity
}