package uk.co.alt236.btlescan.ui.details.recyclerview.binder

import android.content.Context
import uk.co.alt236.btlescan.app.ui.view.details.model.HeaderItem
import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseViewBinder
import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.HeaderHolder

class HeaderBinder(
    context: Context,
) : BaseViewBinder<HeaderItem>(context) {
    override fun bind(
        holder: BaseViewHolder<HeaderItem>,
        item: HeaderItem,
    ) {
        val actualHolder = holder as HeaderHolder
        actualHolder.textView.text = item.text
    }

    override fun canBind(item: RecyclerViewItem): Boolean = item is HeaderItem
}
