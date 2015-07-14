package uk.co.alt236.bluetoothlelib.device.beacon.ibeacon;

import java.util.Arrays;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecord;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconManufacturerData;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconType;
import uk.co.alt236.bluetoothlelib.util.ByteUtils;

/**
 * Parses the Manufactured Data field of an iBeacon
 * <p>
 * The parsing is based on the following schema:
 * <pre>
 * Byte|Value
 * -------------------------------------------------
 * 0	4C - Byte 1 (LSB) of Company identifier code
 * 1	00 - Byte 0 (MSB) of Company identifier code (0x004C == Apple)
 * 2	02 - Byte 0 of iBeacon advertisement indicator
 * 3	15 - Byte 1 of iBeacon advertisement indicator
 * 4	e2 |\
 * 5	c5 |\\
 * 6	6d |#\\
 * 7	b5 |##\\
 * 8	df |###\\
 * 9	fb |####\\
 * 10	48 |#####\\
 * 11	d2 |#####|| iBeacon
 * 12	b0 |#####|| Proximity UUID
 * 13	60 |#####//
 * 14	d0 |####//
 * 15	f5 |###//
 * 16	a7 |##//
 * 17	10 |#//
 * 18	96 |//
 * 19	e0 |/
 * 20	00 - major
 * 21	00
 * 22	00 - minor
 * 23	00
 * 24	c5 - The 2's complement of the calibrated Tx Power
 * </pre>
 * @author Alexandros Schillings
 */

public final class IBeaconManufacturerData extends BeaconManufacturerData{
    private final int mCalibratedTxPower;
    private final int mCompanyIdentidier;
    private final int mIBeaconAdvertisment;
    private final int mMajor;
    private final int mMinor;
    private final String mUUID;

    /**
     * Instantiates a new iBeacon manufacturer data object.
     *
     * @param device a {@link BluetoothLeDevice}
     * @throws IllegalArgumentException if the data is not from an iBeacon.
     */
    public IBeaconManufacturerData(final BluetoothLeDevice device) {
        this(device.getAdRecordStore().getRecord(AdRecord.TYPE_MANUFACTURER_SPECIFIC_DATA).getData());
    }

    /**
     * Instantiates a new iBeacon manufacturer data object.
     *
     * @param manufacturerData the {@link AdRecord#TYPE_MANUFACTURER_SPECIFIC_DATA} data array
     * @throws IllegalArgumentException if the data is not from an iBeacon.
     */
    public IBeaconManufacturerData(final byte[] manufacturerData) {
        super(BeaconType.IBEACON, manufacturerData);

        final byte[] intArray = Arrays.copyOfRange(manufacturerData, 0, 2);
        ByteUtils.invertArray(intArray);

        mCompanyIdentidier = ByteUtils.getIntFrom2ByteArray(intArray);
        mIBeaconAdvertisment = ByteUtils.getIntFrom2ByteArray(Arrays.copyOfRange(manufacturerData, 2, 4));
        mUUID = IBeaconUtils.calculateUuidString(Arrays.copyOfRange(manufacturerData, 4, 20));
        mMajor = ByteUtils.getIntFrom2ByteArray(Arrays.copyOfRange(manufacturerData, 20, 22));
        mMinor = ByteUtils.getIntFrom2ByteArray(Arrays.copyOfRange(manufacturerData, 22, 24));
        mCalibratedTxPower = manufacturerData[24];
    }

    /**
     * Gets the calibrated TX power of the iBeacon device as reported.
     *
     * @return the calibrated TX power
     */
    public int getCalibratedTxPower() {
        return mCalibratedTxPower;
    }

    /**
     * Gets the iBeacon company identifier.
     *
     * @return the company identifier
     */
    public int getCompanyIdentifier() {
        return mCompanyIdentidier;
    }

    public int getIBeaconAdvertisement() {
        return mIBeaconAdvertisment;
    }

    /**
     * Gets the iBeacon Major value.
     *
     * @return the Major value
     */
    public int getMajor() {
        return mMajor;
    }

    /**
     * Gets the iBeacon Minor value.
     *
     * @return the Minor value
     */
    public int getMinor() {
        return mMinor;
    }

    /**
     * Gets the iBeacon UUID.
     *
     * @return the UUID
     */
    public String getUUID() {
        return mUUID;
    }
}
