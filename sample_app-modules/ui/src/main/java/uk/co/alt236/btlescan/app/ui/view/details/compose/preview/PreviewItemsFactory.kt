package uk.co.alt236.btlescan.app.ui.view.details.compose.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import uk.co.alt236.btlescan.app.ui.R
import uk.co.alt236.btlescan.app.ui.content.StringExt.singleQuote
import uk.co.alt236.btlescan.app.ui.view.details.model.AdRecordItem
import uk.co.alt236.btlescan.app.ui.view.details.model.DetailsScreenItems
import uk.co.alt236.btlescan.app.ui.view.details.model.DeviceInfoItem
import uk.co.alt236.btlescan.app.ui.view.details.model.HeaderItem
import uk.co.alt236.btlescan.app.ui.view.details.model.RssiItem
import uk.co.alt236.btlescan.app.ui.view.details.model.TextItem

internal object PreviewItemsFactory {

    @Composable
    fun get(): List<DetailsScreenItems> {
        val rawScanRecordArray =
            LoremIpsum(10)
                .values
                .joinToString()
                .toByteArray()
                .contentToString()

        return listOf(
            TestHeaderItem(stringResource(R.string.header_device_info)),
            TestDeviceInfoItem(
                bluetoothDeviceKnownSupportedServices = setOf("FOO", "BAR", "BAZ"),
                bluetoothDeviceBondState = "some state",
                bluetoothDeviceMajorClassName = "Something something",
                bluetoothDeviceClassName = "yes, it's a device",
                address = "00:00:00:00:00:00",
                name = "Test device!",
            ),
            TestHeaderItem(stringResource(R.string.header_rssi_info)),
            TestRssiItem(
                rssi = -10,
                runningAverageRssi = -50.0,
                firstTimestamp = System.currentTimeMillis(),
                firstRssi = -100,
                timestamp = System.currentTimeMillis(),
            ),
            TestHeaderItem(stringResource(R.string.header_scan_record)),
            TestTextItem(rawScanRecordArray),
            TestHeaderItem(stringResource(R.string.header_raw_ad_records)),
            TestAdRecord.create(0),
            TestAdRecord.create(1),
            TestAdRecord.create(2),
        )
    }

    private data class TestHeaderItem(
        override val text: CharSequence,
    ) : HeaderItem

    private data class TestTextItem(
        override val text: CharSequence,
    ) : TextItem

    private data class TestRssiItem(
        override val rssi: Int,
        override val runningAverageRssi: Double,
        override val firstRssi: Int,
        override val firstTimestamp: Long,
        override val timestamp: Long,
    ) : RssiItem

    private data class TestDeviceInfoItem(
        override val bluetoothDeviceKnownSupportedServices: Set<String>,
        override val bluetoothDeviceBondState: String,
        override val bluetoothDeviceMajorClassName: String,
        override val bluetoothDeviceClassName: String,
        override val address: String,
        override val name: String,
    ) : DeviceInfoItem

    private data class TestAdRecord(
        override val title: String,
        override val data: ByteArray,
        override val dataAsString: String,
        override val dataAsChars: String,
        override val dataAsHexArray: String,
    ) : AdRecordItem {
        companion object {
            fun create(index: Int): AdRecordItem {
                val input = LoremIpsum(3).values.joinToString(" ")
                val byteArray = input.toByteArray(Charsets.UTF_8)
                return TestAdRecord(
                    title = "Entry # ${index + 1}",
                    data = byteArray,
                    dataAsString = input.singleQuote(),
                    dataAsChars = input.singleQuote(),
                    dataAsHexArray = byteArray.contentToString(),
                )
            }
        }
    }
}