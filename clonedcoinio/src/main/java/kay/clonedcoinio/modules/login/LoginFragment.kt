package kay.clonedcoinio.modules.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.kay.core.resolver.DefaultResolution
import com.kay.core.resolver.DefaultUiResolver
import com.kay.core.resolver.Resolution
import com.kay.core.simple.SimpleFragment
import com.kay.core.utils.Retriable
import com.kay.core.utils.inject
import com.tapadoo.alerter.Alerter
import kay.clonedcoinio.R
import kay.clonedcoinio.modules.coin.CoinActivity
import kay.clonedcoinio.resolver.FcsUiResolver
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

/**
 * Created by Kay Tran on 16/3/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
/**
 * Login Screen doesn't need to display any data, all it cares is login response(success or failed)
 */
class LoginFragment : SimpleFragment<LoginViewModel>(), Retriable {

    override fun getViewModel() = VIEW_MODEL_FACTORY.inject(this, LoginViewModel::class.java)

    /**
     * Composition over inheritance
     */
    override fun getResolution(): Resolution {
        return DefaultResolution(mutableListOf(FcsUiResolver(this)))
                .apply { addResolver(LoginUiResolver(this@LoginFragment)) }
    }


//   override fun onSuccess(message: String?) {
//        super.onSuccess(message)
//
//        launch(UI) {
//            delay(3, TimeUnit.SECONDS)
//            startActivity(Intent(this@LoginFragment.context, CoinActivity::class.java))
//        }
//    }

    internal inner class LoginUiResolver(fragment: Fragment) : DefaultUiResolver(fragment) {
        override fun showSuccess(message: String?) {
            val fragment = fragmentRef.get()
            fragment?.let {
                try {
                    Alerter.create(it.requireActivity())
                            .setText("Welcome to Cosmo")
                            .setDuration(1000L)
                            .setBackgroundColorInt(Color.CYAN)
                            .show()
                    launch(UI) {
                        delay(2, TimeUnit.SECONDS)
                        startActivity(Intent(it.context, CoinActivity::class.java))
                        it.activity?.finish()
                    }
                } catch (e: Throwable) {

                }

            }
        }
    }


    override fun retry() {
        mViewModel.retry()
    }

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun getActionBarTitle(): String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {
            val userName = edit_username.text?.trim().toString()
            val password = edit_password.text?.trim().toString()
            mViewModel.login(userName, password)
        }
    }

}