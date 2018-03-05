package com.kay.core.error

import com.kay.core.R
import javax.inject.Inject

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
        resolvers.add(uiResolver)
    }

    override fun onGenericException(throwable: Throwable) {

        resolvers.map {
            it.resolveErrorMessage(throwable.message
                    ?: "Unknown error", retryOption = true to R.string.retry)
        }
    }

    override fun onConnectivityAvailable() {
        resolvers.map { it.showConnectivity(true) }
    }

    override fun onConnectivityUnavailable() {
        resolvers.map { it.showConnectivity(false) }
    }

    override fun onUnavailableService() {
        resolvers.map {
            it.resolveErrorMessage(R.string.message_error_service_unavailable, retryOption = true to R.string.retry)
        }
    }

    override fun onInternalServerError() {
        resolvers.map {
            it.resolveErrorMessage(R.string.message_error_internal_server_error, retryOption = true to R.string.retry)
        }
    }

    override fun onClientError() {
        resolvers.map {
            it.resolveErrorMessage(R.string.message_error_client_error)
        }
    }

    override fun onNotFound() {

    }

    override fun onUnAuthorized() {

    }

    override fun onForbidden() {
    }
}