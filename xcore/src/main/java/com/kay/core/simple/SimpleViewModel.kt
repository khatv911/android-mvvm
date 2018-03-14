package com.kay.core.simple

import android.arch.lifecycle.LiveData
import com.kay.core.viewmodel.AbsBaseViewModel


/**
 * Created by Kay Tran on 13/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
abstract class SimpleViewModel<T> : AbsBaseViewModel() {
    lateinit var mLiveData: LiveData<T?>
}