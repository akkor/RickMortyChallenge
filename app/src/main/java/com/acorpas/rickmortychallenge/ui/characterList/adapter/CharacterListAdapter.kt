package com.acorpas.rickmortychallenge.ui.characterList.adapter

import android.view.ViewGroup
import com.acorpas.rickmortychallenge.ui.base.adapter.RecyclerViewAdapterBase
import com.acorpas.rickmortychallenge.ui.base.item.ItemView
import com.acorpas.rickmortychallenge.domain.model.Character
import com.acorpas.rickmortychallenge.ui.Characterslist.item.CharacterItemView
import com.acorpas.rickmortychallenge.ui.base.adapter.AdapterViewHolder
import javax.inject.Inject


class CharacterListAdapter @Inject constructor(
    private val itemClick: (Character) -> Unit
    ): RecyclerViewAdapterBase<Character, ItemView<Character>>() {


    override fun onCreateItemView(parent: ViewGroup, viewType: Int): ItemView<Character> {
        return CharacterItemView(parent.context)
            .apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            }
    }

    override fun onBindViewHolder(holder: AdapterViewHolder<ItemView<Character>>, position: Int) {
        val character = items[position]

        holder.view.apply {
            bind(character)
        }

        holder.view.setOnClickListener { itemClick(character) }
    }


}