package com.kay.core.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.kay.core.viewmodel.ViewModelFactory

/**
 * Created by KhaTran on 2/2/18.
 */


@Suppress("UNCHECKED_CAST")
fun <T : ViewModel> ViewModelFactory.inject(fragment: Fragment,_class: Class<T>): T
        = ViewModelProviders.of(fragment,this ).get(_class)

@Suppress("UNCHECKED_CAST")
fun <T : ViewModel> ViewModelFactory.inject(fragmentActivity: FragmentActivity,_class: Class<T>): T
        = ViewModelProviders.of(fragmentActivity, this).get(_class)