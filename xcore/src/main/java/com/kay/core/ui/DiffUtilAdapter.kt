package com.kay.core.ui

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch


/**
 * Created by Kay Tran on 11/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
abstract class DiffUtilAdapter<D, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {


    var compareItemsFunc: (o: D, n: D) -> Boolean = { _, _ -> false }

    var compareContentsFunc: (o: D, n: D) -> Boolean = { o, n -> o == n }

    var getPayloadFunc: (o: D, n: D) -> Any? = { _, _ -> null }

    var onEmptyListCallback: (() -> Any)? = null

    protected var mData: List<D> = listOf()

    override fun getItemCount() = mData.size

    protected fun getItem(position: Int): D? = mData.getOrNull(position)

    private val diffCallback by lazy(LazyThreadSafetyMode.NONE) { DiffCallback() }

    private val eventActor = actor<List<D>>(capacity = Channel.CONFLATED) { for (list in channel) internalUpdate(list) }

    fun update(list: List<D>) = eventActor.offer(list)

    private suspend fun internalUpdate(list: List<D>) {
        val result = DiffUtil.calculateDiff(diffCallback.apply { newList = list }, false)
        launch(UI) {

            result.dispatchUpdatesTo(this@DiffUtilAdapter)
            onEmptyListCallback?.invoke()
            mData = list
        }.join()
    }

    private inner class DiffCallback : DiffUtil.Callback() {
        lateinit var newList: List<D>
        override fun getOldListSize() = mData.size
        override fun getNewListSize() = newList.size
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = compareContentsFunc(mData[oldItemPosition], newList[newItemPosition])
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = compareItemsFunc(mData[oldItemPosition], newList[newItemPosition])
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? = getPayloadFunc(mData[oldItemPosition], newList[newItemPosition])
    }
}