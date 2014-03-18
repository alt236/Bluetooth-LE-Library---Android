package uk.co.alt236.btlescan.activities;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.adapters.LeDeviceListAdapter;
import uk.co.alt236.btlescan.containers.BluetoothLeDeviceStore;
import uk.co.alt236.btlescan.util.BluetoothLeScanner;
import uk.co.alt236.btlescan.util.BluetoothUtils;
import uk.co.alt236.btlescan.views.RadarView;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ListActivity {
	@InjectView(R.id.tvBluetoothLe) TextView mTvBluetoothLeStatus;
	@InjectView(R.id.tvBluetoothStatus) TextView mTvBluetoothStatus;
	@InjectView(R.id.radarView) RadarView mRadarView;

	private BluetoothUtils mBluetoothUtils;
	private BluetoothLeScanner mScanner;
	private LeDeviceListAdapter mLeDeviceListAdapter;
	private BluetoothLeDeviceStore mDeviceStore;

	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

			final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mDeviceStore.addDevice(deviceLe);
					mLeDeviceListAdapter.clear();
					mLeDeviceListAdapter.addAll(mDeviceStore.getDeviceList());
					mLeDeviceListAdapter.notifyDataSetChanged();
				}
			});
		}
	};

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		final BluetoothLeDevice device = mLeDeviceListAdapter.getItem(position);
		if (device == null) return;


		final Intent intent = new Intent(this, DetailsActivity.class);
		intent.putExtra(DetailsActivity.EXTRA_DEVICE, device);

		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);

		mDeviceStore = new BluetoothLeDeviceStore();
		mBluetoothUtils = new BluetoothUtils(this);
		mScanner = new BluetoothLeScanner(mLeScanCallback, mBluetoothUtils);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		if (!mScanner.isScanning()) {
			menu.findItem(R.id.menu_stop).setVisible(false);
			menu.findItem(R.id.menu_scan).setVisible(true);
			menu.findItem(R.id.menu_refresh).setActionView(null);
		} else {
			menu.findItem(R.id.menu_stop).setVisible(true);
			menu.findItem(R.id.menu_scan).setVisible(false);
			menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_progress_indeterminate);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_scan:
			startScan();
			break;
		case R.id.menu_stop:
			mScanner.scanLeDevice(-1, false);
			invalidateOptionsMenu();
			break;
		}
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		mScanner.scanLeDevice(-1, false);
	}

	@Override
	public void onResume(){
		super.onResume();
		final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
		final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();

		if(mIsBluetoothOn){
			mTvBluetoothStatus.setText(R.string.on);
		} else {
			mTvBluetoothStatus.setText(R.string.off);
		}

		if(mIsBluetoothLePresent){
			mTvBluetoothLeStatus.setText(R.string.supported);
		} else {
			mTvBluetoothLeStatus.setText(R.string.not_supported);
		}

		invalidateOptionsMenu();
	}

	private void startScan(){
		final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
		final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();
		mDeviceStore.clear();

		mLeDeviceListAdapter = new LeDeviceListAdapter(this, mDeviceStore.getDeviceList());
		setListAdapter(mLeDeviceListAdapter);

		mBluetoothUtils.askUserToEnableBluetoothIfNeeded();
		if(mIsBluetoothOn && mIsBluetoothLePresent){
			mScanner.scanLeDevice(-1, true);
			invalidateOptionsMenu();
		}
	}

}
