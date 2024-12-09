package uk.co.alt236.btlescan.ui.details.recyclerview.holder

import android.view.View
import android.widget.TextView
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.app.ui.view.details.model.RssiItem
import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseViewHolder

class RssiInfoHolder(
    itemView: View,
) : BaseViewHolder<RssiItem>(itemView) {
    val firstTimestamp: TextView = itemView.findViewById<View>(R.id.firstTimestamp) as TextView
    val firstRssi: TextView = itemView.findViewById<View>(R.id.firstRssi) as TextView
    val lastTimestamp: TextView = itemView.findViewById<View>(R.id.lastTimestamp) as TextView
    val lastRssi: TextView = itemView.findViewById<View>(R.id.lastRssi) as TextView
    val runningAverageRssi: TextView = itemView.findViewById<View>(R.id.runningAverageRssi) as TextView
}
