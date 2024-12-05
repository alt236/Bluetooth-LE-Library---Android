package uk.co.alt236.btlescan.ui.main;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import dev.alt236.bluetoothlelib.device.BluetoothLeDevice;
import dev.alt236.bluetoothlelib.device.beacon.BeaconType;
import dev.alt236.bluetoothlelib.device.beacon.BeaconUtils;
import dev.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.containers.BluetoothLeDeviceStore;
import uk.co.alt236.btlescan.permission.BluetoothPermissionCheck;
import uk.co.alt236.btlescan.permission.PermissionDeniedDialogFragment;
import uk.co.alt236.btlescan.ui.common.Navigation;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewBinderCore;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.main.recyclerview.model.IBeaconItem;
import uk.co.alt236.btlescan.ui.main.recyclerview.model.LeDeviceItem;
import uk.co.alt236.btlescan.ui.main.share.Sharer;
import uk.co.alt236.btlescan.util.BluetoothAdapterWrapper;
import uk.co.alt236.btlescan.util.BluetoothLeScanner;

public class MainActivity extends AppCompatActivity {
    private final BluetoothPermissionCheck permissionCheck = new BluetoothPermissionCheck();
    private RecyclerViewBinderCore mCore;
    private BluetoothAdapterWrapper mBluetoothAdapterWrapper;
    private BluetoothLeScanner mScanner;
    private BluetoothLeDeviceStore mDeviceStore;
    private DeviceRecyclerAdapter mRecyclerAdapter;
    private View view;

    private final ScanCallback mLeScanCallback = new ScanCallback() {

        @Override
        public void onScanResult(final int callbackType, final ScanResult result) {
            super.onScanResult(callbackType, result);
            onNewDevices(Collections.singletonList(result));
        }

        @Override
        public void onBatchScanResults(final List<ScanResult> results) {
            super.onBatchScanResults(results);
            onNewDevices(results);
        }

        @Override
        public void onScanFailed(final int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("YAY", "Error: " + errorCode);
            mScanner.stopScan("error: " + errorCode);
        }

        private void onNewDevices(final List<ScanResult> results) {
            Log.d("YAY", "New Devices! " + results.size());

            final List<RecyclerViewItem> itemList = new ArrayList<>();
            for (ScanResult result : results) {
                final BluetoothLeDevice deviceLe = new BluetoothLeDevice(
                        result.getDevice(),
                        result.getRssi(),
                        result.getScanRecord().getBytes(),
                        result.getTimestampNanos() / 1000);

                mDeviceStore.addDevice(deviceLe);

                for (final BluetoothLeDevice leDevice : mDeviceStore.getDeviceList()) {
                    if (BeaconUtils.getBeaconType(leDevice) == BeaconType.IBEACON) {
                        itemList.add(new IBeaconItem(new IBeaconDevice(leDevice)));
                    } else {
                        itemList.add(new LeDeviceItem(leDevice));
                    }
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
        final int itemId = item.getItemId();
        if (itemId == R.id.menu_scan) {
            startScanPrepare();
        } else if (itemId == R.id.menu_stop) {
            mScanner.stopScan("menu");
            invalidateOptionsMenu();
        } else if (itemId == R.id.menu_about) {
            DialogFactory.createAboutDialog(this).show();
        } else if (itemId == R.id.menu_share) {
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
        permissionCheck.checkBluetoothPermissions(this, new BluetoothPermissionCheck.PermissionCheckResultCallback() {
            @Override
            public void onSuccess() {
                startScan();
            }

            @Override
            public void onFailure(@NonNull CharSequence message) {
                final DialogFragment dialog = PermissionDeniedDialogFragment.create(message);
                final FragmentManager fm = MainActivity.this.getSupportFragmentManager();
                dialog.show(fm, dialog.getClass().getName());
            }
        });
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

}