package kay.clonedcoinio.group

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kay.clonedcoinio.R
import kotlinx.android.synthetic.main.section_x_header.view.*


/**
 * Created by Kay Tran on 24/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class HeaderXItem(private val text:String): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.text.text = text
    }
    override fun getLayout() = R.layout.section_x_header

}