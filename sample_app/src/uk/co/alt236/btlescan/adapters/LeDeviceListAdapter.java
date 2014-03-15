package uk.co.alt236.btlescan.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.mfdata.IBeaconManufacturerData;
import uk.co.alt236.bluetoothlelib.util.IBeaconUtils;
import uk.co.alt236.btlescan.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
// Adapter for holding devices found through scanning.
public class LeDeviceListAdapter extends BaseAdapter {
	private static final DecimalFormat DOUBLE_TWO_DIGIT_ACCURACY = new DecimalFormat("#.##");
	private static final String TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
	
	private final List<BluetoothLeDevice> mLeDevices;
	private final LayoutInflater mInflator;

	public LeDeviceListAdapter(Activity activity) {
		super();
		mLeDevices = new ArrayList<BluetoothLeDevice>();
		mInflator = activity.getLayoutInflater();
	}

	public void addDevice(BluetoothLeDevice device) {
		final int position = mLeDevices.indexOf(device);
		if(position == -1){
			mLeDevices.add(device);
		} else {
			mLeDevices.set(position, device);
		}
	}

	public BluetoothLeDevice getDevice(int position) {
		return mLeDevices.get(position);
	}

	public void clear() {
		mLeDevices.clear();
	}

	@Override
	public int getCount() {
		return mLeDevices.size();
	}

	@Override
	public Object getItem(int i) {
		return mLeDevices.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder;
		// General ListView optimization code.
		if (view == null) {
			view = mInflator.inflate(R.layout.list_item_device, null);
			viewHolder = new ViewHolder();
			viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
			viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
			viewHolder.deviceRssi = (TextView) view.findViewById(R.id.device_rssi);
			viewHolder.deviceIcon = (ImageView) view.findViewById(R.id.device_icon);
			viewHolder.deviceLastUpdated = (TextView) view.findViewById(R.id.device_last_update);
			viewHolder.ibeaconMajor = (TextView) view.findViewById(R.id.ibeacon_major);
			viewHolder.ibeaconMinor = (TextView) view.findViewById(R.id.ibeacon_minor);
			viewHolder.ibeaconDistance = (TextView) view.findViewById(R.id.ibeacon_distance);
			viewHolder.ibeaconUUID = (TextView) view.findViewById(R.id.ibeacon_uuid);
			viewHolder.ibeaconTxPower = (TextView) view.findViewById(R.id.ibeacon_tx_power);
			viewHolder.ibeaconSection = (View) view.findViewById(R.id.ibeacon_section);
			viewHolder.ibeaconDistanceDescriptor = (TextView) view.findViewById(R.id.ibeacon_distance_descriptor);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		final BluetoothLeDevice device = mLeDevices.get(i);
		final String deviceName = device.getName();
		final double rssi = device.getRssi();

		if (deviceName != null && deviceName.length() > 0){
			viewHolder.deviceName.setText(deviceName);
		} else{
			viewHolder.deviceName.setText(R.string.unknown_device);
		}

		if (IBeaconUtils.isThisAnIBeacon(device)){
			final IBeaconManufacturerData data = new IBeaconManufacturerData(device);
			final double accuracy = data.getAccuracy(rssi);

			viewHolder.deviceIcon.setImageResource(R.drawable.ic_device_ibeacon);
			viewHolder.ibeaconSection.setVisibility(View.VISIBLE);
			viewHolder.ibeaconMajor.setText(String.valueOf(data.getMajor()));
			viewHolder.ibeaconMinor.setText(String.valueOf(data.getMinor()));
			viewHolder.ibeaconTxPower.setText(String.valueOf(data.getCalibratedTxPower()));
			viewHolder.ibeaconUUID.setText(data.getUUID());
			viewHolder.ibeaconDistance.setText(DOUBLE_TWO_DIGIT_ACCURACY.format(accuracy) + "m");
			viewHolder.ibeaconDistanceDescriptor.setText(data.getDistanceDescriptor(accuracy));
		} else {
			viewHolder.deviceIcon.setImageResource(R.drawable.ic_bluetooth);
			viewHolder.ibeaconSection.setVisibility(View.GONE);
		}

		viewHolder.deviceLastUpdated.setText(
				android.text.format.DateFormat.format(TIME_FORMAT, new java.util.Date(device.getTimestamp())));
		viewHolder.deviceAddress.setText(device.getAddress());
		viewHolder.deviceRssi.setText(String.valueOf(rssi) + "db");
		return view;
	}

	static class ViewHolder {
		TextView deviceName;
		TextView deviceAddress;
		TextView deviceRssi;
		TextView ibeaconUUID;
		TextView ibeaconMajor;
		TextView ibeaconMinor;
		TextView ibeaconTxPower;
		TextView ibeaconDistance;
		TextView ibeaconDistanceDescriptor;
		TextView deviceLastUpdated;
		View ibeaconSection;
		ImageView deviceIcon;
	}

}