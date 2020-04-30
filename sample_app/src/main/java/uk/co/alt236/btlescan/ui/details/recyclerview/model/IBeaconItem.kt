package uk.co.alt236.btlescan.ui.details.recyclerview.model

import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconManufacturerData
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem

class IBeaconItem(iBeaconData: IBeaconManufacturerData) : RecyclerViewItem {
    val major: Int = iBeaconData.major
    val minor: Int = iBeaconData.minor
    val uuid: String = iBeaconData.uuid
    val companyIdentifier: Int = iBeaconData.companyIdentifier
    val iBeaconAdvertisement: Int = iBeaconData.iBeaconAdvertisement
    val calibratedTxPower: Int = iBeaconData.calibratedTxPower
}