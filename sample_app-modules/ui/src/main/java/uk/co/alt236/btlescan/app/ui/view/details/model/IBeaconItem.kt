package uk.co.alt236.btlescan.app.ui.view.details.model

import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem

interface IBeaconItem :
    DetailsScreenItems,
    RecyclerViewItem {
    val major: Int
    val minor: Int
    val uuid: String
    val companyIdentifier: Int
    val iBeaconAdvertisement: Int
    val calibratedTxPower: Int
}
