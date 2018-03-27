package com.kay.core.ui

import android.support.v7.util.AdapterListUpdateCallback
import android.support.v7.util.ListUpdateCallback


/**
 * Created by Kay Tran on 25/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class ItemInsertedAwareCallback(private val adapterListUpdateCallback: AdapterListUpdateCallback,
                                private val onItemInsertedCallback: OnItemInsertedCallback) : ListUpdateCallback {

    private var firstInsert = -1

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        adapterListUpdateCallback.onChanged(position, count, payload)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        adapterListUpdateCallback.onMoved(fromPosition, toPosition)
    }

    override fun onInserted(position: Int, count: Int) {

        adapterListUpdateCallback.onInserted(position, count)

        if (firstInsert == -1 || firstInsert > position) {
            firstInsert = position;
            onItemInsertedCallback.onItemInserted(firstInsert)
        }
    }

    override fun onRemoved(position: Int, count: Int) {
        adapterListUpdateCallback.onRemoved(position, count)
    }
}