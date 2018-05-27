package kay.clonedcoinio.group


/**
 * Created by Kay Tran on 23/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
object DataProvider {

    fun getData(): List<GroupData> {
        val jobs_101 = arrayListOf<JobData>().apply {
            add(JobData("job 1 at room 1 at group 1", "g1_r1_j1"))
            add(JobData("job 2 at room 1 at group 1", "g1_r1_j2"))
            add(JobData("job 3 at room 1 at group 1", "g1_r1_j3"))
            add(JobData("job 4 at room 1 at group 1", "g1_r1_j4"))
        }

        val jobs_102 = arrayListOf<JobData>().apply {
            add(JobData("job 1 at room 2 at group 1", "g1_r2_j1"))
            add(JobData("job 2 at room 2 at group 1", "g1_r2_j2"))
            add(JobData("job 3 at room 2 at group 1", "g1_r2_j3"))
        }


        val jobs_103 = arrayListOf<JobData>().apply {
            add(JobData("job 1 at room 3 at group 1", "g1_r3_j1"))
            add(JobData("job 2 at room 3 at group 1", "g1_r3_j2"))
            add(JobData("job 3 at room 3 at group 1", "g1_r3_j3"))
            add(JobData("job 4 at room 3 at group 1", "g1_r3_j4"))
            add(JobData("job 5 at room 3 at group 1", "g1_r3_j5"))
            add(JobData("job 6 at room 3 at group 1", "g1_r3_j6"))
            add(JobData("job 7 at room 3 at group 1", "g1_r3_j7"))
            add(JobData("job 8 at room 3 at group 1", "g1_r3_j8"))
        }
        val room101 = RoomData("Room 101", "g1_r1", jobs_101)

        val room102 = RoomData("Room 102", "g1_r2", jobs_102)

        val room103 = RoomData("Room 103", "g1_r3", jobs_103)


        val group1 = GroupData("Group 1", "g1", arrayListOf<RoomData>().apply {
            add(room101)
            add(room102)
            add(room103)
        })


        val jobs_201 = arrayListOf<JobData>().apply {
            add(JobData("job 1 at room 1 at group 2", "g2_r1_j1"))
            add(JobData("job 2 at room 1 at group 2", "g2_r1_j2"))
        }

        val jobs_202 = arrayListOf<JobData>().apply {
            add(JobData("job 1 at room 2 at group 2", "g2_r2_j1"))
            add(JobData("job 2 at room 2 at group 2", "g2_r2_j2"))
            add(JobData("job 3 at room 2 at group 2", "g2_r2_j3"))
            add(JobData("job 3 at room 2 at group 2", "g2_r2_j4"))
        }
        val room201 = RoomData("Room 201", "g2_r1", jobs_201)

        val room202 = RoomData("Room 202", "g2_r2", jobs_202)


        val group2 = GroupData("Group 2", "g2", arrayListOf<RoomData>().apply {
            add(room201)
            add(room202)
        })


        return arrayListOf<GroupData>().apply {
            add(group1)
            add(group2)
        }
    }
}