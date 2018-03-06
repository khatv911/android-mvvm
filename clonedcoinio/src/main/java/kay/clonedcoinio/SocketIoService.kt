package kay.clonedcoinio

import android.content.Context
import android.content.Intent
import android.support.v4.app.JobIntentService
import dagger.android.AndroidInjection
import io.socket.client.IO
import kay.clonedcoinio.models.AppDatabase
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Kay Tran on 2/2/18.
 * Profile: https://github.com/khatv911
 * Email: khatv911@gmail.com
 */
class SocketIoService : JobIntentService() {

    @Inject
    lateinit var listener: CustomListener

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        SocketInstance.mListener = listener
    }

    companion object {
        private const val START_LISTENING = "start"
        private const val STOP_LISTENING = "stop"
        private const val JOB_ID = 1000


        fun connect(context: Context) {
            enqueueWork(context, SocketIoService::class.java, JOB_ID, Intent().apply {
                action = SocketIoService.START_LISTENING
            })
        }

        fun disconnect(context: Context) {
            enqueueWork(context, SocketIoService::class.java, JOB_ID, Intent().apply {
                action = SocketIoService.STOP_LISTENING
            })
        }

    }


    override fun onHandleWork(intent: Intent) {
        val action = intent.action

        when (action) {
            START_LISTENING -> {
                SocketInstance.socket.connect()
            }
            STOP_LISTENING -> {
                SocketInstance.socket.disconnect()
                SocketInstance.socket.off()
            }
        }
    }
}