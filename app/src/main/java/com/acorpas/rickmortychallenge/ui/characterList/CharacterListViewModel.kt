package com.acorpas.rickmortychallenge.ui.characterList

import android.arch.lifecycle.MutableLiveData
import com.acorpas.rickmortychallenge.domain.model.Character
import com.acorpas.rickmortychallenge.domain.model.Resource
import com.acorpas.rickmortychallenge.domain.usecases.base.GetCharactersList
import com.acorpas.rickmortychallenge.extension.logd
import com.acorpas.rickmortychallenge.extension.loge
import com.acorpas.rickmortychallenge.ui.base.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharacterListViewModel @Inject constructor(
    private val getCharactersList: GetCharactersList
) : BaseViewModel() {

    val resource = MutableLiveData<Resource<List<Character>>>()

    fun loadCharacterList(forceFromRemoteSource: Boolean = false) {
        resource.value = Resource.loading()
        disposables += getCharactersList.buildUseCase(forceFromRemoteSource)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    logd("Load character detail Success")
                    resource.value = Resource.success(it)
                },
                onError = {
                    loge("Load Character detail error", it)
                    resource.value = Resource.error(it)
                }
            )
    }

}