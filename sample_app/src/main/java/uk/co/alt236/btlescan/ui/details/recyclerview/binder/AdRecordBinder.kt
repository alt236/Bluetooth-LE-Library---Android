package uk.co.alt236.btlescan.ui.details.recyclerview.binder

import android.content.Context
import uk.co.alt236.bluetoothlelib.util.ByteUtils
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.AdRecordHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.model.AdRecordItem

class AdRecordBinder(context: Context) : BaseViewBinder<AdRecordItem>(context) {

    override fun bind(holder: BaseViewHolder<AdRecordItem>, item: AdRecordItem) {
        val actualHolder = holder as AdRecordHolder
        actualHolder.titleTextView.text = item.title
        actualHolder.stringTextView.text = context.getString(R.string.formatter_single_quoted_string,
                item.dataAsString)
        actualHolder.arrayTextView.text = context.getString(R.string.formatter_single_quoted_string,
                ByteUtils.byteArrayToHexString(item.data))
    }

    override fun canBind(item: RecyclerViewItem): Boolean {
        return item is AdRecordItem
    }
}