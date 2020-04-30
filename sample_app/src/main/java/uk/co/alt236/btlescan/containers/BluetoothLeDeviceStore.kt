package uk.co.alt236.btlescan.containers

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor
import java.util.*

class BluetoothLeDeviceStore {
    private val mDeviceMap = HashMap<String, BluetoothLeDevice>()

    fun addDevice(device: BluetoothLeDevice) {
        if (mDeviceMap.containsKey(device.address)) {
            mDeviceMap[device.address]!!.updateRssiReading(device.timestamp, device.rssi)
        } else {
            mDeviceMap[device.address] = device
        }
    }

    fun clear() {
        mDeviceMap.clear()
    }

    val size: Int
        get() = mDeviceMap.size

    val deviceCursor: EasyObjectCursor<BluetoothLeDevice>
        get() = getDeviceCursor(DEFAULT_COMPARATOR)

    fun getDeviceCursor(comparator: Comparator<BluetoothLeDevice>): EasyObjectCursor<BluetoothLeDevice> {
        return EasyObjectCursor(
                BluetoothLeDevice::class.java,
                getDeviceList(comparator),
                "address")
    }

    val deviceList: List<BluetoothLeDevice>
        get() = getDeviceList(DEFAULT_COMPARATOR)

    fun getDeviceList(comparator: Comparator<BluetoothLeDevice>): List<BluetoothLeDevice> {
        val methodResult: List<BluetoothLeDevice> = ArrayList(mDeviceMap.values)
        Collections.sort(methodResult, comparator)
        return methodResult
    }

    private class BluetoothLeDeviceComparator : Comparator<BluetoothLeDevice> {
        override fun compare(arg0: BluetoothLeDevice, arg1: BluetoothLeDevice): Int {
            return arg0.address.compareTo(arg1.address)
        }
    }

    companion object {
        private val DEFAULT_COMPARATOR = BluetoothLeDeviceComparator()
    }
}