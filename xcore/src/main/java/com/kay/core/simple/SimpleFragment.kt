package com.kay.core.simple

import android.arch.lifecycle.Observer
import com.kay.core.ui.AbsBaseFragment

/**
 * Created by Kay Tran on 14/3/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
abstract class SimpleFragment<T, out VM : SimpleViewModel<T>> : AbsBaseFragment() {

    protected val mViewModel by lazy {
        getViewModel().apply {
            mLiveData.observe(this@SimpleFragment, Observer {
                it?.run { onDataChanged(it) }
            })
        }
    }

    abstract fun getViewModel(): VM

    abstract fun onDataChanged(t: T)

}