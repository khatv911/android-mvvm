package kay.clonedcoinio.group.x

import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder


/**
 * Created by Kay Tran on 27/5/18.
 * Profile : https://github.com/khatv911
 * Email   : khatv911@gmail.com
 */
class GroupieAdapterBuilder{

    private val children = mutableListOf<Group>()

    fun section(block: TBinder<SectionBuilder>){
        children.add(xSection(block))
    }

    fun build(): GroupAdapter<ViewHolder> = GroupAdapter<ViewHolder>().apply { addAll(children) }
}

fun groupAdapter(block: TBinder<GroupieAdapterBuilder>) = GroupieAdapterBuilder().apply(block).build()

fun GroupAdapter<ViewHolder>.section(block: TBinder<SectionBuilder>){
    add(xSection(block))
}