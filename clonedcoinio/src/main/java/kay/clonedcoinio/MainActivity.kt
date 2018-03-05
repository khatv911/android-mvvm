package kay.clonedcoinio

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kay.core.error.Resolution
import com.kay.core.error.UiResolver
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
