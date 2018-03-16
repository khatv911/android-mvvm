package kay.clonedcoinio.views

import android.graphics.Color
import android.support.v4.app.Fragment
import com.kay.core.error.UiResolver
import com.kay.core.utils.Retriable
import com.tapadoo.alerter.Alerter

/**
 * Created by Kay Tran on 16/3/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class FcsUiResolver constructor(private val fragment: Fragment) : UiResolver {
    override fun showSuccess(message: String?) {
        Alerter.create(fragment.requireActivity())
                .setTitle(message)
                .setText("Welcome to Cosmo!")
                .setDuration(2000L)
                .setBackgroundColorInt(Color.GREEN)
                .show()
    }

    override fun resolveErrorMessage(message: String, retryOption: Pair<Boolean, Int?>) {
        Alerter.create(fragment.requireActivity())
                .setTitle(message)
                .setText("Click to retry!")
                .setDuration(2000L)
                .setBackgroundColorInt(Color.RED)
                .setOnClickListener {
                    if (fragment is Retriable && retryOption.first) {
                        fragment.retry()
                    }
                }
                .show()
    }

    override fun resolveErrorMessage(resource: Int, retryOption: Pair<Boolean, Int?>) {
    }

    override fun showConnectivity(available: Boolean) {
    }

    override fun resolveNotFound(retryOption: Pair<Boolean, Int?>) {
    }

    override fun resolveUnAuthorized() {
    }

    override fun resolveForbidden() {
    }
}