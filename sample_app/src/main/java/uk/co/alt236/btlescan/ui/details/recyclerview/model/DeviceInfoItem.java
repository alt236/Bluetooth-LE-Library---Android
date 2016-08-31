package uk.co.alt236.btlescan.ui.details.recyclerview.model;

import java.util.Set;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.BluetoothService;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;

public class DeviceInfoItem implements RecyclerViewItem {

    private final BluetoothLeDevice mDevice;

    public DeviceInfoItem(BluetoothLeDevice device) {
        mDevice = device;
    }

    public Set<BluetoothService> getBluetoothDeviceKnownSupportedServices() {
        return mDevice.getBluetoothDeviceKnownSupportedServices();
    }

    public String getBluetoothDeviceBondState() {
        return mDevice.getBluetoothDeviceBondState();
    }

    public String getBluetoothDeviceMajorClassName() {
        return mDevice.getBluetoothDeviceMajorClassName();
    }

    public String getBluetoothDeviceClassName() {
        return mDevice.getBluetoothDeviceClassName();
    }

    public String getAddress() {
        return mDevice.getAddress();
    }

    public String getName() {
        return mDevice.getName();
    }
}
