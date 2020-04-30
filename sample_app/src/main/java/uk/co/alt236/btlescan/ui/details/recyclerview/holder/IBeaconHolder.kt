package uk.co.alt236.btlescan.ui.details.recyclerview.holder

import android.view.View
import android.widget.TextView
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.model.IBeaconItem

class IBeaconHolder(itemView: View) : BaseViewHolder<IBeaconItem>(itemView) {
    val companyId: TextView = itemView.findViewById<View>(R.id.companyId) as TextView
    val advert: TextView = itemView.findViewById<View>(R.id.advertisement) as TextView
    val uuid: TextView = itemView.findViewById<View>(R.id.uuid) as TextView
    val major: TextView = itemView.findViewById<View>(R.id.major) as TextView
    val minor: TextView = itemView.findViewById<View>(R.id.minor) as TextView
    val txPower: TextView = itemView.findViewById<View>(R.id.txpower) as TextView
}