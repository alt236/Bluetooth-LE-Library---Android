package uk.co.alt236.bluetoothlelib.device.mfdata;

import java.util.Arrays;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecord;
import uk.co.alt236.bluetoothlelib.util.ByteUtils;

/**
 * Parses the Manufactured Data field of an iBeacon
 *
 * The parsing is based on the following schema:
 *
 * <p>
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
 * 11	d2 |#####|| iBeacon proximity UUID
 * 12	b0 |#####||
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
 *
 * </p>
 *
 * @author Alexandros Schillings
 *
 */

public final class IBeaconManufacturerData {
	private final byte[] mData;
	private final int mCalibratedTxPower;
	private final int mCompanyIdentidier;
	private final int mIBeaconAdvertisment;
	private final int mMajor;
	private final int mMinor;
	private final String mUUID;

	public IBeaconManufacturerData(BluetoothLeDevice device){
		this(device.getAdRecordStore().getRecord(AdRecord.TYPE_MANUFACTURER_SPECIFIC_DATA).getData());
	}

	/**
	 * Instantiates a new iBeacon manufacturer data object.

	 * @param data the {@link #uk.co.alt236.bluetoothlelib.device.adrecord.AdRecord.TYPE_MANUFACTURER_SPECIFIC_DATA} data array
	 * @throws IndexOutOfBoundsException if the data array is shorter than expected
	 */
	public IBeaconManufacturerData(byte[] data){
		mData = data;

		mCompanyIdentidier = ByteUtils.getIntFrom2ByteArray(
				ByteUtils.invertArray(Arrays.copyOfRange(mData, 0, 2)));

		mIBeaconAdvertisment = ByteUtils.getIntFrom2ByteArray(Arrays.copyOfRange(mData, 2, 4));
		mUUID =  calculateUUIDString(Arrays.copyOfRange(mData, 4, 20));
		mMajor = ByteUtils.getIntFrom2ByteArray(Arrays.copyOfRange(mData, 20, 22));
		mMinor = ByteUtils.getIntFrom2ByteArray(Arrays.copyOfRange(mData, 22, 24));
		mCalibratedTxPower = data[24];
	}

	/**
	 * Gets the calibrated TX power of the iBeacon device as reported.
	 *
	 * @return the calibrated TX power
	 */
	public int getCalibratedTxPower(){
		return mCalibratedTxPower;
	}

	/**
	 * Gets the iBeacon company identifier.
	 *
	 * @return the company identifier
	 */
	public int getCompanyIdentifier(){
		return mCompanyIdentidier;
	}

	public int getIBeaconAdvertisement(){
		return mIBeaconAdvertisment;
	}

	/**
	 * Gets the iBeacon Major value.
	 *
	 * @return the Major value
	 */
	public int getMajor(){
		return mMajor;
	}

	/**
	 * Gets the iBeacon Minor value.
	 *
	 * @return the Minor value
	 */
	public int getMinor(){
		return mMinor;
	}

	/**
	 * Gets the iBeacon UUID.
	 *
	 * @return the UUID
	 */
	public String getUUID(){
		return mUUID;
	}

	private static String calculateUUIDString(final byte[] uuid){
		final StringBuffer sb = new StringBuffer();

		for(int i = 0 ; i< uuid.length; i++){
			if(i == 4){sb.append('-');}
			if(i == 6){sb.append('-');}
			if(i == 8){sb.append('-');}
			if(i == 10){sb.append('-');}

			sb.append(
					Integer.toHexString(ByteUtils.getIntFromByte(uuid[i])));
		}


		return sb.toString();
	}
}
