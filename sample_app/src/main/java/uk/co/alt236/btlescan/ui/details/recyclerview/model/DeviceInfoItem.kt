package uk.co.alt236.btlescan.ui.details.recyclerview.model

import android.annotation.SuppressLint
import dev.alt236.bluetoothlelib.device.BluetoothLeDevice
import uk.co.alt236.btlescan.app.ui.view.details.model.DeviceInfoItem

@SuppressLint("MissingPermission") // We check before this is called
class DeviceInfoItem(
    private val device: BluetoothLeDevice,
) : DeviceInfoItem {
    override val bluetoothDeviceKnownSupportedServices: Set<String>
        get() = device.bluetoothDeviceKnownSupportedServices.map { it.toString() }.toSet()

    override val bluetoothDeviceBondState: String
        get() = device.bluetoothDeviceBondState

    override val bluetoothDeviceMajorClassName: String
        get() = device.bluetoothDeviceMajorClassName

    override val bluetoothDeviceClassName: String
        get() = device.bluetoothDeviceClassName

    override val address: String
        get() = device.address

    override val name: String
        get() = device.name.orEmpty()
}
