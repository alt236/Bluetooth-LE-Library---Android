package uk.co.alt236.btlescan.app.ui.view.details.model

import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem

interface RssiItem :
    DetailsScreenItems,
    RecyclerViewItem {
    val rssi: Int
    val runningAverageRssi: Double
    val firstRssi: Int
    val firstTimestamp: Long
    val timestamp: Long
}
