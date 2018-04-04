package kay.clonedcoinio

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kay.coin.view.CoinActivity

/**
 * Created by Kay Tran on 3/4/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity( Intent(this, CoinActivity::class.java))
        finish()
    }


}