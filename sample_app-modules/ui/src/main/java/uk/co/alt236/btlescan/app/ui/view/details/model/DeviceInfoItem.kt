package uk.co.alt236.btlescan.app.ui.view.details.model

import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem

interface DeviceInfoItem :
    DetailsScreenItems,
    RecyclerViewItem {
    val bluetoothDeviceKnownSupportedServices: Set<String>
    val bluetoothDeviceBondState: String
    val bluetoothDeviceMajorClassName: String
    val bluetoothDeviceClassName: String
    val address: String
    val name: String
}
