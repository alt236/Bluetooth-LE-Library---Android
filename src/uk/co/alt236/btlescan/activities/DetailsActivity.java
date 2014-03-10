package uk.co.alt236.btlescan.activities;

import java.util.Collection;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.containers.AdRecord;
import uk.co.alt236.btlescan.containers.BluetoothLeDevice;
import uk.co.alt236.btlescan.util.AdRecordUtils;
import uk.co.alt236.btlescan.util.ByteUtils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailsActivity extends Activity{
	public static final String EXTRA_DEVICE = "extra_device";

	@InjectView(R.id.tvDetails) TextView mTvDetails;

	private BluetoothLeDevice mDevice;

	private void append(StringBuilder sb, byte[] value){
		append(sb, ByteUtils.byteArrayToHexString(value), null);
	}

	private void append(StringBuilder sb, String label, String value){
		if(value != null){
			sb.append("\u2022"  + label +":\t" + value + "\n");
		} else {
			sb.append(label + "\n");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		ButterKnife.inject(this);

		mDevice = getIntent().getParcelableExtra(EXTRA_DEVICE);

		pupulateDetails(mDevice);
	}

	private void pupulateDetails(BluetoothLeDevice device) {
		final StringBuilder sb = new StringBuilder();

		if(device == null){
			append(sb, "Invalid Device Data!", null);
		} else {
			append(sb, "Device Name", device.getName());
			append(sb, "Device Address", device.getAddress());

			append(sb, "", null);
			append(sb, "Device Class", device.getBluetoothDeviceClassName());
			append(sb, "Bonding State", device.getBluetoothDeviceBondState());

			append(sb, "", null);
			append(sb, "Scan Record", null);
			append(sb, "-----------------", null);
			append(sb, device.getScanRecord());

			append(sb, "", null);
			append(sb, "Ad Records", null);
			append(sb, "-----------------", null);


			final Collection<AdRecord> adRecords = device.getAdRecordStore().getRecordsAsCollection();

			for(final AdRecord record : adRecords){
				append(sb, "#" +record.getType() + " " + record.getHumanReadableType(), null);
				append(sb, AdRecordUtils.getRecordDataAsString(record), null);
				append(sb, "", null);
			}
		}

		mTvDetails.setText(sb.toString());
	}
}
