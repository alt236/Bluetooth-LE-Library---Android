package uk.co.alt236.btlescan.ui.details.recyclerview.model

import dev.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconManufacturerData
import uk.co.alt236.btlescan.app.ui.view.details.model.IBeaconItem
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem

class IBeaconItem(
    iBeaconData: IBeaconManufacturerData,
) : RecyclerViewItem,
    IBeaconItem {
    override val major: Int = iBeaconData.major
    override val minor: Int = iBeaconData.minor
    override val uuid: String = iBeaconData.uuid
    override val companyIdentifier: Int = iBeaconData.companyIdentifier
    override val iBeaconAdvertisement: Int = iBeaconData.iBeaconAdvertisement
    override val calibratedTxPower: Int = iBeaconData.calibratedTxPower
}
