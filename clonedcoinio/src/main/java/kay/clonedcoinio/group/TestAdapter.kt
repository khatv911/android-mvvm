package kay.clonedcoinio.group

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kay.clonedcoinio.R
import kotlinx.android.synthetic.main.header_item_expandable.view.*
import kotlinx.android.synthetic.main.view_item_text_1.view.*


/**
 * Created by Kay Tran on 24/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class TestAdapter : GroupAdapter<ViewHolder>() {
    fun addData(data: List<GroupData>) {
        add(xSection {
            header {
                title = "List Header"
                subtitle = "Test"
                icon = R.drawable.ic_expand_more
            }
            footer {
                "List footer"
            }
            data.asSequence().forEach { groupData ->
                section {
                    header {
                        title = groupData.name
                    }
                    footer {
                         "${groupData.name} end"
                    }
                    groupData.rooms.asSequence().forEach { roomData ->
                        //                        val roomExpandable = ExpandableHeaderItem(roomData.name, roomData.jobs.count())
                        expandable {
                            // expanded by default
                            isExpanded { true }
                            // header should look like this
                            header {
                                title = roomData.name
                                subtitle = "%d".format(roomData.jobs.count())
                            }
                            roomData.jobs.asSequence().forEach { jobData ->

                                item {
                                    layoutId = R.layout.view_item_text_1
                                    binder = { position ->
                                        with(itemView) {
                                            tv_item_name.text = jobData.name
                                            editText.text = jobData.id
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        )
    }
}