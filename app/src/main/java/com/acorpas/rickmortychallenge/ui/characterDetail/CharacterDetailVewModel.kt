package com.acorpas.rickmortychallenge.ui.characterDetail

import android.arch.lifecycle.MutableLiveData
import com.acorpas.rickmortychallenge.domain.model.Character
import com.acorpas.rickmortychallenge.domain.model.Resource
import com.acorpas.rickmortychallenge.domain.usecases.GetCharacter
import com.acorpas.rickmortychallenge.domain.usecases.base.GetCharactersList
import com.acorpas.rickmortychallenge.extension.logd
import com.acorpas.rickmortychallenge.extension.loge
import com.acorpas.rickmortychallenge.ui.base.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import com.squareup.picasso.Picasso
import android.databinding.BindingAdapter
import android.text.TextUtils
import android.widget.ImageView





class CharacterDetailVewModel @Inject constructor(
    private val getCharacter: GetCharacter
) : BaseViewModel() {

    object ImageViewBindingAdapter {
        @BindingAdapter("bind:imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, url: String?) {
            if (!TextUtils.isEmpty(url))
                Picasso.get().load(url).into(view)
        }
    }

    val resource = MutableLiveData<Resource<Character>>()
    lateinit var character : Character

    fun loadCharacterDetail(idCharacter: Int) {
        resource.value = Resource.loading()
        disposables += getCharacter.buildUseCase(idCharacter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    logd("Load character Success")
                    character = it
                    resource.value = Resource.success(it)
                },
                onError = {
                    loge("Load Characters error", it)
                    resource.value = Resource.error(it)
                }
            )
    }


}