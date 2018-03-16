package com.kay.core.ui

import android.support.v7.util.AdapterListUpdateCallback
import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback
import android.support.v7.widget.RecyclerView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch


/**
 * Created by Kay Tran on 15/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class ListDiffer<D> constructor(private val mUpdateCallback: ListUpdateCallback, private val diffItemCallback: DiffUtil.ItemCallback<D>) {

    constructor(adapter: RecyclerView.Adapter<*>, itemCallback: DiffUtil.ItemCallback<D>) : this(AdapterListUpdateCallback(adapter), itemCallback)

    var onEmptyListCallback: (() -> Any)? = null

    private var mData: List<D> = emptyList()

    private val diffCallback by lazy(LazyThreadSafetyMode.NONE) { DiffCallback(diffItemCallback) }

    private val eventActor = actor<List<D>>(capacity = Channel.CONFLATED) { for (list in channel) internalUpdate(list) }

    fun submitList(list: List<D>) = eventActor.offer(list)

    fun getItemCount() = mData.size

    fun getItem(position: Int): D = mData[position]

    private suspend fun internalUpdate(list: List<D>) {
        val result = DiffUtil.calculateDiff(diffCallback.apply { newList = list }, false)
        launch(UI) {
            result.dispatchUpdatesTo(mUpdateCallback)
            mData = list
            if (mData.isEmpty()) onEmptyListCallback?.invoke()
        }.join()
    }

    private inner class DiffCallback(private val diffItemCallback: DiffUtil.ItemCallback<D>) : DiffUtil.Callback() {
        lateinit var newList: List<D>
        override fun getOldListSize() = mData.size
        override fun getNewListSize() = newList.size
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = diffItemCallback.areContentsTheSame(mData[oldItemPosition], newList[newItemPosition])
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = diffItemCallback.areItemsTheSame(mData[oldItemPosition], newList[newItemPosition])
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? = diffItemCallback.getChangePayload(mData[oldItemPosition], newList[newItemPosition])
    }


}