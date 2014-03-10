package uk.co.alt236.btlescan.containers;

public final class ManufacturerDataIBeacon {
// 0	FF # Manufacturer specific data AD type
// 1	4C # Byte 1 of Company identifier code
// 2	00 # Byte 0 of Company identifier code (0x004C == Apple)
// 3	02 # Byte 0 of iBeacon advertisement indicator
// 4	15 # Byte 1 of iBeacon advertisement indicator
// 5	e2 #
// 6	c5 ##
// 7	6d ###
// 8	b5 ####
// 9	df #####
// 10	fb ######
// 11	48 #######
// 12	d2 ######## iBeacon proximity uuid
// 13	b0 ########
// 14	60 #######
// 15	d0 ######
// 16	f5 #####
// 17	a7 ####
// 18	10 ###
// 19	96 ##
// 20	e0 #
// 21	00
// 22	00 # major
// 23	00
// 24	00 # minor
// 25	c5 # The 2's complement of the calibrated Tx Power

	private final byte[] mData;
	private final int mTxPower;

	public ManufacturerDataIBeacon(byte[] data){
		mData = data;
		mTxPower = 0;
	}


	// Code taken from: http://stackoverflow.com/questions/20416218/understanding-ibeacon-distancing
	protected static double calculateAccuracy(int txPower, double rssi) {
		if (rssi == 0) {
			return -1.0; // if we cannot determine accuracy, return -1.
		}

		double ratio = rssi*1.0/txPower;
		if (ratio < 1.0) {
			return Math.pow(ratio,10);
		}
		else {
			double accuracy =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
			return accuracy;
		}
	}
}
