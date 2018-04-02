package com.kay.core.resolver

import com.kay.core.R

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class DefaultResolution constructor(private val resolvers: MutableList<UiResolver>) : AbsResolution() {


    /**
     * add more resolver on the fly
     */
    fun addResolver(uiResolver: UiResolver) {
        resolvers += uiResolver
    }

    override fun onGenericException(throwable: Throwable) {

        resolvers.forEach {
            it.resolveErrorMessage(throwable.message
                    ?: "Unknown error", retryOption = true to R.string.retry)
        }
    }

    override fun onConnectivityAvailable() {
        resolvers.forEach { it.showConnectivity(true) }
    }

    override fun onConnectivityUnavailable() {
        resolvers.forEach { it.showConnectivity(false) }
    }

    override fun onUnavailableService() {
        resolvers.forEach {
            it.resolveErrorMessage(R.string.message_error_service_unavailable, retryOption = true to R.string.retry)
        }
    }

    override fun onInternalServerError() {
        resolvers.forEach {
            it.resolveErrorMessage(R.string.message_error_internal_server_error, retryOption = true to R.string.retry)
        }
    }

    override fun onClientError() {
        resolvers.forEach {
            it.resolveErrorMessage(R.string.message_error_client_error)
        }
    }

    override fun onNotFound() {
        resolvers.forEach {
            it.resolveNotFound()
        }
    }

    override fun onUnAuthorized() {
        resolvers.forEach {
            it.resolveUnAuthorized()
        }
    }

    override fun onForbidden() {
        resolvers.forEach {
            it.resolveForbidden()
        }
    }

    override fun success(message: String?) {
        resolvers.forEach {
            it.showSuccess(message)
        }
    }
}