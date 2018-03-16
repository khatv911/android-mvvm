package kay.clonedcoinio.models.repositories

import android.content.SharedPreferences
import com.kay.core.extension.PrefHelper.set
import com.kay.core.livedata.BaseRepository
import com.kay.core.network.RequestState
import com.squareup.moshi.Json
import kay.clonedcoinio.models.entities.FcsResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import ru.gildor.coroutines.retrofit.awaitResult
import ru.gildor.coroutines.retrofit.getOrThrow
import javax.inject.Inject


/**
 * Created by Kay Tran on 16/3/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */


typealias LoginResult = FcsResponse<LoginRepository.LoginResponseWrapper>

class LoginRepository @Inject constructor(private val api: Retrofit, private val preferences: SharedPreferences) : BaseRepository() {


    interface WS {

        @POST("/cosmo-service/api/web/v1/auth/login")
        fun login(@Body request: LoginRequest): Call<LoginResult>
    }


    data class LoginRequest(@Json(name = "username") val username: String, @Json(name = "password") val password: String)
    data class LoginResponseWrapper(@Json(name = "response") val response: LoginResponse? = null, @Json(name = "status") val status: Int? = null)
    data class LoginResponse(@Json(name = "access_token") val accessToken: String)

    private val loginWS = api.create(WS::class.java)

    fun login(pair: Pair<String, String>) {
        launch(UI + withRetryExceptionHandler { login(pair) }) {
            val result = loginWS.login(LoginRequest(pair.first, pair.second)).awaitResult()
            val response = result.getOrThrow()
            process(response)

        }
    }

    private fun process(result: LoginResult) {
        if (result.data?.response == null || result.data?.status == 401) {
            throw Throwable(result.msg)
        }
        // save to prefs
        preferences["access_token"] = result.data?.response?.accessToken ?: ""

        state.value = RequestState.SUCCESS("Login Success")
    }


}