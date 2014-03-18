package uk.co.alt236.btlescan.adapters;

import java.util.List;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.IBeaconDevice;
import uk.co.alt236.bluetoothlelib.util.IBeaconUtils;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.util.Constants;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
// Adapter for holding devices found through scanning.
public class LeDeviceListAdapter extends ArrayAdapter<BluetoothLeDevice> {
	private final LayoutInflater mInflator;
	private final Activity mActivity;

	public LeDeviceListAdapter(Activity activity, List<BluetoothLeDevice> list) {
		super(activity, R.layout.list_item_device, list);
		mInflator = activity.getLayoutInflater();
		mActivity = activity;
	}

//	public void addDevice(BluetoothLeDevice device) {
//		final int position = mLeDevices.indexOf(device);
//		if(position == -1){
//			mLeDevices.add(device);
//		} else {
//			mLeDevices.set(position, device);
//		}
//	}

	public void replaceData(List<BluetoothLeDevice> list){
		clear();
		addAll(list);
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

		final BluetoothLeDevice device = getItem(i);
		final String deviceName = device.getName();
		final double rssi = device.getRssi();

		if (deviceName != null && deviceName.length() > 0){
			viewHolder.deviceName.setText(deviceName);
		} else{
			viewHolder.deviceName.setText(R.string.unknown_device);
		}

		if (IBeaconUtils.isThisAnIBeacon(device)){
			final IBeaconDevice iBeacon = new IBeaconDevice(device);
			final String accuracy = Constants.DOUBLE_TWO_DIGIT_ACCURACY.format(iBeacon.getAccuracy());

			viewHolder.deviceIcon.setImageResource(R.drawable.ic_device_ibeacon);
			viewHolder.ibeaconSection.setVisibility(View.VISIBLE);
			viewHolder.ibeaconMajor.setText(String.valueOf(iBeacon.getMajor()));
			viewHolder.ibeaconMinor.setText(String.valueOf(iBeacon.getMinor()));
			viewHolder.ibeaconTxPower.setText(String.valueOf(iBeacon.getCalibratedTxPower()));
			viewHolder.ibeaconUUID.setText(iBeacon.getUUID());
			viewHolder.ibeaconDistance.setText(
					mActivity.getString(R.string.formatter_meters, accuracy));
			viewHolder.ibeaconDistanceDescriptor.setText(iBeacon.getDistanceDescriptor().toString());
		} else {
			viewHolder.deviceIcon.setImageResource(R.drawable.ic_bluetooth);
			viewHolder.ibeaconSection.setVisibility(View.GONE);
		}

		final String rssiString = mActivity.getString(R.string.formatter_db, String.valueOf(rssi));
		final String runningAverageRssiString = mActivity.getString(R.string.formatter_db, String.valueOf(device.getRunningAverageRssi()));

		viewHolder.deviceLastUpdated.setText(
				android.text.format.DateFormat.format(
						Constants.TIME_FORMAT, new java.util.Date(device.getTimestamp())));
		viewHolder.deviceAddress.setText(device.getAddress());
		viewHolder.deviceRssi.setText(rssiString + " / " + runningAverageRssiString);
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