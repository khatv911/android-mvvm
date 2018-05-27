package kay.clonedcoinio.group

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kay.clonedcoinio.R
import kay.clonedcoinio.selection.CustomItemDetails
import kay.clonedcoinio.selection.ItemDetailsGetter
import kotlinx.android.synthetic.main.view_item_text_1.view.*


/**
 * Created by Kay Tran on 24/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class TextItem(private val name: String, private val value: String) : Item(), ItemDetailsGetter {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.itemView) {
            tv_item_name.text = name
            editText.text = value
        }

    }

    override fun getItemDetails(pos: Int): CustomItemDetails {
        return CustomItemDetails(id, pos)
    }

    override fun getLayout(): Int = R.layout.view_item_text_1
}