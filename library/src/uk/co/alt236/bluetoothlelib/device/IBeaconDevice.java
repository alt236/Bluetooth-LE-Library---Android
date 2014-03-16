package uk.co.alt236.bluetoothlelib.device;

import uk.co.alt236.bluetoothlelib.device.mfdata.IBeaconManufacturerData;
import uk.co.alt236.bluetoothlelib.util.IBeaconUtils;
import uk.co.alt236.bluetoothlelib.util.IBeaconUtils.IBeaconDistanceDescriptor;
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;

public class IBeaconDevice extends BluetoothLeDevice{
	private final IBeaconManufacturerData mIBeaconData;
	
	public IBeaconDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
		super(device, rssi, scanRecord, 0);
		mIBeaconData = new IBeaconManufacturerData(this);
	}

	public IBeaconDevice(BluetoothDevice device, int rssi, byte[] scanRecord, long timestamp){
		super(device, rssi, scanRecord, timestamp);
		mIBeaconData = new IBeaconManufacturerData(this);
	}

	public IBeaconDevice(BluetoothLeDevice device){
		this(device.getDevice(), device.getRssi(), device.getScanRecord(), device.getTimestamp());
	}
	
	private IBeaconDevice(Parcel in) {
		super(in);
		mIBeaconData = new IBeaconManufacturerData(this);
	}
	
	public double getAccuracy(){
		return IBeaconUtils.calculateAccuracy(getCalibratedTxPower(), getRssi());
	}
	
	public int getCalibratedTxPower(){
		return getIBeaconData().getCalibratedTxPower();
	}
	
	public int getCompanyIdentifier(){
		return getIBeaconData().getCompanyIdentifier();
	}
	
	public IBeaconDistanceDescriptor getDistanceDescriptor(){
		return IBeaconUtils.getDistanceDescriptor(getAccuracy());
	}
	
	public IBeaconManufacturerData getIBeaconData(){
		return mIBeaconData;
	}
	
	public int getMajor(){
		return getIBeaconData().getMajor();
	}
	
	public int getMinor(){
		return getIBeaconData().getMinor();
	}
	
	public String getUUID(){
		return getIBeaconData().getUUID();
	}
}
