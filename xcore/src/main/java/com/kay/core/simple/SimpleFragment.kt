package com.kay.core.simple

import android.os.Bundle
import android.view.View
import com.kay.core.ui.AbsBaseFragment
import com.kay.core.viewmodel.AbsBaseViewModel

/**
 * Created by Kay Tran on 22/3/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
abstract class SimpleFragment<VM : AbsBaseViewModel> : AbsBaseFragment() {

    protected lateinit var mViewModel: VM

    abstract fun getViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.setup(this)
    }

    override fun onDestroyView() {
        mViewModel.tearDown(this)
        super.onDestroyView()
    }
}