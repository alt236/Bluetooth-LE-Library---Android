package uk.co.alt236.btlescan.util

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanSettings
import android.os.Handler
import android.util.Log


@Suppress("MemberVisibilityCanBePrivate")
class BluetoothLeScanner(
    private val bluetoothAdapterWrapper: BluetoothAdapterWrapper,
    private val leScanCallback: ScanCallback
) {
    private val mHandler: Handler = Handler()

    var isScanning = false
        private set

    fun startScan() {
        scanLeDevice(duration = -1)
    }

    fun scanLeDevice(duration: Int) {
        bluetoothAdapterWrapper.bluetoothAdapter?.let { startScan(it, duration) }
    }

    fun stopScan(reason: String = "[not given]") {
        bluetoothAdapterWrapper.bluetoothAdapter?.let { stopScan(it, reason) }
    }

    @SuppressLint("MissingPermission") // We check before this is called
    private fun startScan(adapter: BluetoothAdapter, duration: Int) {
        if (isScanning) {
            return
        }

        // Stops scanning after a pre-defined scan period.
        if (duration > 0) {
            mHandler.postDelayed({
                stopScan("timeout")
            }, duration.toLong())
        }

        Log.d(TAG, "~ Starting Scan (duration: $duration)")
        isScanning = true
        val filters = ArrayList<ScanFilter>().apply {
            this.add(ScanFilter.Builder().build())
        }
        val settings = ScanSettings.Builder().setScanMode(
            ScanSettings.SCAN_MODE_LOW_LATENCY
        ).build()

        adapter.bluetoothLeScanner.startScan(filters, settings, leScanCallback)
    }

    @SuppressLint("MissingPermission") // We check before this is called
    private fun stopScan(adapter: BluetoothAdapter, reason: String) {
        Log.d(TAG, "~ Stopping Scan - reason: '$reason'")
        isScanning = false
        adapter.bluetoothLeScanner?.stopScan(leScanCallback)
    }

    private companion object {
        val TAG: String = BluetoothLeScanner::class.java.simpleName
    }

}