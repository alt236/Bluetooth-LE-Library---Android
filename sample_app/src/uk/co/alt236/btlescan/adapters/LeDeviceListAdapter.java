package uk.co.alt236.btlescan.adapters;

import java.util.ArrayList;
import java.util.List;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
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
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            final BluetoothLeDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();

            if (deviceName != null && deviceName.length() > 0){
                viewHolder.deviceName.setText(deviceName);
            } else{
                viewHolder.deviceName.setText(R.string.unknown_device);
            }

            if (IBeaconUtils.isThisAnIBeacon(device)){
            	viewHolder.deviceIcon.setImageResource(R.drawable.ic_bluetooth_ibeacon);
            } else {
            	viewHolder.deviceIcon.setImageResource(R.drawable.ic_bluetooth);
            }

            viewHolder.deviceAddress.setText(device.getAddress());
            viewHolder.deviceRssi.setText(String.valueOf(device.getRssi()) + "db");
            return view;
        }

        static class ViewHolder {
            TextView deviceName;
            TextView deviceAddress;
            TextView deviceRssi;
            ImageView deviceIcon;
        }

    }