package uk.co.alt236.btlescan.activities;

import java.util.Collection;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.containers.AdRecord;
import uk.co.alt236.btlescan.containers.AdRecordUtils;
import uk.co.alt236.btlescan.containers.BluetoothLeDevice;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailsActivity extends Activity{
	public static final String EXTRA_DEVICE = "extra_device";

	@InjectView(R.id.tvDetails) TextView mTvDetails;

	private BluetoothLeDevice mDevice;

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
			append(sb, "Ad Records", null);
			append(sb, "-----------------", null);


			final Collection<AdRecord> adRecords = device.getAdRecordStore().getRecordsAsCollection();

			for(AdRecord record : adRecords){
				append(sb,
						"Record Id'" +record.getType()+ "' - " + record.getHumanReadableType(), AdRecordUtils.getRecordDataAsString(record));
			}
		}

		mTvDetails.setText(sb.toString());
	}


	private void append(StringBuilder sb, String label, boolean value){
		append(sb, label, String.valueOf(value));
	}

	private void append(StringBuilder sb, String label, long value){
		append(sb, label, String.valueOf(value));
	}

	private void append(StringBuilder sb, String label, String value){
		if(value != null){
			sb.append("#"  + label +":\t" + value + "\n");
		} else {
			sb.append(label + "\n");
		}
	}
}
