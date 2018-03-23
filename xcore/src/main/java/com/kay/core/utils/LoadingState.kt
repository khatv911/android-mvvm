package com.kay.core.utils

import android.support.annotation.IntDef


/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class LoadingState {
    companion object {
        const val NONE = 1
        const val NORMAL = 2
        const val REFRESHING = 3
        const val MORE = 4
        const val SEARCHING = 5
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(NONE, NORMAL, REFRESHING, MORE, SEARCHING)
    annotation class Value
}

