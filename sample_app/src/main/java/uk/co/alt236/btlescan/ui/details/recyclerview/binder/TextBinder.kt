package uk.co.alt236.btlescan.ui.details.recyclerview.binder

import android.content.Context
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.TextHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.model.TextItem

class TextBinder(
    context: Context,
) : BaseViewBinder<TextItem>(context) {
    override fun bind(
        holder: BaseViewHolder<TextItem>,
        item: TextItem,
    ) {
        val actualHolder = holder as TextHolder
        actualHolder.textView.text = item.text
    }

    override fun canBind(item: RecyclerViewItem): Boolean = item is TextItem
}
