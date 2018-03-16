package com.kay.core.ui

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView


/**
 * Created by Kay Tran on 11/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
abstract class DiffingAdapter<D, VH : RecyclerView.ViewHolder> constructor(diffItemCallback: DiffUtil.ItemCallback<D>) : RecyclerView.Adapter<VH>() {

    @Suppress("LeakingThis")
    private val listDiffer = ListDiffer(this, diffItemCallback)

    @Suppress("unused")
    fun submitList(list: List<D>) = listDiffer.submitList(list)

    protected fun getItem(position: Int): D {
        return listDiffer.getItem(position)
    }

    override fun getItemCount(): Int {
        return listDiffer.getItemCount()
    }
}