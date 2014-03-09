package uk.co.alt236.btlescan;

import java.util.Collection;

import uk.co.alt236.btlescan.containers.AdRecord;
import uk.co.alt236.btlescan.containers.AdRecordUtils;
import uk.co.alt236.btlescan.containers.BluetoothLeDevice;
import uk.co.alt236.btlescan.util.BluetoothLeScanner;
import uk.co.alt236.btlescan.util.BluetoothUtils;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ListActivity {
	@InjectView(R.id.tvBluetoothLe) TextView mTvBluetoothLeStatus;
	@InjectView(R.id.tvBluetoothStatus) TextView mTvBluetoothStatus;
	
	private static final long SCAN_PERIOD = 10000;
	private BluetoothUtils mBluetoothUtils;
	private BluetoothLeScanner mScanner;
    //private LeDeviceListAdapter mLeDeviceListAdapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		mBluetoothUtils = new BluetoothUtils(this);
		mScanner = new BluetoothLeScanner(mLeScanCallback, mBluetoothUtils);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	

	
	
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
	    @Override
	    public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
	    	
	    	final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord);
	        Log.d("TAG", "~ New BT Device: " + deviceLe);
	        
	        final Collection<AdRecord> adRecords = deviceLe.getAdRecordStore().getRecordsAsCollection();
	        
	        for(final AdRecord record : adRecords){
	        	Log.d("TAG", "~ Has Record: " + record.getType() + ": '" + record.getHumanReadableType() +"', data: '"+ AdRecordUtils.getRecordDataAsString(record) + "'");
	        }
	        
//	    	runOnUiThread(new Runnable() {
//	           @Override
//	           public void run() {
//	               mLeDeviceListAdapter.addDevice(device);
//	               mLeDeviceListAdapter.notifyDataSetChanged();
//	           }
//	       });
	        
	   }
	};
	
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
		
		mBluetoothUtils.askUserToEnableBluetoothIfNeeded();
		if(mIsBluetoothOn && mIsBluetoothLePresent){
			mScanner.scanLeDevice(true);
		}
	}

}
