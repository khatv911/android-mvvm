package kay.clonedcoinio.group.x

import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import kay.clonedcoinio.group.ExpandableHeaderItem


/**
 * Created by Kay Tran on 27/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class ExpandableBuilder {

    private var header: ExpandableHeaderItem? = null

    private var isExpanded = false

    private val children = mutableListOf<Group>()

    fun isExpanded(block: () -> Boolean) {
        isExpanded = block.invoke()
    }

    fun header(block: TBinder<XHeaderData>) {
        header = ExpandableHeaderItem().apply { xHeaderData = XHeaderData().apply(block) }
    }

    fun item(block: TBinder<ItemBuilder>) = children.add(xItem(block))


    fun build() = ExpandableGroup(header, isExpanded).apply { addAll(children) }
}


fun xExpandable(block: TBinder<ExpandableBuilder>): ExpandableGroup = ExpandableBuilder().apply(block).build()