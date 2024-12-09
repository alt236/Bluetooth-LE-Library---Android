package uk.co.alt236.btlescan.app.ui.view.details.model

import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem

interface AdRecordItem :
    DetailsScreenItems,
    RecyclerViewItem {
    val title: String
    val data: ByteArray
    val dataAsString: String
    val dataAsChars: String
    val dataAsHexArray: String
}
