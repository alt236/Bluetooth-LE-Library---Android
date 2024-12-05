/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.alt236.btlescan.ui.control;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.services.BluetoothLeService;
import uk.co.alt236.btlescan.services.LocalBinder;
import uk.co.alt236.btlescan.ui.common.IntentReceiverCompat;

/**
 * For a given BLE device, this Activity provides the user interface to connect, display data,
 * and display GATT services and characteristics supported by the device.  The Activity
 * communicates with {@code BluetoothLeService}, which in turn interacts with the
 * Bluetooth LE API.
 */
public class DeviceControlActivity extends AppCompatActivity {
    private static final String EXTRA_DEVICE = DeviceControlActivity.class.getName() + ".EXTRA_DEVICE";
    private final static String TAG = DeviceControlActivity.class.getSimpleName();
    private Exporter mExporter;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothLeService mBluetoothLeService;
    // If a given GATT characteristic is selected, check for supported features.  This sample
    // demonstrates 'Read' and 'Notify' features.  See
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for the complete
    // list of supported characteristic features.
    private final ExpandableListView.OnChildClickListener servicesListClickListner = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(final ExpandableListView parent, final android.view.View v, final int groupPosition, final int childPosition, final long id) {
            final GattDataAdapterFactory.GattDataAdapter adapter =
                    (GattDataAdapterFactory.GattDataAdapter) parent.getExpandableListAdapter();

            final BluetoothGattCharacteristic characteristic =
                    adapter.getBluetoothGattCharacteristic(groupPosition, childPosition);

            final int charaProp = characteristic.getProperties();
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
                if (mNotifyCharacteristic != null) {
                    mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                    mNotifyCharacteristic = null;
                }
                mBluetoothLeService.readCharacteristic(characteristic);
            }
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                mNotifyCharacteristic = characteristic;
                mBluetoothLeService.setCharacteristicNotification(characteristic, true);
            }
            return true;
        }
    };
    private View view;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(final ComponentName componentName, final IBinder service) {
            mBluetoothLeService = ((LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDevice.getAddress());
        }

        @Override
        public void onServiceDisconnected(final ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };
    private BluetoothLeDevice mDevice;
    private State mCurrentState = State.DISCONNECTED;

    private String mExportString;
    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.
    //					      this can be a result of read or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            Log.i(TAG, "Received Action:" + action);

            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                updateConnectionState(State.CONNECTED);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                clearUI();
                updateConnectionState(State.DISCONNECTED);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_CONNECTING.equals(action)) {
                clearUI();
                updateConnectionState(State.CONNECTING);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                final String uuid = intent.getStringExtra(BluetoothLeService.EXTRA_UUID_CHAR);
                final byte[] dataArr = intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA_RAW);

                view.setGattUuid(uuid);
                view.setData(dataArr);
            }
        }
    };

    private void clearUI() {
        mExportString = null;
        view.clearUi();
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    @SuppressLint("MissingPermission") // We check before this is called
    private void displayGattServices(final List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        mExportString = mExporter.generateExportString(
                mDevice.getName(),
                mDevice.getAddress(),
                gattServices);

        final GattDataAdapterFactory.GattDataAdapter adapter = GattDataAdapterFactory.createAdapter(this, gattServices);
        view.setListAdapter(adapter);
        invalidateOptionsMenu();
    }

    @SuppressLint("MissingPermission") // We check before this is called
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gatt_services);

        final Intent intent = getIntent();
        mDevice = intent.getParcelableExtra(EXTRA_DEVICE);
        view = new View(this);

        // Sets up UI references.
        ((TextView) findViewById(R.id.device_address)).setText(mDevice.getAddress());
        view.setListClickListener(servicesListClickListner);

        getSupportActionBar().setTitle(mDevice.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mExporter = new Exporter(this);

        final Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);

        switch (mCurrentState) {

            case DISCONNECTED:
                menu.findItem(R.id.menu_connect).setVisible(true);
                menu.findItem(R.id.menu_disconnect).setVisible(false);
                menu.findItem(R.id.menu_refresh).setActionView(null);
                break;
            case CONNECTING:
                menu.findItem(R.id.menu_connect).setVisible(false);
                menu.findItem(R.id.menu_disconnect).setVisible(false);
                menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_progress_indeterminate);
                break;
            case CONNECTED:
                menu.findItem(R.id.menu_connect).setVisible(false);
                menu.findItem(R.id.menu_disconnect).setVisible(true);
                menu.findItem(R.id.menu_refresh).setActionView(null);
                break;
            default:
                throw new IllegalStateException("Don't know how to handle: " + mCurrentState);
        }

        if (mExportString == null) {
            menu.findItem(R.id.menu_share).setVisible(false);
        } else {
            menu.findItem(R.id.menu_share).setVisible(true);
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    @Override
    @SuppressLint("MissingPermission") // We check before this is called
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.menu_connect) {
            mBluetoothLeService.connect(mDevice.getAddress());
            return true;
        } else if (itemId == R.id.menu_disconnect) {
            mBluetoothLeService.disconnect();
            return true;
        } else if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.menu_share) {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            final String subject = getString(
                    R.string.exporter_email_device_services_subject,
                    mDevice.getName(),
                    mDevice.getAddress());

            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, mExportString);

            startActivity(Intent.createChooser(
                    intent,
                    getString(R.string.exporter_email_device_list_picker_text)));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentReceiverCompat.registerExportedReceiver(
                this,
                mGattUpdateReceiver,
                makeGattUpdateIntentFilter()
        );

        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDevice.getAddress());
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    private void updateConnectionState(final State state) {
        mCurrentState = state;
        runOnUiThread(() -> view.setConnectionState(state));
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTING);
        return intentFilter;
    }

    public static Intent createIntent(final Context context, final BluetoothLeDevice device) {
        final Intent intent = new Intent(context, DeviceControlActivity.class);
        intent.putExtra(DeviceControlActivity.EXTRA_DEVICE, device);
        return intent;
    }
}