package uk.co.alt236.btlescan.ui.details.recyclerview.binder

import android.content.Context
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.RssiInfoHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.model.RssiItem
import uk.co.alt236.btlescan.util.TimeFormatter

class RssiBinder(context: Context) : BaseViewBinder<RssiItem>(context) {

    override fun bind(holder: BaseViewHolder<RssiItem>, item: RssiItem) {
        val actualHolder = holder as RssiInfoHolder
        actualHolder.firstTimestamp.text = formatTime(item.firstTimestamp)
        actualHolder.firstRssi.text = formatRssi(item.firstRssi)
        actualHolder.lastTimestamp.text = formatTime(item.timestamp)
        actualHolder.lastRssi.text = formatRssi(item.rssi)
        actualHolder.runningAverageRssi.text = formatRssi(item.runningAverageRssi)
    }

    override fun canBind(item: RecyclerViewItem): Boolean {
        return item is RssiItem
    }

    private fun formatRssi(rssi: Double): String {
        return context.getString(R.string.formatter_db, rssi.toString())
    }

    private fun formatRssi(rssi: Int): String {
        return context.getString(R.string.formatter_db, rssi.toString())
    }

    companion object {
        private fun formatTime(time: Long): String {
            return TimeFormatter.getIsoDateTime(time)
        }
    }
}