package uk.co.alt236.btlescan.util;

import uk.co.alt236.btlescan.containers.BluetoothLeDevice;

public class IBeaconUtils {
	private static final byte[] SCAN_RECORD_PREFIX_IBEACON_1 = new byte[]{0x02, 0x01, 0x1A, 0x1A, (byte) 0xFF, 0x4C, 0x00, 0x02, 0x15};
	private static final byte[] SCAN_RECORD_PREFIX_IBEACON_2 = new byte[]{0x02, 0x01, 0x06, 0x1A, (byte) 0xFF, 0x4C, 0x00, 0x02, 0x15};

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

	private static boolean doesArrayBeginWith(byte[] array, byte[] prefix){
		if(array.length < prefix.length){return false;}

		for(int i = 0; i < prefix.length; i++){
			if(array[i] != prefix[i]){
				return false;
			}
		}

		return true;
	}
}
