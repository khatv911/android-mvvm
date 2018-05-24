package kay.clonedcoinio.group

import android.view.View
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kay.clonedcoinio.R
import kotlinx.android.synthetic.main.header_item_expandable.view.*

class ExpandableHeaderItem(private val header: String,
                           private val count: Int)
    : Item(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup


    override fun getLayout(): Int = R.layout.header_item_expandable

    override fun bind(viewHolder: ViewHolder, position: Int) {

        // Initial icon state -- not animated.
       with(viewHolder.itemView) {
           icon.apply {
               visibility = View.VISIBLE
               setImageResource(if (expandableGroup.isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more)

           }

           textView2.text = "%d".format(count)

           tv_header_name.text = header

           setOnClickListener {
               expandableGroup.onToggleExpanded()
               bindIcon(viewHolder)
           }

       }

    }

    private fun bindIcon(viewHolder: ViewHolder) {
        viewHolder.itemView.icon.apply {
            visibility = View.VISIBLE
            setImageResource(if (expandableGroup.isExpanded) R.drawable.collapse_animated else R.drawable.expand_animated)
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }
}
