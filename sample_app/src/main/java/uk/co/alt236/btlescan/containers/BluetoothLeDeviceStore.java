package uk.co.alt236.btlescan.containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;

public class BluetoothLeDeviceStore {
    private final Map<String, BluetoothLeDevice> mDeviceMap;


    public BluetoothLeDeviceStore() {
        mDeviceMap = new HashMap<>();
    }

    public void addDevice(final BluetoothLeDevice device) {
        if (mDeviceMap.containsKey(device.getAddress())) {
            mDeviceMap.get(device.getAddress()).updateRssiReading(device.getTimestamp(), device.getRssi());
        } else {
            mDeviceMap.put(device.getAddress(), device);
        }
    }

    public void clear() {
        mDeviceMap.clear();
    }

    public EasyObjectCursor<BluetoothLeDevice> getDeviceCursor() {
        return new EasyObjectCursor<>(
                BluetoothLeDevice.class,
                getDeviceList(),
                "address");
    }

    public List<BluetoothLeDevice> getDeviceList() {
        final List<BluetoothLeDevice> methodResult = new ArrayList<>(mDeviceMap.values());

        Collections.sort(methodResult, new Comparator<BluetoothLeDevice>() {

            @Override
            public int compare(final BluetoothLeDevice arg0, final BluetoothLeDevice arg1) {
                return arg0.getAddress().compareToIgnoreCase(arg1.getAddress());
            }
        });

        return methodResult;
    }
}
