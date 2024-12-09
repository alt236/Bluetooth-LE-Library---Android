package uk.co.alt236.btlescan.ui.main.recyclerview.model

import dev.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem

data class IBeaconItem(
    val device: IBeaconDevice,
) : RecyclerViewItem
