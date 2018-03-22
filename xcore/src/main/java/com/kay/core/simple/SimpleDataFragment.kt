package com.kay.core.simple

import android.os.Bundle
import android.view.View
import com.kay.core.utils.observeK
import com.kay.core.viewmodel.plusAssign

/**
 * Created by Kay Tran on 14/3/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
abstract class SimpleDataFragment<T, VM : SimpleViewModel<T>> : SimpleFragment<VM>() {

    abstract fun onDataChanged(t: T)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel += mViewModel.mLiveData.observeK(this@SimpleDataFragment) {
            it?.let { onDataChanged(it) }
        }

    }

}