package kay.clonedcoinio.selection

import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import com.xwray.groupie.ViewHolder


/**
 * Created by Kay Tran on 24/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class JobItemDetailsLookup(private val mRecyclerView: RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(e: MotionEvent): ItemDetailsLookup.ItemDetails<Long>? {
        val view = mRecyclerView.findChildViewUnder(e.x, e.y)
        view?.let {
            val holder = mRecyclerView.getChildViewHolder(it)
            val item = (holder as ViewHolder).item
            return (item as ItemDetailsGetter).getItemDetails(holder.adapterPosition)
        }
        return null
    }
}