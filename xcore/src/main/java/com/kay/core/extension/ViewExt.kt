package com.kay.core.extension

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */


fun View.visible() {
    if (this.visibility != View.VISIBLE) this.visibility = View.VISIBLE
}

fun View.invisible() {
    if (this.visibility != View.INVISIBLE) this.visibility = View.INVISIBLE
}

fun View.gone() {
    if (this.visibility != View.GONE) this.visibility = View.GONE
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.isInvisible() = visibility == View.INVISIBLE

fun View.isGone() = visibility == View.GONE

fun View.visibleIf(prediction: Boolean, otherwise: View.() -> Unit?) {
    if (prediction) visible() else otherwise()
}


fun View.invisibleIf(prediction: Boolean, otherwise: View.() -> Unit?) {
    if (prediction) invisible() else otherwise()
}

fun View.goneIf(prediction: Boolean, otherwise: View.() -> Unit?) {
    if (prediction) gone() else otherwise()
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}