package kay.clonedcoinio.resolver

import android.graphics.Color
import android.support.v4.app.Fragment
import com.kay.core.resolver.DefaultUiResolver
import com.kay.core.utils.Retriable
import com.tapadoo.alerter.Alerter

/**
 * Created by Kay Tran on 16/3/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class FcsUiResolver(fragment: Fragment) : DefaultUiResolver(fragment) {

    override fun resolveErrorMessage(message: String, retryOption: Pair<Boolean, Int?>) {
        val fragment = fragmentRef.get()
        fragment?.let {
            Alerter.create(it.requireActivity())
                    .setTitle(message)
                    .setText("Click to retryEvent!")
                    .setDuration(2000L)
                    .setBackgroundColorInt(Color.RED)
                    .setOnClickListener {
                        if (fragment is Retriable && retryOption.first) {
                            fragment.retry()
                        }
                    }
                    .show()
        }
    }

}