package uk.co.alt236.btlescan.util

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

class BluetoothAdapterWrapper(private val context: Context) {
    var bluetoothAdapter: BluetoothAdapter? = null

    init {
        val btManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?
        checkNotNull(btManager) { "No bluetooth manager adapter present!" }
        bluetoothAdapter = btManager.adapter
    }

    @SuppressLint("MissingPermission") // We check before this is called
    fun askUserToEnableBluetoothIfNeeded(activity: Activity) {
        if (isBluetoothLeSupported && !isBluetoothOn) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
    }

    val isBluetoothLeSupported: Boolean
        get() = context.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)

    val isBluetoothOn: Boolean
        get() = bluetoothAdapter?.isEnabled ?: false


    companion object {
        const val REQUEST_ENABLE_BT = 2001
    }
}