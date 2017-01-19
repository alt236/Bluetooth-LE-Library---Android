package uk.co.alt236.btlescan.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecord;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconType;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconUtils;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconManufacturerData;
import uk.co.alt236.bluetoothlelib.util.ByteUtils;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.Navigation;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewBinderCore;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.AdRecordItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.DeviceInfoItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.HeaderItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.IBeaconItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.RssiItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.TextItem;

public class DeviceDetailsActivity extends AppCompatActivity {
    private static final String EXTRA_DEVICE = DeviceDetailsActivity.class.getName() + ".EXTRA_DEVICE";
    private static final int LAYOUT_ID = R.layout.activity_details;

    @Bind(R.id.recycler)
    protected RecyclerView mRecycler;
    private BluetoothLeDevice mDevice;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_ID);
        ButterKnife.bind(this);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mDevice = getIntent().getParcelableExtra(EXTRA_DEVICE);

        getSupportActionBar().setTitle(mDevice.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pupulateDetails(mDevice);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                new Navigation(this).startControlActivity(mDevice);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void pupulateDetails(final BluetoothLeDevice device) {
        final DetailsRecyclerAdapter detailsRecyclerAdapter;

        final List<RecyclerViewItem> list = new ArrayList<>();

        if (device == null) {
            list.add(new HeaderItem(getString(R.string.header_device_info)));
            list.add(new TextItem(getString(R.string.invalid_device_data)));
        } else {
            list.add(new HeaderItem(getString(R.string.header_device_info)));
            list.add(new DeviceInfoItem(device));

            list.add(new HeaderItem(getString(R.string.header_rssi_info)));
            list.add(new RssiItem(device));

            list.add(new HeaderItem(getString(R.string.header_scan_record)));
            list.add(new TextItem(ByteUtils.byteArrayToHexString(device.getScanRecord())));

            final Collection<AdRecord> adRecords = device.getAdRecordStore().getRecordsAsCollection();
            if (adRecords.size() > 0) {
                list.add(new HeaderItem(getString(R.string.header_raw_ad_records)));

                for (final AdRecord record : adRecords) {

                    final String title = "#" + record.getType() + " " + record.getHumanReadableType();
                    list.add(new AdRecordItem(title, record));
                }
            }

            final boolean isIBeacon = BeaconUtils.getBeaconType(device) == BeaconType.IBEACON;
            if (isIBeacon) {
                final IBeaconManufacturerData iBeaconData = new IBeaconManufacturerData(device);
                list.add(new HeaderItem(getString(R.string.header_ibeacon_data)));
                list.add(new IBeaconItem(iBeaconData));
            }

        }

        final RecyclerViewBinderCore core = RecyclerViewCoreFactory.create(this);
        mRecycler.setAdapter(new DetailsRecyclerAdapter(core, list));
    }

    public static Intent createIntent(Context context, BluetoothLeDevice device) {
        final Intent intent = new Intent(context, DeviceDetailsActivity.class);
        intent.putExtra(DeviceDetailsActivity.EXTRA_DEVICE, device);

        return intent;
    }
}
