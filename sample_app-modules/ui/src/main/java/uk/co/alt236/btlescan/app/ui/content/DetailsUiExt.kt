package uk.co.alt236.btlescan.app.ui.content

import uk.co.alt236.btlescan.app.ui.view.details.model.DeviceInfoItem

object DetailsUiExt {
    fun DeviceInfoItem.getPrettyServices(fallback: String): String =
        if (bluetoothDeviceKnownSupportedServices.isEmpty()) {
            fallback
        } else {
            bluetoothDeviceKnownSupportedServices.joinToString(", ")
        }
}
