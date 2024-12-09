package uk.co.alt236.btlescan.ui.details.recyclerview.model

import dev.alt236.bluetoothlelib.device.adrecord.AdRecord
import dev.alt236.bluetoothlelib.util.AdRecordUtils
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.kt.ByteArrayExt.toCharString

class AdRecordItem(
    val title: String,
    record: AdRecord,
) : RecyclerViewItem {
    val data: ByteArray = record.data ?: ByteArray(0)
    val dataAsString: String = AdRecordUtils.getRecordDataAsString(record)
    val dataAsChars: String = data.toCharString()
}
