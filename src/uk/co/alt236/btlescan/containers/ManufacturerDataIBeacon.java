package uk.co.alt236.btlescan.containers;

public final class ManufacturerDataIBeacon {
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
