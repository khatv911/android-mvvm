package com.kay.core.ui

import android.os.Bundle
import com.kay.core.utils.CanSetTitle
import dagger.android.support.DaggerFragment


/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */

abstract class AbsBaseFragment : DaggerFragment() {



    abstract fun getLayoutId(): Int

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            parseArguments(it)
        }
    }

    /**
     * Parse arguments, normally
     */
    protected open fun parseArguments(arguments: Bundle) {}

    /**
     *
     */
    abstract fun getActionBarTitle(): String

    override fun onResume() {
        super.onResume()
        if (activity is CanSetTitle) {
            (activity as CanSetTitle).setActionBarTitle(getActionBarTitle())
        }
    }
}