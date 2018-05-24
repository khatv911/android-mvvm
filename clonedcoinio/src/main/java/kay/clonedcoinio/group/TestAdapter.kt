package kay.clonedcoinio.group

import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder


/**
 * Created by Kay Tran on 24/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class TestAdapter : GroupAdapter<ViewHolder>() {

    fun addData(data: List<GroupData>) {
        val section = Section()
        data.asSequence().forEach { groupData ->
            val groupSection = Section(HeaderXItem(groupData.name)).apply {
                groupData.rooms.asSequence().forEach { roomData ->
                    val roomExpandable = ExpandableHeaderItem(roomData.name, roomData.jobs.count())
                    val jobsByRoom = ExpandableGroup(roomExpandable).apply {
                        roomData.jobs.asSequence().forEach { jobData ->
                            this.add(TextItem(jobData.name, jobData.id))
                        }
                    }
                    this.add(jobsByRoom)
                }
            }
            add(groupSection)
        }


        add(section)
    }
}