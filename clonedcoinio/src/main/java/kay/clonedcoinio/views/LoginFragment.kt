package kay.clonedcoinio.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.kay.core.error.DefaultResolution
import com.kay.core.error.Resolution
import com.kay.core.error.UiResolver
import com.kay.core.extension.inject
import com.kay.core.ui.AbsBaseFragment
import com.kay.core.utils.Retriable
import kay.clonedcoinio.CoinActivity
import kay.clonedcoinio.R
import kay.clonedcoinio.viewmodels.LoginViewModel
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
class LoginFragment : AbsBaseFragment(), Retriable {

    private val mViewModel by lazy {
        VIEW_MODEL_FACTORY.inject(this, LoginViewModel::class.java).apply {
            setup(this@LoginFragment)
        }
    }

    private val loginResolver: UiResolver by lazy {
        FcsUiResolver(this)
    }

    // lazy function -> move this to another 'BaseFcsFragment' maybe.
    override fun getResolution(): Resolution? {
        return DefaultResolution(
                defaultUiResolvers.apply {
                    clear()
                    add(loginResolver)
                }
        )
    }

    override fun retry() {
        mViewModel.retry()
    }

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun getActionBarTitle(): String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {
            val userName = edit_username.text.trim().toString()
            val password = edit_password.text.trim().toString()
            mViewModel.login(userName, password)
        }
    }

    override fun onSuccess(message: String?) {
        super.onSuccess(message)

        launch(UI) {
            delay(3, TimeUnit.SECONDS)
            startActivity(Intent(this@LoginFragment.context, CoinActivity::class.java))
        }
    }
}