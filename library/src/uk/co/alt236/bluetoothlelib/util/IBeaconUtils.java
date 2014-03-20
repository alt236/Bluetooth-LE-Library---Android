package uk.co.alt236.bluetoothlelib.util;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecord;

public class IBeaconUtils {
	private static final double DISTANCE_THRESHOLD_WTF = 0.0;
	private static final double DISTANCE_THRESHOLD_IMMEDIATE = 0.5;
	private static final double DISTANCE_THRESHOLD_NEAR = 3.0;

	private static final byte[] MANUFACTURER_DATA_IBEACON_PREFIX = new byte[]{0x4C, 0x00, 0x02, 0x15};

	public static IBeaconDistanceDescriptor getDistanceDescriptor(double accuracy){
		if(accuracy < DISTANCE_THRESHOLD_WTF){
			return IBeaconDistanceDescriptor.UNKNOWN;
		}

		if(accuracy < DISTANCE_THRESHOLD_IMMEDIATE){
			return IBeaconDistanceDescriptor.IMMEDIATE;
		}

		if(accuracy < DISTANCE_THRESHOLD_NEAR){
			return IBeaconDistanceDescriptor.NEAR;
		}

		return IBeaconDistanceDescriptor.FAR;
	}

	/**
	 * Calculates the accuracy of an RSSI reading.
	 *
	 * The code was taken from {@link http://stackoverflow.com/questions/20416218/understanding-ibeacon-distancing}
	 *
	 * @param txPower the calibrated TX power of an iBeacon
	 * @param rssi the RSSI value of the iBeacon
	 * @return
	 */
	public static double calculateAccuracy(int txPower, double rssi) {
		if (rssi == 0) {
			return -1.0; // if we cannot determine accuracy, return -1.
		}

		double ratio = rssi*1.0/txPower;
		if (ratio < 1.0) {
			return Math.pow(ratio,10);
		}
		else {
			final double accuracy =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
			return accuracy;
		}
	}

	/**
	 * Ascertains whether a {@link uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice} is an iBeacon;
	 *
	 * @param device a {@link uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice} device.
	 * @return
	 */
	public static boolean isThisAnIBeacon(BluetoothLeDevice device){
		return isThisAnIBeacon(
				device.getAdRecordStore().getRecordDataAsString(AdRecord.TYPE_MANUFACTURER_SPECIFIC_DATA).getBytes());
	}

	/**
	 * Ascertains whether a Manufacturer Data byte array belongs to an iBeacon;
	 *
	 * @param scanRecord a Bluetooth LE device's manufacturerData.
	 * @return
	 */
	public static boolean isThisAnIBeacon(byte[] manufacturerData){
		if(manufacturerData == null){return false;}

		// An iBeacon record must be at least 25 chars long
		if(!(manufacturerData.length >= 25)){return false;}

		if(ByteUtils.doesArrayBeginWith(manufacturerData, MANUFACTURER_DATA_IBEACON_PREFIX)){
			return true;
		}

		return false;
	}

	public enum IBeaconDistanceDescriptor{
		IMMEDIATE,
		NEAR,
		FAR,
		UNKNOWN,
	}
}
