package uk.co.alt236.bluetoothlelib.device;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecordStore;
import uk.co.alt236.bluetoothlelib.util.AdRecordUtils;
import uk.co.alt236.bluetoothlelib.util.ByteUtils;
import uk.co.alt236.bluetoothlelib.util.LimitedLinkHashMap;
import android.bluetooth.BluetoothClass;
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
		return resolveBluetoothClass(mDevice.getBluetoothClass().getDeviceClass());
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

	private static String resolveBluetoothClass(int btClass){
		switch (btClass){
		case BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER:
			return "A/V, Camcorder";
		case BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO:
			return "A/V, Car Audio";
		case BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE:
			return "A/V, Handsfree";
		case BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES:
			return "A/V, Headphones";
		case BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO:
			return "A/V, HiFi Audio";
		case BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER:
			return "A/V, Loudspeaker";
		case BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE:
			return "A/V, Microphone";
		case BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO:
			return "A/V, Portable Audio";
		case BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX:
			return "A/V, Set Top Box";
		case BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED:
			return "A/V, Uncategorized";
		case BluetoothClass.Device.AUDIO_VIDEO_VCR:
			return "A/V, VCR";
		case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA:
			return "A/V, Video Camera";
		case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING:
			return "A/V, Video Conferencing";
		case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER:
			return "A/V, Video Display and Loudspeaker";
		case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY:
			return "A/V, Video Gaming Toy";
		case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR:
			return "A/V, Video Monitor";
		case BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET:
			return "A/V, Video Wearable Headset";
		case BluetoothClass.Device.COMPUTER_DESKTOP:
			return "Computer, Desktop";
		case BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA:
			return "Computer, Handheld PC/PDA";
		case BluetoothClass.Device.COMPUTER_LAPTOP:
			return "Computer, Laptop";
		case BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA:
			return "Computer, Palm Size PC/PDA";
		case BluetoothClass.Device.COMPUTER_SERVER:
			return "Computer, Server";
		case BluetoothClass.Device.COMPUTER_UNCATEGORIZED:
			return "Computer, Uncategorized";
		case BluetoothClass.Device.COMPUTER_WEARABLE:
			return "Computer, Wearable";
		case BluetoothClass.Device.HEALTH_BLOOD_PRESSURE:
			return "Health, Blood Pressure";
		case BluetoothClass.Device.HEALTH_DATA_DISPLAY:
			return "Health, Data Display";
		case BluetoothClass.Device.HEALTH_GLUCOSE:
			return "Health, Glucose";
		case BluetoothClass.Device.HEALTH_PULSE_OXIMETER :
			return "Health, Pulse Oximeter";
		case BluetoothClass.Device.HEALTH_PULSE_RATE 	:
			return "Health, Pulse Rate";
		case BluetoothClass.Device.HEALTH_THERMOMETER :
			return "Health, Thermometer";
		case BluetoothClass.Device.HEALTH_UNCATEGORIZED :
			return "Health, Uncategorized";
		case BluetoothClass.Device.HEALTH_WEIGHING:
			return "Health, Weighting";
		case BluetoothClass.Device.PHONE_CELLULAR:
			return "Phone, Cellular";
		case BluetoothClass.Device.PHONE_CORDLESS:
			return "Phone, Cordless";
		case BluetoothClass.Device.PHONE_ISDN:
			return "Phone, ISDN";
		case BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY:
			return "Phone, Modem or Gateway";
		case BluetoothClass.Device.PHONE_SMART:
			return "Phone, Smart";
		case BluetoothClass.Device.PHONE_UNCATEGORIZED:
			return "Phone, Uncategorized";
		case BluetoothClass.Device.TOY_CONTROLLER:
			return "Toy, Controller";
		case BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE:
			return "Toy, Doll/Action Figure";
		case BluetoothClass.Device.TOY_GAME:
			return "Toy, Game";
		case BluetoothClass.Device.TOY_ROBOT:
			return "Toy, Robot";
		case BluetoothClass.Device.TOY_UNCATEGORIZED:
			return "Toy, Uncategorized";
		case BluetoothClass.Device.TOY_VEHICLE:
			return "Toy, Vehicle";
		case BluetoothClass.Device.WEARABLE_GLASSES:
			return "Wearable, Glasses";
		case BluetoothClass.Device.WEARABLE_HELMET:
			return "Wearable, Helmet";
		case BluetoothClass.Device.WEARABLE_JACKET:
			return "Wearable, Jacket";
		case BluetoothClass.Device.WEARABLE_PAGER:
			return "Wearable, Pager";
		case BluetoothClass.Device.WEARABLE_UNCATEGORIZED:
			return "Wearable, Uncategorized";
		case BluetoothClass.Device.WEARABLE_WRIST_WATCH:
			return "Wearable, Wrist Watch";
		default:
			return "Unknown, Unknown (class=" + btClass +")";
		}
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
