package com.kay.core.error

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import com.kay.core.R
import com.kay.core.utils.Retriable

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class DefaultUiResolver constructor(private val fragment: Fragment) : UiResolver {
    override fun resolveErrorMessage(message: String, retryOption: Pair<Boolean, Int?>) {
        fragment.view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_INDEFINITE).apply {
                if (fragment is Retriable && retryOption.first) {
                    setAction(retryOption.second ?: R.string.retry, {
                        dismiss()
                        fragment.retry()
                    })
                } else {
                    setAction(R.string.dismiss, {
                        dismiss()
                    })
                }
            }.show()
        }
    }

    override fun resolveErrorMessage(resource: Int, retryOption: Pair<Boolean, Int?>) {
        fragment.view?.let {
            Snackbar.make(it, resource, Snackbar.LENGTH_INDEFINITE).apply {
                if (fragment is Retriable && retryOption.first) {
                    setAction(retryOption.second ?: R.string.retry, {
                        dismiss()
                        fragment.retry()
                    })
                } else {
                    setAction(R.string.dismiss, {
                        dismiss()
                    })
                }
            }.show()
        }
    }

    //NO-OP
    override fun showConnectivity(available: Boolean) {}

    // NO-OP
    override fun resolveNotFound(retryOption: Pair<Boolean, Int?>) {}

    override fun resolveUnAuthorized(){}

    //NO-OP
    override fun resolveForbidden() {}
}