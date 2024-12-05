package uk.co.alt236.btlescan.ui.details.recyclerview.model

import android.annotation.SuppressLint
import dev.alt236.bluetoothlelib.device.BluetoothLeDevice
import dev.alt236.bluetoothlelib.device.BluetoothService
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem

@SuppressLint("MissingPermission") // We check before this is called
class DeviceInfoItem(private val mDevice: BluetoothLeDevice) : RecyclerViewItem {

    val bluetoothDeviceKnownSupportedServices: Set<BluetoothService>
        get() = mDevice.bluetoothDeviceKnownSupportedServices

    val bluetoothDeviceBondState: String
        get() = mDevice.bluetoothDeviceBondState

    val bluetoothDeviceMajorClassName: String
        get() = mDevice.bluetoothDeviceMajorClassName

    val bluetoothDeviceClassName: String
        get() = mDevice.bluetoothDeviceClassName

    val address: String
        get() = mDevice.address

    val name: String
        get() = mDevice.name ?: ""

}