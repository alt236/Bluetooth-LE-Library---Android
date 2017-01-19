package uk.co.alt236.btlescan.ui.details.recyclerview.model;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;

public class RssiItem implements RecyclerViewItem {

    private final BluetoothLeDevice mDevice;

    public RssiItem(BluetoothLeDevice device) {
        mDevice = device;
    }

    public int getRssi() {
        return mDevice.getRssi();
    }

    public double getRunningAverageRssi() {
        return mDevice.getRunningAverageRssi();
    }

    public int getFirstRssi() {
        return mDevice.getFirstRssi();
    }

    public long getFirstTimestamp() {
        return mDevice.getFirstTimestamp();
    }

    public long getTimestamp() {
        return mDevice.getTimestamp();
    }
}
