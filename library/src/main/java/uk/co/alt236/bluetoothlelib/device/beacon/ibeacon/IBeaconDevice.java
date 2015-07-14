package uk.co.alt236.bluetoothlelib.device.beacon.ibeacon;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconType;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconUtils;

public class IBeaconDevice extends BluetoothLeDevice implements BeaconDevice{

    /**
     * The m iBeacon data.
     */
    private final IBeaconManufacturerData mIBeaconData;

    /**
     * Instantiates a new iBeacon device.
     *
     * @param device     the device
     * @param rssi       the RSSI value
     * @param scanRecord the scanRecord
     * @throws IllegalArgumentException if the passed device is not an iBeacon
     */
    public IBeaconDevice(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
        super(device, rssi, scanRecord, 0);
        mIBeaconData = new IBeaconManufacturerData(this);
    }

    /**
     * Instantiates a new iBeacon device.
     *
     * @param device     the device
     * @param rssi       the RSSI value of the RSSI measurement
     * @param scanRecord the scan record
     * @param timestamp  the timestamp of the RSSI measurement
     * @throws IllegalArgumentException if the passed device is not an iBeacon
     */
    public IBeaconDevice(final BluetoothDevice device, final int rssi, final byte[] scanRecord, final long timestamp) {
        super(device, rssi, scanRecord, timestamp);
        mIBeaconData = new IBeaconManufacturerData(this);
    }

    /**
     * Will try to convert a {@link BluetoothLeDevice} into an
     * iBeacon Device.
     *
     * @param device the device
     * @throws IllegalArgumentException if the passed device is not an iBeacon
     */
    public IBeaconDevice(final BluetoothLeDevice device) {
        super(device);
        mIBeaconData = new IBeaconManufacturerData(this);
    }

    private IBeaconDevice(final Parcel in) {
        super(in);
        mIBeaconData = new IBeaconManufacturerData(this);
    }

    /**
     * Gets the estimated Accuracy of the reading in meters based on
     * a simple running average of the last {@link #MAX_RSSI_LOG_SIZE}
     * samples.
     *
     * @return the accuracy in meters
     */
    public double getAccuracy() {
        return IBeaconUtils.calculateAccuracy(
                getCalibratedTxPower(),
                getRunningAverageRssi());
    }

    @Override
    public BeaconType getBeaconType() {
        return BeaconType.IBEACON;
    }

    /**
     * Gets the calibrated TX power of the iBeacon device as reported.
     *
     * @return the calibrated TX power
     */
    public int getCalibratedTxPower() {
        return getIBeaconData().getCalibratedTxPower();
    }

    /**
     * Gets the iBeacon company identifier.
     *
     * @return the company identifier
     */
    public int getCompanyIdentifier() {
        return getIBeaconData().getCompanyIdentifier();
    }

    /**
     * Gets the estimated Distance descriptor.
     *
     * @return the distance descriptor
     */
    public IBeaconDistanceDescriptor getDistanceDescriptor() {
        return IBeaconUtils.getDistanceDescriptor(getAccuracy());
    }

    /**
     * Gets the iBeacon manufacturing data.
     *
     * @return the iBeacon data
     */
    public IBeaconManufacturerData getIBeaconData() {
        return mIBeaconData;
    }

    /**
     * Gets the iBeacon Major value.
     *
     * @return the Major value
     */
    public int getMajor() {
        return getIBeaconData().getMajor();
    }

    /**
     * Gets the iBeacon Minor value.
     *
     * @return the Minor value
     */
    public int getMinor() {
        return getIBeaconData().getMinor();
    }

    /**
     * Gets the iBeacon UUID.
     *
     * @return the UUID
     */
    public String getUUID() {
        return getIBeaconData().getUUID();
    }
}
