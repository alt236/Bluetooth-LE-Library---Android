package uk.co.alt236.btlescan.ui.details.recyclerview.model

import dev.alt236.bluetoothlelib.device.BluetoothLeDevice
import uk.co.alt236.btlescan.app.ui.view.details.model.RssiItem
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem

class RssiItem(
    private val mDevice: BluetoothLeDevice,
) : RecyclerViewItem,
    RssiItem {
    override val rssi: Int
        get() = mDevice.rssi

    override val runningAverageRssi: Double
        get() = mDevice.runningAverageRssi

    override val firstRssi: Int
        get() = mDevice.firstRssi

    override val firstTimestamp: Long
        get() = mDevice.firstTimestamp

    override val timestamp: Long
        get() = mDevice.timestamp
}
