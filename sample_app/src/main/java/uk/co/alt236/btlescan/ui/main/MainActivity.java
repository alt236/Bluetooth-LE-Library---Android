package uk.co.alt236.btlescan.ui.main;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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
import uk.co.alt236.btlescan.util.BluetoothLeScanner;
import uk.co.alt236.btlescan.util.BluetoothUtils;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.tvBluetoothLe)
    protected TextView mTvBluetoothLeStatus;
    @Bind(R.id.tvBluetoothStatus)
    protected TextView mTvBluetoothStatus;
    @Bind(R.id.tvItemCount)
    protected TextView mTvItemCount;
    @Bind(android.R.id.list)
    protected RecyclerView mList;
    @Bind(android.R.id.empty)
    protected View mEmpty;

    private RecyclerViewBinderCore mCore;
    private BluetoothUtils mBluetoothUtils;
    private BluetoothLeScanner mScanner;
    private BluetoothLeDeviceStore mDeviceStore;
    private DeviceRecyclerAdapter mRecyclerAdapter;

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

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRecyclerAdapter.setData(itemList);
                    updateItemCount(mRecyclerAdapter.getItemCount());
                }
            });
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCore = RecyclerViewCoreFactory.create(this, new Navigation(this));
        mList.setLayoutManager(new LinearLayoutManager(this));
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
                mScanner.scanLeDevice(-1, false);
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
        mScanner.scanLeDevice(-1, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mBluetoothUtils.isBluetoothOn()) {
            mTvBluetoothStatus.setText(R.string.on);
        } else {
            mTvBluetoothStatus.setText(R.string.off);
        }

        if (mBluetoothUtils.isBluetoothLeSupported()) {
            mTvBluetoothLeStatus.setText(R.string.supported);
        } else {
            mTvBluetoothLeStatus.setText(R.string.not_supported);
        }

        invalidateOptionsMenu();
    }


    private void startScanPrepare() {
        //
        // The COARSE_LOCATION permission is only needed after API 23 to do a BTLE scan
        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, new PermissionsResultAction() {

                        @Override
                        public void onGranted() {
                            startScan();
                        }

                        @Override
                        public void onDenied(String permission) {
                            Toast.makeText(MainActivity.this,
                                    R.string.permission_not_granted_coarse_location,
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
        } else {
            startScan();
        }
    }

    private void startScan() {
        final boolean isBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean isBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();
        mDeviceStore.clear();
        updateItemCount(0);

        mRecyclerAdapter = new DeviceRecyclerAdapter(mCore);
        mList.setAdapter(mRecyclerAdapter);

        mBluetoothUtils.askUserToEnableBluetoothIfNeeded();
        if (isBluetoothOn && isBluetoothLePresent) {
            mScanner.scanLeDevice(-1, true);
            invalidateOptionsMenu();
        }
    }

    private void updateItemCount(final int count) {
        mTvItemCount.setText(
                getString(
                        R.string.formatter_item_count,
                        String.valueOf(count)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
