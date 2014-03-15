package uk.co.alt236.bluetoothlelib.util;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;

public class IBeaconUtils {
	private static final double DISTANCE_THRESHOLD_WTF = 0.0;
	private static final double DISTANCE_THRESHOLD_IMMEDIATE = 0.5;
	private static final double DISTANCE_THRESHOLD_NEAR = 0.5;
	
	private static final byte[] SCAN_RECORD_PREFIX_IBEACON_1 = new byte[]{0x02, 0x01, 0x1A, 0x1A, (byte) 0xFF, 0x4C, 0x00, 0x02, 0x15};
	private static final byte[] SCAN_RECORD_PREFIX_IBEACON_2 = new byte[]{0x02, 0x01, 0x06, 0x1A, (byte) 0xFF, 0x4C, 0x00, 0x02, 0x15};

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

	// Code taken from: http://stackoverflow.com/questions/20416218/understanding-ibeacon-distancing
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

	
	private static boolean doesArrayBeginWith(byte[] array, byte[] prefix){
		if(array.length < prefix.length){return false;}

		for(int i = 0; i < prefix.length; i++){
			if(array[i] != prefix[i]){
				return false;
			}
		}

		return true;
	}
	
	public static boolean isThisAnIBeacon(BluetoothLeDevice device){
		return isThisAnIBeacon(device.getScanRecord());
	}

	public static boolean isThisAnIBeacon(byte[] scanRecord){
		if(doesArrayBeginWith(scanRecord, SCAN_RECORD_PREFIX_IBEACON_1)){
			return true;
		}

		if(doesArrayBeginWith(scanRecord, SCAN_RECORD_PREFIX_IBEACON_2)){
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
