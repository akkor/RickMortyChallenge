package com.acorpas.rickmortychallenge.ui.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

abstract class RecyclerViewAdapterBase<T, V : View> : RecyclerView.Adapter<AdapterViewHolder<V>>() {

    val items = emptyList<T>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder<V> {
        return AdapterViewHolder(onCreateItemView(parent, viewType))
    }

    override fun getItemCount() = items.size

    abstract fun onCreateItemView(parent: ViewGroup, viewType: Int): V

    open fun setItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}