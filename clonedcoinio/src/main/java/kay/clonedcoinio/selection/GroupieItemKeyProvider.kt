package kay.clonedcoinio.selection

import android.support.v7.widget.RecyclerView
import android.util.LongSparseArray
import android.util.SparseArray
import android.view.View
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.util.containsKey
import androidx.util.set
import com.xwray.groupie.ViewHolder


/**
 * Created by Kay Tran on 24/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 *
 *
 * Creates a new key provider that uses cached `long` stable ids associated
 * with the RecyclerView items.
 *
 * @param recyclerView the owner RecyclerView
 */
class GroupieItemKeyProvider(recyclerView: RecyclerView) : ItemKeyProvider<Long>(ItemKeyProvider.SCOPE_CACHED) {

    private val mPositionToKey = SparseArray<Long>()
    private val mKeyToPosition = LongSparseArray<Int>()
    private var mRecyclerView: RecyclerView = recyclerView


    private fun onAttached(view: View) {
        val holder = mRecyclerView.findContainingViewHolder(view)
        val position = holder!!.adapterPosition
        val id = (holder as ViewHolder).item.id
        if (position != RecyclerView.NO_POSITION && id != RecyclerView.NO_ID) {
            mPositionToKey.put(position, id)
            mKeyToPosition[id] = position
        }
    }

    private fun onDetached(view: View) {
        val holder = mRecyclerView.findContainingViewHolder(view)
        val position = holder!!.adapterPosition
        val id = (holder as ViewHolder).item.id
        if (position != RecyclerView.NO_POSITION && id != RecyclerView.NO_ID) {
            mPositionToKey.delete(position)
            mKeyToPosition.remove(id)
        }
    }

    override fun getKey(position: Int): Long? {
        return mPositionToKey.get(position, null)
    }

    override fun getPosition(key: Long): Int {
        return if (mKeyToPosition.containsKey(key)) {
            mKeyToPosition[key]
        } else RecyclerView.NO_POSITION
    }

    init {
        mRecyclerView.addOnChildAttachStateChangeListener(
                object : RecyclerView.OnChildAttachStateChangeListener {
                    override fun onChildViewAttachedToWindow(view: View) {
                        onAttached(view)
                    }

                    override fun onChildViewDetachedFromWindow(view: View) {
                        onDetached(view)
                    }
                }
        )
    }
}