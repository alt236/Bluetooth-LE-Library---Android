package uk.co.alt236.btlescan.ui.details.recyclerview.holder

import android.view.View
import android.widget.TextView
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.model.DeviceInfoItem

class DeviceInfoHolder(
    itemView: View,
) : BaseViewHolder<DeviceInfoItem>(itemView) {
    val name: TextView = itemView.findViewById<View>(R.id.deviceName) as TextView
    val address: TextView = itemView.findViewById<View>(R.id.deviceAddress) as TextView
    val deviceClass: TextView = itemView.findViewById<View>(R.id.deviceClass) as TextView
    val majorClass: TextView = itemView.findViewById<View>(R.id.deviceMajorClass) as TextView
    val services: TextView = itemView.findViewById<View>(R.id.deviceServiceList) as TextView
    val bondingState: TextView = itemView.findViewById<View>(R.id.deviceBondingState) as TextView
}
