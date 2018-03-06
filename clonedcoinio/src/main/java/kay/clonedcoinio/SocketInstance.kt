package kay.clonedcoinio

import io.socket.client.IO
import io.socket.client.Socket
import kay.clonedcoinio.models.AppDatabase
import javax.inject.Inject


/**
 * Created by Kay Tran on 6/3/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
object SocketInstance {

    var mListener: CustomListener? = null

    val socket: Socket by lazy {
        IO.socket("https://coincap.io").apply {
            on("trades", mListener)
        }
    }
}