package uk.co.alt236.btlescan.util;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.util.Log;

public class BluetoothLeScanner {
	private static final long SCAN_PERIOD = 10000;
	
	private final Handler mHandler;
	private final BluetoothAdapter.LeScanCallback mLeScanCallback;
	private final BluetoothUtils mBluetoothUtils;
	private boolean mScanning;
	
	public BluetoothLeScanner(BluetoothAdapter.LeScanCallback leScanCallback, BluetoothUtils bluetoothUtils){
		mHandler = new Handler();
		mLeScanCallback = leScanCallback;
		mBluetoothUtils = bluetoothUtils;
	}
	
	
	public void scanLeDevice(final boolean enable) {
        if (enable) {
        	if(mScanning){return;}
        	Log.d("TAG", "~ Starting Scan");
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                	Log.d("TAG", "~ Stopping Scan (timeout)");
                    mScanning = false;
                    mBluetoothUtils.getBluetoothAdapter().stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothUtils.getBluetoothAdapter().startLeScan(mLeScanCallback);
        } else {
        	Log.d("TAG", "~ Stopping Scan");
            mScanning = false;
            mBluetoothUtils.getBluetoothAdapter().stopLeScan(mLeScanCallback);
        }
    }
}
