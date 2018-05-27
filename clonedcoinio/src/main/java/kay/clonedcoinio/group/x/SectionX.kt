package kay.clonedcoinio.group.x

import android.R
import android.widget.TextView
import com.xwray.groupie.Group
import com.xwray.groupie.Section
import kay.clonedcoinio.group.HeaderXItem
import kay.clonedcoinio.group.XItem


/**
 * Created by Kay Tran on 23/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */

class SectionBuilder {
    private var header: HeaderXItem? = null

    private var footer: XItem? = null

    private val children = mutableListOf<Group>()

    fun header(block: TBinder<XHeaderData>) {
        header = HeaderXItem().apply { xHeaderData = XHeaderData().apply(block) }
    }

    fun footer(block: () -> String) {
        footer = XItem(R.layout.simple_list_item_1) {
            itemView.findViewById<TextView>(R.id.text1).text = block.invoke()
        }
    }

    fun section(block: TBinder<SectionBuilder>) = children.add(xSection(block))


    fun expandable(block: TBinder<ExpandableBuilder>) = children.add(xExpandable(block))

    fun item(block: TBinder<ItemBuilder>) = children.add(xItem(block))


    fun build(): Section = Section().apply {
        header?.let { this.setHeader(it) }
        if (children.size > 0) addAll(children)
        footer?.let { this.setFooter(it) }
    }


}

fun xSection(block: TBinder<SectionBuilder>): Section = SectionBuilder().apply(block).build()
