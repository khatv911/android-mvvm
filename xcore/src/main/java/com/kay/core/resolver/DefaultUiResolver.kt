package com.kay.core.resolver

import android.support.v4.app.Fragment
import java.lang.ref.WeakReference

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
open class DefaultUiResolver constructor(protected val fragmentRef: WeakReference<Fragment>) : UiResolver {

    constructor(fragment: Fragment) : this(WeakReference(fragment))

    override fun resolveErrorMessage(message: String, retryOption: Pair<Boolean, Int?>) {}

    override fun resolveErrorMessage(resource: Int, retryOption: Pair<Boolean, Int?>) {}

    //NO-OP
    override fun showConnectivity(available: Boolean) {}

    // NO-OP
    override fun resolveNotFound(retryOption: Pair<Boolean, Int?>) {}

    override fun resolveUnAuthorized() {}

    //NO-OP
    override fun resolveForbidden() {}

    override fun showSuccess(message: String?) {}
}