package kay.clonedcoinio.group

import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kay.clonedcoinio.R
import kay.clonedcoinio.selection.CustomItemDetails
import kay.clonedcoinio.selection.ItemDetailsGetter
import kotlinx.android.synthetic.main.header_item_expandable.view.*

class ExpandableHeaderItem
    : Item(), ExpandableItem, ItemDetailsGetter {

    override fun getItemDetails(pos: Int): CustomItemDetails = CustomItemDetails(id, pos)

    var xHeaderData = XHeaderData()

    private lateinit var expandableGroup: ExpandableGroup

    override fun getLayout(): Int = R.layout.header_item_expandable

    override fun bind(viewHolder: ViewHolder, position: Int) {

        // Initial icon state -- not animated.
        with(viewHolder.itemView) {
            icon.apply {
                setImageResource(if (expandableGroup.isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more)
                setOnClickListener {
                    expandableGroup.onToggleExpanded()
                    bindIcon(viewHolder)
                }
            }

            textView2.text = xHeaderData.subtitle

            tv_header_name.text = xHeaderData.title


        }

    }

    private fun bindIcon(viewHolder: ViewHolder) {
        viewHolder.itemView.icon.apply {
            setImageResource(if (expandableGroup.isExpanded) R.drawable.collapse_animated else R.drawable.expand_animated)
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }
}
