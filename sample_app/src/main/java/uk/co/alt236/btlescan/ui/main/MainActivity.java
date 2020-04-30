package uk.co.alt236.btlescan.ui.main;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconType;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconUtils;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.containers.BluetoothLeDeviceStore;
import uk.co.alt236.btlescan.ui.common.Navigation;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewBinderCore;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.main.recyclerview.model.IBeaconItem;
import uk.co.alt236.btlescan.ui.main.recyclerview.model.LeDeviceItem;
import uk.co.alt236.btlescan.ui.main.share.Sharer;
import uk.co.alt236.btlescan.util.BluetoothAdapterWrapper;
import uk.co.alt236.btlescan.util.BluetoothLeScanner;

public class MainActivity extends AppCompatActivity {
    private RecyclerViewBinderCore mCore;
    private BluetoothAdapterWrapper mBluetoothAdapterWrapper;
    private BluetoothLeScanner mScanner;
    private BluetoothLeDeviceStore mDeviceStore;
    private DeviceRecyclerAdapter mRecyclerAdapter;
    private View view;

    private final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

            final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());
            mDeviceStore.addDevice(deviceLe);
            final List<RecyclerViewItem> itemList = new ArrayList<>();

            for (final BluetoothLeDevice leDevice : mDeviceStore.getDeviceList()) {
                if (BeaconUtils.getBeaconType(leDevice) == BeaconType.IBEACON) {
                    itemList.add(new IBeaconItem(new IBeaconDevice(leDevice)));
                } else {
                    itemList.add(new LeDeviceItem(leDevice));
                }
            }

            runOnUiThread(() -> {
                mRecyclerAdapter.setData(itemList);
                view.updateItemCount(mRecyclerAdapter.getItemCount());
            });
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = new View(this);

        mCore = RecyclerViewCoreFactory.create(this, new Navigation(this));
        mDeviceStore = new BluetoothLeDeviceStore();
        mBluetoothAdapterWrapper = new BluetoothAdapterWrapper(this.getApplicationContext());
        mScanner = new BluetoothLeScanner(mBluetoothAdapterWrapper, mLeScanCallback);
        view.updateItemCount(0);
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

        if (mRecyclerAdapter != null && mRecyclerAdapter.getItemCount() > 0) {
            menu.findItem(R.id.menu_share).setVisible(true);
        } else {
            menu.findItem(R.id.menu_share).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                startScanPrepare();
                break;
            case R.id.menu_stop:
                mScanner.stopScan("menu");
                invalidateOptionsMenu();
                break;
            case R.id.menu_about:
                DialogFactory.createAboutDialog(this).show();
                break;
            case R.id.menu_share:
                new Sharer().shareDataAsEmail(this, mDeviceStore);
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanner.stopScan("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        view.setBluetoothEnabled(mBluetoothAdapterWrapper.isBluetoothOn());
        view.setBluetoothLeSupported(mBluetoothAdapterWrapper.isBluetoothLeSupported());
        invalidateOptionsMenu();
    }

    private void startScanPrepare() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final String permission;
            final int message;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permission = Manifest.permission.ACCESS_FINE_LOCATION;
                message = R.string.permission_not_granted_fine_location;
            } else {
                permission = Manifest.permission.ACCESS_COARSE_LOCATION;
                message = R.string.permission_not_granted_coarse_location;
            }

            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                    new String[]{permission}, new PermissionsResultAction() {

                        @Override
                        public void onGranted() {
                            startScan();
                        }

                        @Override
                        public void onDenied(String permission) {
                            Toast.makeText(MainActivity.this,
                                    message,
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
        } else {
            startScan();
        }
    }

    private void startScan() {
        final boolean isBluetoothOn = mBluetoothAdapterWrapper.isBluetoothOn();
        final boolean isBluetoothLePresent = mBluetoothAdapterWrapper.isBluetoothLeSupported();
        if (!isBluetoothLePresent) {
            Toast.makeText(this, "This device does not support BTLE. Cannot scan...", Toast.LENGTH_LONG).show();
            return;
        }

        mDeviceStore.clear();
        view.updateItemCount(0);

        mRecyclerAdapter = new DeviceRecyclerAdapter(mCore);
        view.setListAdapter(mRecyclerAdapter);

        mBluetoothAdapterWrapper.askUserToEnableBluetoothIfNeeded(this);
        if (isBluetoothOn) {
            mScanner.startScan();
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}