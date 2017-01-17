package uk.co.alt236.btlescan.ui.main.recyclerview.model;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;

public class LeDeviceItem implements RecyclerViewItem {

    private final BluetoothLeDevice device;

    public LeDeviceItem(final BluetoothLeDevice device) {
        this.device = device;
    }

    public BluetoothLeDevice getDevice() {
        return device;
    }
}
