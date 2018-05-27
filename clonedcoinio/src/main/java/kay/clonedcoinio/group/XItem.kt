package kay.clonedcoinio.group

import android.support.annotation.LayoutRes
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder


/**
 * Created by Kay Tran on 25/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */


typealias BindFunction =  ViewHolder.(position: Int) -> Unit

open class XItem( @LayoutRes private val layoutId: Int,private val binder: BindFunction) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        binder(viewHolder, position)
    }

    override fun getLayout(): Int {
        return layoutId
    }
}