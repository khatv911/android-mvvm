package kay.clonedcoinio.selection


/**
 * Created by Kay Tran on 25/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
interface ItemDetailsGetter {
    fun getItemDetails(pos: Int): CustomItemDetails
}