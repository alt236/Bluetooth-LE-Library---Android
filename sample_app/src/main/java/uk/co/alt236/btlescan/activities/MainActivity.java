package uk.co.alt236.btlescan.activities;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.adapters.LeDeviceListAdapter;
import uk.co.alt236.btlescan.containers.BluetoothLeDeviceStore;
import uk.co.alt236.btlescan.util.BluetoothLeScanner;
import uk.co.alt236.btlescan.util.BluetoothUtils;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
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
	@InjectView(R.id.tvItemCount) TextView mTvItemCount;

	private BluetoothUtils mBluetoothUtils;
	private BluetoothLeScanner mScanner;
	private LeDeviceListAdapter mLeDeviceListAdapter;
	private BluetoothLeDeviceStore mDeviceStore;

	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

			final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());
			mDeviceStore.addDevice(deviceLe);
			final EasyObjectCursor<BluetoothLeDevice> c = mDeviceStore.getDeviceCursor();

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mLeDeviceListAdapter.swapCursor(c);
					updateItemCount(mLeDeviceListAdapter.getCount());
				}
			});
		}
	};

	private void updateItemCount(final int count){
		mTvItemCount.setText(
				getString(
						R.string.formatter_item_count,
						String.valueOf(count)));
	}

	private void displayAboutDialog(){
		// REALLY REALLY LAZY LINKIFIED DIALOG
		final int paddingSizeDp = 5;
		final float scale = getResources().getDisplayMetrics().density;
		final int dpAsPixels = (int) (paddingSizeDp * scale + 0.5f);

		final TextView textView=new TextView(this);
		final SpannableString text = new SpannableString(getString(R.string.about_dialog_text));

		textView.setText(text);
		textView.setAutoLinkMask(RESULT_OK);
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		textView.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

		Linkify.addLinks(text, Linkify.ALL);
		new AlertDialog.Builder(this)
		.setTitle(R.string.menu_about)
		.setCancelable(false)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int id) {}
		})
		.setView(textView)
		.show();
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);

		mDeviceStore = new BluetoothLeDeviceStore();
		mBluetoothUtils = new BluetoothUtils(this);
		mScanner = new BluetoothLeScanner(mLeScanCallback, mBluetoothUtils);
		updateItemCount(0);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
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

		if(getListView().getCount() > 0){
			menu.findItem(R.id.menu_share).setVisible(true);
		} else {
			menu.findItem(R.id.menu_share).setVisible(false);
		}

		return true;
	}

	@Override
	protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
		final BluetoothLeDevice device = (BluetoothLeDevice) mLeDeviceListAdapter.getItem(position);
		if (device == null) return;


		final Intent intent = new Intent(this, DeviceDetailsActivity.class);
		intent.putExtra(DeviceDetailsActivity.EXTRA_DEVICE, device);

		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_scan:
			startScan();
			break;
		case R.id.menu_stop:
			mScanner.scanLeDevice(-1, false);
			invalidateOptionsMenu();
			break;
		case R.id.menu_about:
			displayAboutDialog();
			break;
		case R.id.menu_share:
			mDeviceStore.shareDataAsEmail(this);
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
		updateItemCount(0);

		mLeDeviceListAdapter = new LeDeviceListAdapter(this, mDeviceStore.getDeviceCursor());
		setListAdapter(mLeDeviceListAdapter);

		mBluetoothUtils.askUserToEnableBluetoothIfNeeded();
		if(mIsBluetoothOn && mIsBluetoothLePresent){
			mScanner.scanLeDevice(-1, true);
			invalidateOptionsMenu();
		}
	}

}
