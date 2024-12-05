package uk.co.alt236.btlescan.ui.main.recyclerview.model

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem

data class LeDeviceItem(val device: BluetoothLeDevice) : RecyclerViewItem