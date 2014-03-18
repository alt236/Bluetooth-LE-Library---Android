package uk.co.alt236.bluetoothlelib.device;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecordStore;
import uk.co.alt236.bluetoothlelib.resolvers.BluetoothClassResolver;
import uk.co.alt236.bluetoothlelib.util.AdRecordUtils;
import uk.co.alt236.bluetoothlelib.util.ByteUtils;
import uk.co.alt236.bluetoothlelib.util.LimitedLinkHashMap;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * This is a wrapper around the default BluetoothDevice object
 * As BluetoothDevice is final it cannot be extended, so to get it you
 * need to call {@link #getDevice()} method.
 *
 * @author Alexandros Schillings
 */
public class BluetoothLeDevice implements Parcelable{
	private static final String PARCEL_EXTRA_BLUETOOTH_DEVICE = "bluetooth_device";
	private static final String PARCEL_EXTRA_CURRENT_RSSI = "current_rssi";
	private static final String PARCEL_EXTRA_CURRENT_TIMESTAMP = "current_timestamp";
	private static final String PARCEL_EXTRA_DEVICE_RSSI_LOG = "device_rssi_log";
	private static final String PARCEL_EXTRA_DEVICE_SCANRECORD = "device_scanrecord";
	private static final String PARCEL_EXTRA_DEVICE_SCANRECORD_STORE = "device_scanrecord_store";
	private static final String PARCEL_EXTRA_FIRST_RSSI = "device_first_rssi";
	private static final String PARCEL_EXTRA_FIRST_TIMESTAMP = "first_timestamp";

	private static final int MAX_RSSI_LOG_SIZE = 10;
	private static final long LOG_INVALIDATION_THRESHOLD = 10 * 1000;

	private final AdRecordStore mRecordStore;
	private final BluetoothDevice mDevice;
	private final Map<Long, Integer> mRssiLog;
	private final byte[] mScanRecord;
	private final int mFirstRssi;
	private final long mFirstTimestamp;
	private int mCurrentRssi;
	private long mCurrentTimestamp;

	public static final Parcelable.Creator<BluetoothLeDevice> CREATOR = new Parcelable.Creator<BluetoothLeDevice>() {
		public BluetoothLeDevice createFromParcel(Parcel in) {
			return new BluetoothLeDevice(in);
		}

		public BluetoothLeDevice[] newArray(int size) {
			return new BluetoothLeDevice[size];
		}
	};

	public BluetoothLeDevice(BluetoothDevice device, int rssi, byte[] scanRecord, long timestamp){
		mDevice = device;
		mFirstRssi = rssi;
		mFirstTimestamp = timestamp;
		mRecordStore = new AdRecordStore(AdRecordUtils.parseScanRecordAsSparseArray(scanRecord));
		mScanRecord = scanRecord;
		mRssiLog = Collections.synchronizedMap(
				new LimitedLinkHashMap<Long, Integer>(MAX_RSSI_LOG_SIZE));
		updateRssiReading(timestamp, rssi);
	}

	public double getRunningAverageRssi(){
		final Collection<Integer> values = mRssiLog.values();
		int sum = 0;

		for(Integer value: values){
			sum += value.intValue();
		}

		return sum/values.size();
	}

	@SuppressWarnings("unchecked")
	protected BluetoothLeDevice(Parcel in) {
		final Bundle b = in.readBundle(getClass().getClassLoader());

		mCurrentRssi = b.getInt(PARCEL_EXTRA_CURRENT_RSSI, 0);
		mCurrentTimestamp = b.getLong(PARCEL_EXTRA_CURRENT_TIMESTAMP, 0);
		mDevice = b.getParcelable(PARCEL_EXTRA_BLUETOOTH_DEVICE);
		mFirstRssi = b.getInt(PARCEL_EXTRA_FIRST_RSSI, 0);
		mFirstTimestamp = b.getLong(PARCEL_EXTRA_FIRST_TIMESTAMP, 0);
		mRecordStore = b.getParcelable(PARCEL_EXTRA_DEVICE_SCANRECORD_STORE);
		mScanRecord = b.getByteArray(PARCEL_EXTRA_DEVICE_SCANRECORD);

		mRssiLog = Collections.synchronizedMap(
				(Map<Long, Integer>) b.getSerializable(PARCEL_EXTRA_DEVICE_RSSI_LOG));
    }

	public synchronized void updateRssiReading(long timestamp, int rssiReading){
		addToRssiLog(timestamp, rssiReading);
	}

	private void addToRssiLog(long timestamp, int rssiReading){

		if(timestamp - mCurrentTimestamp > LOG_INVALIDATION_THRESHOLD){
			mRssiLog.clear();
		}

		mCurrentRssi = rssiReading;
		mCurrentTimestamp = timestamp;
		mRssiLog.put(timestamp, rssiReading);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BluetoothLeDevice other = (BluetoothLeDevice) obj;
		if (mCurrentRssi != other.mCurrentRssi)
			return false;
		if (mCurrentTimestamp != other.mCurrentTimestamp)
			return false;
		if (mDevice == null) {
			if (other.mDevice != null)
				return false;
		} else if (!mDevice.equals(other.mDevice))
			return false;
		if (mFirstRssi != other.mFirstRssi)
			return false;
		if (mFirstTimestamp != other.mFirstTimestamp)
			return false;
		if (mRecordStore == null) {
			if (other.mRecordStore != null)
				return false;
		} else if (!mRecordStore.equals(other.mRecordStore))
			return false;
		if (mRssiLog == null) {
			if (other.mRssiLog != null)
				return false;
		} else if (!mRssiLog.equals(other.mRssiLog))
			return false;
		if (!Arrays.equals(mScanRecord, other.mScanRecord))
			return false;
		return true;
	}

	public String getAddress(){
		return mDevice.getAddress();
	}

	public AdRecordStore getAdRecordStore(){
		return mRecordStore;
	}

	public String getBluetoothDeviceBondState(){
		return resolveBondingState(mDevice.getBondState());
	}

	public String getBluetoothDeviceClassName(){
		return BluetoothClassResolver.resolveDeviceClass(mDevice.getBluetoothClass().getDeviceClass());
	}

	public BluetoothDevice getDevice() {
		return mDevice;
	}

	public int getFirstRssi(){
		return mFirstRssi;
	}

	public long getFirstTimestamp(){
		return mFirstTimestamp;
	}

	public String getName(){
		return mDevice.getName();
	}

	public int getRssi() {
		return mCurrentRssi;
	}

	public byte[] getScanRecord() {
		return mScanRecord;
	}

	public long getTimestamp(){
		return mCurrentTimestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mCurrentRssi;
		result = prime * result + (int) (mCurrentTimestamp ^ (mCurrentTimestamp >>> 32));
		result = prime * result + ((mDevice == null) ? 0 : mDevice.hashCode());
		result = prime * result + mFirstRssi;
		result = prime * result + (int) (mFirstTimestamp ^ (mFirstTimestamp >>> 32));
		result = prime * result + ((mRecordStore == null) ? 0 : mRecordStore.hashCode());
		result = prime * result + ((mRssiLog == null) ? 0 : mRssiLog.hashCode());
		result = prime * result + Arrays.hashCode(mScanRecord);
		return result;
	}

	@Override
	public String toString() {
		return "BluetoothLeDevice [mDevice=" + mDevice + ", mRssi=" + mFirstRssi + ", mScanRecord=" + ByteUtils.byteArrayToHexString(mScanRecord) + ", mRecordStore=" + mRecordStore + ", getBluetoothDeviceBondState()=" + getBluetoothDeviceBondState() + ", getBluetoothDeviceClassName()=" + getBluetoothDeviceClassName() + "]";
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		final Bundle b = new Bundle(getClass().getClassLoader());

		b.putByteArray(PARCEL_EXTRA_DEVICE_SCANRECORD, mScanRecord);

		b.putInt(PARCEL_EXTRA_FIRST_RSSI, mFirstRssi);
		b.putInt(PARCEL_EXTRA_CURRENT_RSSI, mCurrentRssi);

		b.putLong(PARCEL_EXTRA_FIRST_TIMESTAMP, mFirstTimestamp);
		b.putLong(PARCEL_EXTRA_CURRENT_TIMESTAMP, mCurrentTimestamp);

		b.putParcelable(PARCEL_EXTRA_BLUETOOTH_DEVICE, mDevice);
		b.putParcelable(PARCEL_EXTRA_DEVICE_SCANRECORD_STORE, mRecordStore);
		b.putSerializable(PARCEL_EXTRA_DEVICE_RSSI_LOG, (Serializable) mRssiLog);

		parcel.writeBundle(b);
	}

	private static String resolveBondingState(int bondState){
		switch (bondState){
		case BluetoothDevice.BOND_BONDED:
			return "Paired";
		case BluetoothDevice.BOND_BONDING:
			return "Pairing";
		case BluetoothDevice.BOND_NONE:
			return "Unbonded";
		default:
			return "Unknown";
		}
	}
}
