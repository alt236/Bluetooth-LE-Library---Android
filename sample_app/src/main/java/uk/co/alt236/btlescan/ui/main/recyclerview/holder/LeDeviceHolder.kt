package uk.co.alt236.btlescan.ui.main.recyclerview.holder

import android.view.View
import android.widget.TextView
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.main.recyclerview.model.LeDeviceItem

class LeDeviceHolder(itemView: View) : BaseViewHolder<LeDeviceItem>(itemView), CommonDeviceHolder {
    override val deviceName: TextView = itemView.findViewById<View>(R.id.device_name) as TextView
    override val deviceAddress: TextView = itemView.findViewById<View>(R.id.device_address) as TextView
    override val deviceRssi: TextView = itemView.findViewById<View>(R.id.device_rssi) as TextView
    override val deviceLastUpdated: TextView = itemView.findViewById<View>(R.id.device_last_update) as TextView
}