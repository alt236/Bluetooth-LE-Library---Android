package uk.co.alt236.btlescan.util

import android.bluetooth.BluetoothAdapter.LeScanCallback
import android.os.Handler
import android.util.Log

class BluetoothLeScanner(private val bluetoothAdapterWrapper: BluetoothAdapterWrapper,
                         private val leScanCallback: LeScanCallback
) {
    private val mHandler: Handler = Handler()

    var isScanning = false
        private set

    fun scanLeDevice(duration: Int) {
        bluetoothAdapterWrapper.bluetoothAdapter?.let { btAdapter ->
            if (isScanning) {
                return
            }

            Log.d("TAG", "~ Starting Scan")
            // Stops scanning after a pre-defined scan period.
            if (duration > 0) {
                mHandler.postDelayed({
                    Log.d("TAG", "~ Stopping Scan (due to timeout)")
                    stopScan()
                }, duration.toLong())
            }
            isScanning = true
            btAdapter.startLeScan(leScanCallback)
        }
    }

    fun stopScan() {
        bluetoothAdapterWrapper.bluetoothAdapter?.let { btAdapter ->
            Log.d("TAG", "~ Stopping Scan (actually)")
            isScanning = false
            btAdapter.stopLeScan(leScanCallback)
        }
    }

}