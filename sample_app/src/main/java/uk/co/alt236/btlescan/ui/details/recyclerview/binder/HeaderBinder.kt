package uk.co.alt236.btlescan.ui.details.recyclerview.binder

import android.content.Context
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.HeaderHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.model.HeaderItem

class HeaderBinder(context: Context) : BaseViewBinder<HeaderItem>(context) {

    override fun bind(holder: BaseViewHolder<HeaderItem>, item: HeaderItem) {
        val actualHolder = holder as HeaderHolder
        actualHolder.textView.text = item.text
    }

    override fun canBind(item: RecyclerViewItem): Boolean {
        return item is HeaderItem
    }

}