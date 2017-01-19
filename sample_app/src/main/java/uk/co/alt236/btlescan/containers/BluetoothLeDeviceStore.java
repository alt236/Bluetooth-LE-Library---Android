package uk.co.alt236.btlescan.containers;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;

public class BluetoothLeDeviceStore {
    private static final BluetoothLeDeviceComparator DEFAULT_COMPARATOR = new BluetoothLeDeviceComparator();
    private final Map<String, BluetoothLeDevice> mDeviceMap;

    public BluetoothLeDeviceStore() {
        mDeviceMap = new HashMap<>();
    }

    public void addDevice(@NonNull final BluetoothLeDevice device) {
        if (mDeviceMap.containsKey(device.getAddress())) {
            mDeviceMap.get(device.getAddress()).updateRssiReading(device.getTimestamp(), device.getRssi());
        } else {
            mDeviceMap.put(device.getAddress(), device);
        }
    }

    public void clear() {
        mDeviceMap.clear();
    }

    public int getSize() {
        return mDeviceMap.size();
    }

    @NonNull
    public EasyObjectCursor<BluetoothLeDevice> getDeviceCursor() {
        return getDeviceCursor(DEFAULT_COMPARATOR);
    }

    @NonNull
    public EasyObjectCursor<BluetoothLeDevice> getDeviceCursor(@NonNull Comparator<BluetoothLeDevice> comparator) {
        return new EasyObjectCursor<>(
                BluetoothLeDevice.class,
                getDeviceList(comparator),
                "address");
    }

    @NonNull
    public List<BluetoothLeDevice> getDeviceList() {
        return getDeviceList(DEFAULT_COMPARATOR);
    }

    @NonNull
    public List<BluetoothLeDevice> getDeviceList(@NonNull Comparator<BluetoothLeDevice> comparator) {
        final List<BluetoothLeDevice> methodResult = new ArrayList<>(mDeviceMap.values());

        Collections.sort(methodResult, comparator);

        return methodResult;
    }

    private static class BluetoothLeDeviceComparator implements Comparator<BluetoothLeDevice> {

        @Override
        public int compare(final BluetoothLeDevice arg0, final BluetoothLeDevice arg1) {
            return arg0.getAddress().compareTo(arg1.getAddress());
        }
    }
}
