package com.acorpas.rickmortychallenge.ui.Characterslist.item

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.acorpas.rickmortychallenge.R
import com.acorpas.rickmortychallenge.domain.model.Character
import com.acorpas.rickmortychallenge.ui.base.item.ItemView
import com.acorpas.rickmortychallenge.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_item_character.view.*
import org.jetbrains.anko.dimen

class CharacterItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ItemView<Character>(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_item_character, this, true)
        useCompatPadding = true
        radius = context.dimen(R.dimen.character_item_radius).toFloat()
        cardElevation = context.dimen(R.dimen.character_item_elevation).toFloat()
    }

    override fun bind(item: Character) {
        titleTextView.text = item.name

        Picasso
            .get()
            .load(item.image)
            .into(characterImageView)

    }
}