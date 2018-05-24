package kay.clonedcoinio.group

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kay.clonedcoinio.R
import kotlinx.android.synthetic.main.view_item_text_1.view.*


/**
 * Created by Kay Tran on 24/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class TextItem(private val name:String, private val value:String): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.itemView){
            tv_item_name.text = name
            editText.setText(value)
        }

    }

    override fun getLayout(): Int  = R.layout.view_item_text_1
}