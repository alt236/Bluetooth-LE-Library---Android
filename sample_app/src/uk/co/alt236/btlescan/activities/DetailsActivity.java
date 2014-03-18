package uk.co.alt236.btlescan.activities;

import java.util.Collection;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecord;
import uk.co.alt236.bluetoothlelib.device.mfdata.IBeaconManufacturerData;
import uk.co.alt236.bluetoothlelib.util.AdRecordUtils;
import uk.co.alt236.bluetoothlelib.util.ByteUtils;
import uk.co.alt236.bluetoothlelib.util.IBeaconUtils;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.util.Constants;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailsActivity extends Activity{
	private static final String SECTION_LINE = "------------------------------";
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

	private String formatTime(long time){
		return android.text.format.DateFormat.format(
				Constants.TIME_FORMAT, new java.util.Date(time)).toString();
	}

	private String formatRssi(int rssi){
		return getString(R.string.formatter_db, String.valueOf(rssi));
	}

	private String formatRssi(double rssi){
		return getString(R.string.formatter_db, String.valueOf(rssi));
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
			append(sb, "RSSI Info", null);
			append(sb, SECTION_LINE, null);
			append(sb, "First Timestamp", formatTime(device.getFirstTimestamp()));
			append(sb, "First RSSI", formatRssi(device.getFirstRssi()));
			append(sb, "Current Timestamp", formatTime(device.getTimestamp()));
			append(sb, "Current RSSI", formatRssi(device.getRssi()));
			append(sb, "Running Average RSSI", formatRssi(device.getRunningAverageRssi()));

			append(sb, "", null);
			append(sb, "Scan Record", null);
			append(sb, SECTION_LINE, null);
			append(sb, device.getScanRecord());

			append(sb, "", null);
			append(sb, "Raw Ad Records As String", null);
			append(sb, SECTION_LINE, null);


			final Collection<AdRecord> adRecords = device.getAdRecordStore().getRecordsAsCollection();

			for(final AdRecord record : adRecords){
				append(sb, "#" +record.getType() + " " + record.getHumanReadableType(), null);
				append(sb, AdRecordUtils.getRecordDataAsString(record), null);
				append(sb, "", null);
			}

			append(sb, "Additional", null);
			append(sb, SECTION_LINE, null);
			 final boolean isIBeacon =  IBeaconUtils.isThisAnIBeacon(device);
			append(sb, "Is iBeacon", isIBeacon);

			if(isIBeacon){
				final IBeaconManufacturerData iBeaconData = new IBeaconManufacturerData(device);
				append(sb, "Company ID", iBeaconData.getCompanyIdentifier() + " (" + Integer.toHexString( iBeaconData.getCompanyIdentifier() ) + ")");
				append(sb, "Advertisment", iBeaconData.getIBeaconAdvertisement() + " (" + Integer.toHexString( iBeaconData.getIBeaconAdvertisement() ) + ")");
				append(sb, "UUID", iBeaconData.getUUID().toString());
				append(sb, "Major", iBeaconData.getMajor() + " (" + Integer.toHexString( iBeaconData.getMajor() ) + ")");
				append(sb, "Minor", iBeaconData.getMinor() + " (" + Integer.toHexString( iBeaconData.getMinor() ) + ")");
				append(sb, "TX Power", iBeaconData.getCalibratedTxPower() + " (" + Integer.toHexString( iBeaconData.getCalibratedTxPower() ) + ")");
			}
		}

		mTvDetails.setText(sb.toString());
	}

	private static void append(StringBuilder sb, byte[] value){
		append(sb, ByteUtils.byteArrayToHexString(value), null);
	}

	private static void append(StringBuilder sb, String label, boolean value) {
		append(sb, label, String.valueOf(value));
	}

//	private static void append(StringBuilder sb, String label, double value) {
//		append(sb, label, String.valueOf(value));
//	}
//
//	private static void append(StringBuilder sb, String label, int value) {
//		append(sb, label, String.valueOf(value));
//	}
//
//	private static void append(StringBuilder sb, String label, long value) {
//		append(sb, label, String.valueOf(value));
//	}

	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);
	}

	private static void append(StringBuilder sb, String label, String value){
		if(value != null){
			sb.append("\u2022"  + padRight(label, 10) +":\t" + value + "\n");
		} else {
			sb.append(label + "\n");
		}
	}
}
