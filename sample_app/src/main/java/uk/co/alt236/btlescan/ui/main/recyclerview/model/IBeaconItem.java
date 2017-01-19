package uk.co.alt236.btlescan.ui.main.recyclerview.model;

import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;

public class IBeaconItem implements RecyclerViewItem {

    private final IBeaconDevice device;

    public IBeaconItem(final IBeaconDevice device) {
        this.device = device;
    }

    public IBeaconDevice getDevice() {
        return device;
    }

}
