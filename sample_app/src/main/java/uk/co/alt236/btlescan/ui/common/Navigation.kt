package uk.co.alt236.btlescan.ui.common

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.ui.control.DeviceControlActivity
import uk.co.alt236.btlescan.ui.details.DeviceDetailsActivity

class Navigation(private val activity: Activity) {

    fun openDetailsActivity(device: BluetoothLeDevice?) {
        val intent = DeviceDetailsActivity.createIntent(activity, device)
        startActivity(intent)
    }

    fun startControlActivity(device: BluetoothLeDevice?) {
        val intent = DeviceControlActivity.createIntent(activity, device)
        startActivity(intent)
    }

    fun shareFileViaEmail(fileUri: Uri?, recipient: Array<String?>?, subject: String?, message: String?) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_STREAM, fileUri)
        intent.putExtra(Intent.EXTRA_EMAIL, recipient)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)

        startActivity(Intent.createChooser(
                intent,
                activity.getString(R.string.exporter_email_device_list_picker_text)))
    }

    private fun startActivity(intent: Intent) {
        ActivityCompat.startActivity(activity, intent, null)
    }

}