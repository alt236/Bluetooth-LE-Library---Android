package uk.co.alt236.btlescan.ui.main.recyclerview.holder

import android.view.View
import android.widget.TextView
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.main.recyclerview.model.IBeaconItem

class IBeaconHolder(
    itemView: View,
) : BaseViewHolder<IBeaconItem>(itemView),
    CommonDeviceHolder {
    override val deviceName: TextView = itemView.findViewById<View>(R.id.device_name) as TextView
    override val deviceAddress: TextView = itemView.findViewById<View>(R.id.device_address) as TextView
    override val deviceRssi: TextView = itemView.findViewById<View>(R.id.device_rssi) as TextView
    override val deviceLastUpdated: TextView = itemView.findViewById<View>(R.id.device_last_update) as TextView

    val ibeaconUUID: TextView = itemView.findViewById<View>(R.id.ibeacon_uuid) as TextView
    val ibeaconMajor: TextView = itemView.findViewById<View>(R.id.ibeacon_major) as TextView
    val ibeaconMinor: TextView = itemView.findViewById<View>(R.id.ibeacon_minor) as TextView
    val ibeaconTxPower: TextView = itemView.findViewById<View>(R.id.ibeacon_tx_power) as TextView
    val ibeaconDistance: TextView = itemView.findViewById<View>(R.id.ibeacon_distance) as TextView
    val ibeaconDistanceDescriptor: TextView = itemView.findViewById<View>(R.id.ibeacon_distance_descriptor) as TextView
}
