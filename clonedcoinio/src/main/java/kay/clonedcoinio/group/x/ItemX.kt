package kay.clonedcoinio.group.x

import kay.clonedcoinio.group.BindFunction
import kay.clonedcoinio.group.XItem


/**
 * Created by Kay Tran on 27/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */


typealias TBinder<T> = T.() -> Unit

data class XHeaderData(var title: String? = null, var subtitle: String? = null, var icon: Int? = null)

data class XFooterData(var description: String? = null)

class ItemBuilder {

    var layoutId: Int? = null

    var binder: BindFunction? = null

    fun build(): XItem = XItem(layoutId!!, binder!!)
    /** crash it while you can*/
}

fun xItem(block: TBinder<ItemBuilder>) = ItemBuilder().apply(block).build()