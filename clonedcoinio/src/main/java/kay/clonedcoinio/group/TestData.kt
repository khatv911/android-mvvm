package kay.clonedcoinio.group


/**
 * Created by Kay Tran on 23/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
sealed class BaseData(open val id: String)

data class JobData(var name: String, override val id: String) : BaseData(id)

data class RoomData(var name: String, override val id: String, var jobs: List<JobData>) : BaseData(id)

data class GroupData(var name: String, override var id: String, var rooms: List<RoomData>) : BaseData(id)


