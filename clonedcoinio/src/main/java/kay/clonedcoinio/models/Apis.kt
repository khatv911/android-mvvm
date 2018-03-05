package kay.clonedcoinio.models

import kay.clonedcoinio.models.entities.Coin
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by none on 10/2/18.
 */
interface Apis {

    @GET("/front")
    fun getCoins(): Call<List<Coin>>


}