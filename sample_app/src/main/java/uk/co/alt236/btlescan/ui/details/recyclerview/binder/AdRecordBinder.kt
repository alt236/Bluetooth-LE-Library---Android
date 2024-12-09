package uk.co.alt236.btlescan.ui.details.recyclerview.binder

import android.content.Context
import dev.alt236.bluetoothlelib.util.ByteUtils
import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseViewBinder
import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.AdRecordHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.model.AdRecordItem

class AdRecordBinder(
    context: Context,
) : BaseViewBinder<AdRecordItem>(context) {
    override fun bind(
        holder: BaseViewHolder<AdRecordItem>,
        item: AdRecordItem,
    ) {
        val actualHolder = holder as AdRecordHolder

        actualHolder.titleTextView.text = item.title
        actualHolder.lengthTextView.text = item.data.size.toString()

        actualHolder.stringTextView.text = getQuotedString(item.dataAsString)

        val hexString = ByteUtils.byteArrayToHexString(item.data)
        actualHolder.arrayTextView.text = getQuotedString(hexString)

        val charString = item.dataAsChars
        actualHolder.charactersTextView.text = getQuotedString(charString)
    }

    override fun canBind(item: RecyclerViewItem): Boolean = item is AdRecordItem
}
