package com.kay.core.extension

import android.support.annotation.AnimRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity


/**
 * Created by KhaTran on 29/11/17.
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction()
            .func()
            .commit()
}

fun FragmentTransaction.extends(tag: String,
                                shouldAddToBackStack: Boolean = true,
                                @AnimRes enterAnimation: Int = android.R.anim.fade_in,
                                @AnimRes exitAnimation: Int = android.R.anim.fade_out,
                                @AnimRes popEnterAnimation: Int =android.R.anim.fade_in,
                                @AnimRes popExitAnimation: Int = android.R.anim.fade_out): FragmentTransaction =
        addToBackStack(if (shouldAddToBackStack) tag else null).setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, tag: String, shouldAddToBackStack: Boolean = true) {
    supportFragmentManager.inTransaction {
        add(frameId, fragment).extends(tag, shouldAddToBackStack)
    }
}


fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, tag: String, shouldAddToBackStack: Boolean = true) {
    supportFragmentManager.inTransaction { replace(frameId, fragment).extends(tag, shouldAddToBackStack) }
}

fun AppCompatActivity.replaceToRoot(fragment: Fragment, frameId: Int, tag: String) {
    supportFragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    supportFragmentManager.inTransaction { replace(frameId, fragment).extends(tag) }
}

fun AppCompatActivity.popFragment() {
    supportFragmentManager.popBackStack()
}


fun Fragment.addFragment(fragment: Fragment, frameId: Int, tag: String, shouldAddToBackStack: Boolean = true) {
    fragmentManager?.inTransaction {
        add(frameId, fragment).extends(tag, shouldAddToBackStack)
    }
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int, tag: String, shouldAddToBackStack: Boolean = true) {
    fragmentManager?.inTransaction { replace(frameId, fragment).extends(tag, shouldAddToBackStack) }
}



