package kay.clonedcoinio

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kay.core.ui.CanSetTitle
import timber.log.Timber

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class MainActivity : AppCompatActivity(), CanSetTitle {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        startSocket()

    }


    override fun onDestroy() {
        Timber.d("destroy act")
        super.onDestroy()
    }

    override fun setTitle(title: String?) {

    }
}
