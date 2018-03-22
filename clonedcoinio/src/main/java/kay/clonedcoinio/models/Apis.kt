package kay.clonedcoinio.models

import kay.clonedcoinio.models.entities.Coin
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
interface Apis {

    @GET("http://coincap.io/front")
    fun getCoins(): Call<List<Coin>>


}