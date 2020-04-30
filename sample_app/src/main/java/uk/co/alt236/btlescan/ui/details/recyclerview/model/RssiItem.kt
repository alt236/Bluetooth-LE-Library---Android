package uk.co.alt236.btlescan.ui.details.recyclerview.model

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem

class RssiItem(private val mDevice: BluetoothLeDevice) : RecyclerViewItem {
    val rssi: Int
        get() = mDevice.rssi

    val runningAverageRssi: Double
        get() = mDevice.runningAverageRssi

    val firstRssi: Int
        get() = mDevice.firstRssi

    val firstTimestamp: Long
        get() = mDevice.firstTimestamp

    val timestamp: Long
        get() = mDevice.timestamp

}