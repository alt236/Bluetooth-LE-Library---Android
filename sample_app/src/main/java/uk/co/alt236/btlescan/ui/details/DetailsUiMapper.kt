package uk.co.alt236.btlescan.ui.details

import android.content.Context
import dev.alt236.bluetoothlelib.device.BluetoothLeDevice
import dev.alt236.bluetoothlelib.device.beacon.BeaconType
import dev.alt236.bluetoothlelib.device.beacon.BeaconUtils
import dev.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconManufacturerData
import dev.alt236.bluetoothlelib.util.ByteUtils
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.app.ui.view.details.model.DetailsScreenItems
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ui.details.recyclerview.model.AdRecordItem
import uk.co.alt236.btlescan.ui.details.recyclerview.model.DeviceInfoItem
import uk.co.alt236.btlescan.ui.details.recyclerview.model.HeaderItem
import uk.co.alt236.btlescan.ui.details.recyclerview.model.IBeaconItem
import uk.co.alt236.btlescan.ui.details.recyclerview.model.RssiItem
import uk.co.alt236.btlescan.ui.details.recyclerview.model.TextItem

internal class DetailsUiMapper(private val context: Context) {

    fun map(device: BluetoothLeDevice?): List<DetailsScreenItems> {
        val retVal = ArrayList<DetailsScreenItems>()

        if (device == null) {
            retVal.add(HeaderItem(context.getString(R.string.header_device_info)))
            retVal.add(TextItem(context.getString(R.string.invalid_device_data)))
        } else {
            retVal.add(HeaderItem(context.getString(R.string.header_device_info)))
            retVal.add(DeviceInfoItem(device))

            retVal.add(HeaderItem(context.getString(R.string.header_rssi_info)))
            retVal.add(RssiItem(device))

            retVal.add(HeaderItem(context.getString(R.string.header_scan_record)))
            retVal.add(TextItem(ByteUtils.byteArrayToHexString(device.scanRecord)))

            val adRecords = device.adRecordStore.recordsAsCollection
            if (adRecords.isNotEmpty()) {
                retVal.add(HeaderItem(context.getString(R.string.header_raw_ad_records)))

                for (record in adRecords) {
                    val title = "#" + record.type + " " + record.humanReadableType
                    retVal.add(AdRecordItem(title, record))
                }
            }

            val isIBeacon = BeaconUtils.getBeaconType(device) == BeaconType.IBEACON
            if (isIBeacon) {
                val iBeaconData = IBeaconManufacturerData(device)
                retVal.add(HeaderItem(context.getString(R.string.header_ibeacon_data)))
                retVal.add(IBeaconItem(iBeaconData))
            }
        }

        return retVal
    }

}