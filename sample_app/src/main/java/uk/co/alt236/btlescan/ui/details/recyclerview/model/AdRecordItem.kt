package uk.co.alt236.btlescan.ui.details.recyclerview.model

import dev.alt236.bluetoothlelib.device.adrecord.AdRecord
import dev.alt236.bluetoothlelib.util.AdRecordUtils
import dev.alt236.bluetoothlelib.util.ByteUtils
import uk.co.alt236.btlescan.app.ui.view.details.model.AdRecordItem
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ext.ByteArrayExt.toCharString

class AdRecordItem(
    override val title: String,
    record: AdRecord,
) : RecyclerViewItem,
    AdRecordItem {
    override val data: ByteArray = record.data ?: ByteArray(0)
    override val dataAsString: String = AdRecordUtils.getRecordDataAsString(record)
    override val dataAsChars: String = data.toCharString()
    override val dataAsHexArray: String = ByteUtils.byteArrayToHexString(record.data)
}
