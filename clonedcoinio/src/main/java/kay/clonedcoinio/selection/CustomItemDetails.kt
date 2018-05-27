package kay.clonedcoinio.selection

import androidx.recyclerview.selection.ItemDetailsLookup


/**
 * Created by Kay Tran on 25/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class CustomItemDetails(private val id: Long, private val position: Int) : ItemDetailsLookup.ItemDetails<Long>() {
    override fun getSelectionKey(): Long? = id

    override fun getPosition(): Int = position
}