package kay.clonedcoinio.group


/**
 * Created by Kay Tran on 23/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
sealed class BaseData

data class JobData(var name: String, var id:String): BaseData()

data class RoomData(var name:String, var id: String, var jobs:List<JobData>): BaseData()

data class GroupData(var name: String, var id: String, var rooms: List<RoomData>): BaseData()


