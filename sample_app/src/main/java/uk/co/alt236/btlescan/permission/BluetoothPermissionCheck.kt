package uk.co.alt236.btlescan.permission

import android.Manifest
import android.os.Build
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX
import uk.co.alt236.btlescan.R

class BluetoothPermissionCheck {
    fun checkBluetoothPermissions(
        activity: FragmentActivity,
        callback: PermissionCheckResultCallback,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkNeeded(activity, callback)
        } else {
            checkNotNeeded(callback)
        }
    }

    private fun checkNotNeeded(callback: PermissionCheckResultCallback) {
        callback.onSuccess()
    }

    private fun checkNeeded(
        activity: FragmentActivity,
        callback: PermissionCheckResultCallback,
    ) {
        val permissionRequest = getPermissionRequest()
        val appContext = activity.applicationContext

        PermissionX
            .init(activity)
            .permissions(permissionRequest.permissions)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    message = appContext.getString(permissionRequest.permissionRationaleResId),
                    positiveText = appContext.getString(android.R.string.ok),
                    negativeText = appContext.getString(android.R.string.cancel),
                )
            }.onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    appContext.getString(permissionRequest.permissionNeedToGoToSettings),
                    positiveText = appContext.getString(android.R.string.ok),
                    negativeText = appContext.getString(android.R.string.cancel),
                )
            }.request { allGranted, _, _ ->
                if (allGranted) {
                    callback.onSuccess()
                } else {
                    val notGrantedMessage = appContext.getString(permissionRequest.notGrantedResId)
                    callback.onFailure(notGrantedMessage)
                }
            }
    }

    private fun getPermissionRequest(): PermissionRequest {
        val permissions: List<String>
        val notGrantedResId: Int

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions =
                listOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                )
            notGrantedResId = R.string.permission_not_granted_bt_scan
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION)
            notGrantedResId = R.string.permission_not_granted_fine_location
        } else {
            permissions = listOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            notGrantedResId = R.string.permission_not_granted_coarse_location
        }

        return PermissionRequest(
            permissions = permissions,
            notGrantedResId = notGrantedResId,
            permissionRationaleResId = R.string.permission_rationale,
            permissionNeedToGoToSettings = R.string.permission_need_to_go_to_settings,
        )
    }

    private data class PermissionRequest(
        val permissions: List<String>,
        val notGrantedResId: Int,
        val permissionRationaleResId: Int,
        val permissionNeedToGoToSettings: Int,
    )

    interface PermissionCheckResultCallback {
        fun onSuccess()

        fun onFailure(message: CharSequence)
    }
}
