package uk.co.alt236.btlescan.app.ui.view.details.compose

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.TextUnit
import uk.co.alt236.btlescan.app.ui.R
import uk.co.alt236.btlescan.app.ui.content.DetailsUiExt.getPrettyServices
import uk.co.alt236.btlescan.app.ui.content.StringExt.singleQuote
import uk.co.alt236.btlescan.app.ui.view.compose.DataGrid
import uk.co.alt236.btlescan.app.ui.view.compose.GridRow
import uk.co.alt236.btlescan.app.ui.view.details.model.AdRecordItem
import uk.co.alt236.btlescan.app.ui.view.details.model.DetailsScreenItems
import uk.co.alt236.btlescan.app.ui.view.details.model.DeviceInfoItem
import uk.co.alt236.btlescan.app.ui.view.details.model.HeaderItem
import uk.co.alt236.btlescan.app.ui.view.details.model.IBeaconItem
import uk.co.alt236.btlescan.app.ui.view.details.model.RssiItem
import uk.co.alt236.btlescan.app.ui.view.details.model.TextItem

@Preview
@Composable
fun preview() {
    val rawScanRecordArray =
        LoremIpsum(10)
            .values
            .joinToString()
            .toByteArray()
            .contentToString()

    val data =
        listOf(
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

    DeviceDetails(data)
}

@Composable
fun DeviceDetails(rows: List<DetailsScreenItems>) {
    LazyColumn {
        items(rows) { row ->
            when (row) {
                is AdRecordItem -> DetailsRssiBlock(row)
                is DeviceInfoItem -> DetailsDeviceInfoBlock(row)
                is HeaderItem -> DetailsHeader(row)
                is IBeaconItem -> TODO()
                is RssiItem -> DetailsRssiBlock(row)
                is TextItem -> DetailsText(row)
            }
        }
    }
}

@Composable
fun DetailsHeader(data: HeaderItem) {
    val color = colorResource(R.color.colorAccent)
    val bottomPadding = dimensionResource(R.dimen.space_after_header)
    Text(
        text = data.text.toString().uppercase(),
        color = color,
        fontWeight = FontWeight.Bold,
        fontSize = dimensionTextUnit(R.dimen.value_text_size),
        modifier = Modifier.padding(bottom = bottomPadding),
    )
}

@Composable
fun DetailsText(data: TextItem) {
    val bottomPadding = dimensionResource(R.dimen.space_after_sections)
    val horizontalPadding = dimensionResource(R.dimen.content_indent)
    Text(
        text = data.text.toString(),
        fontSize = dimensionTextUnit(R.dimen.value_text_size),
        modifier =
            Modifier.padding(
                start = horizontalPadding,
                end = horizontalPadding,
                bottom = bottomPadding,
            ),
    )
}

@Composable
fun DetailsRssiBlock(data: RssiItem) {
    val rows =
        listOf(
            GridRow(stringResource(R.string.label_first_timestamp), data.firstTimestamp),
            GridRow(stringResource(R.string.label_first_rssi), data.firstRssi),
            GridRow(stringResource(R.string.label_last_timestamp), data.timestamp),
            GridRow(stringResource(R.string.label_rssi), data.rssi),
            GridRow(stringResource(R.string.label_running_average_rssi), data.runningAverageRssi),
        )
    DataGrid(rows)
}

@Composable
fun DetailsDeviceInfoBlock(data: DeviceInfoItem) {
    val rows =
        listOf(
            GridRow(stringResource(R.string.label_device_name), data.name),
            GridRow(stringResource(R.string.label_device_address), data.address),
            GridRow(stringResource(R.string.label_device_class), data.bluetoothDeviceClassName),
            GridRow(
                stringResource(R.string.label_device_major_class),
                data.bluetoothDeviceMajorClassName,
            ),
            GridRow(
                stringResource(R.string.label_device_services),
                data.getPrettyServices(stringResource(R.string.no_known_services)),
            ),
            GridRow(stringResource(R.string.label_bonding_state), data.bluetoothDeviceBondState),
        )
    DataGrid(rows)
}

@Composable
fun DetailsRssiBlock(data: AdRecordItem) {
    val horizontalPadding = dimensionResource(R.dimen.content_indent)

    val rows =
        listOf(
            GridRow(stringResource(R.string.label_length), data.data.size.toString()),
            GridRow(stringResource(R.string.label_as_utf8), data.dataAsString),
            GridRow(stringResource(R.string.label_as_characters), data.dataAsChars),
            GridRow(stringResource(R.string.label_as_array), data.dataAsHexArray),
        )

    Text(
        text = data.title,
        fontWeight = FontWeight.Bold,
        fontSize = dimensionTextUnit(R.dimen.value_text_size),
        modifier = Modifier.padding(horizontal = horizontalPadding),
    )
    DataGrid(rows, 2, 6)
}

@Composable
internal fun dimensionTextUnit(
    @DimenRes resId: Int,
): TextUnit =
    with(LocalDensity.current) {
        dimensionResource(resId).toSp()
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
