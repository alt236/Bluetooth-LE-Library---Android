package uk.co.alt236.btlescan.ui.common

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.app.ShareCompat
import dev.alt236.bluetoothlelib.device.BluetoothLeDevice
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

    fun shareFileViaEmail(uri: Uri, recipient: Array<String>?, subject: String?, message: String?) {
        val intent = ShareCompat.IntentBuilder.from(activity)
                .setChooserTitle(R.string.exporter_email_device_list_picker_text)
                .setStream(uri)
                .setEmailTo(recipient ?: emptyArray())
                .setSubject(subject ?: "")
                .setText(message ?: "")
                .setType("text/text")
                .intent
                .setAction(Intent.ACTION_SEND)
                .setDataAndType(uri, "plain/text")
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(intent)
    }

    private fun startActivity(intent: Intent) {
        ActivityCompat.startActivity(activity, intent, null)
    }

}