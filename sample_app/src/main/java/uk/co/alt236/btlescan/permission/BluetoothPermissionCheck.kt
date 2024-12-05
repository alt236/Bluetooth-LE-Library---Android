package uk.co.alt236.btlescan.permission

import android.Manifest
import android.app.Activity
import android.os.Build
import com.anthonycr.grant.PermissionsManager
import com.anthonycr.grant.PermissionsResultAction
import uk.co.alt236.btlescan.R

class BluetoothPermissionCheck {

    fun checkBluetoothPermissions(activity: Activity, callback: PermissionCheckResultCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkNeeded(activity, callback)
        } else {
            checkNotNeeded(callback)
        }
    }


    private fun checkNotNeeded(callback: PermissionCheckResultCallback) {
        callback.onSuccess()
    }

    private fun checkNeeded(activity: Activity, callback: PermissionCheckResultCallback) {
        val permissions: List<String>
        val message: Int

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions = listOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            message = R.string.permission_not_granted_bt_scan
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION)
            message = R.string.permission_not_granted_fine_location
        } else {
            permissions = listOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            message = R.string.permission_not_granted_coarse_location
        }

        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(
            activity,
            permissions.toTypedArray(),
            object : PermissionsResultAction() {
                override fun onGranted() {
                    callback.onSuccess()
                }

                override fun onDenied(permission: String) {
                    callback.onFailure(activity.getString(message))
                }
            })
    }

    interface PermissionCheckResultCallback {
        fun onSuccess()
        fun onFailure(message: CharSequence)
    }
}