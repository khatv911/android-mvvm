package kay.clonedcoinio.group

import android.widget.TextView
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.Section


/**
 * Created by Kay Tran on 23/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */


typealias TBinder<T> = T.() -> Unit

data class XHeaderData(var title: String? = null, var subtitle: String? = null, var icon: Int? = null)

data class XFooterData(var description: String? = null)

//fun section(block: TBinder<Section>): Section = Section().apply(block)
//
//fun Section.header(block: TBinder<XHeaderData>) {
//    setHeader(HeaderXItem().apply {
//        xHeaderData = XHeaderData().apply(block)
//    })
//}
//
//fun Section.footer(block: () -> String) {
//    setFooter(XItem(android.R.layout.simple_list_item_1) {
//        itemView.findViewById<TextView>(android.R.id.text1).text = block.invoke()
//    })
//}

class ItemBuilder {

    var layoutId: Int? = null

    var binder: BindFunction? = null

    fun build(): XItem = XItem(layoutId!!, binder!!)
    /** crash it while you can*/
}

class SectionBuilder {
    private var header: HeaderXItem? = null

    private var footer: XItem? = null

    private val children = mutableListOf<Group>()

    fun header(block: TBinder<XHeaderData>) {
        header = HeaderXItem().apply { xHeaderData = XHeaderData().apply(block) }
    }

    fun footer(block: () -> String) {
        footer = XItem(android.R.layout.simple_list_item_1) {
            itemView.findViewById<TextView>(android.R.id.text1).text = block.invoke()
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

fun xItem(block: TBinder<ItemBuilder>) = ItemBuilder().apply(block).build()
fun xExpandable(block: TBinder<ExpandableBuilder>): ExpandableGroup = ExpandableBuilder().apply(block).build()
fun xSection(block: TBinder<SectionBuilder>): Section = SectionBuilder().apply(block).build()
