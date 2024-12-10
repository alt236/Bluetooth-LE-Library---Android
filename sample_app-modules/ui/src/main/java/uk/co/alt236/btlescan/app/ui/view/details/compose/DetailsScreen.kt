package uk.co.alt236.btlescan.app.ui.view.details.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import uk.co.alt236.btlescan.app.ui.R
import uk.co.alt236.btlescan.app.ui.content.DetailsUiExt.getPrettyServices
import uk.co.alt236.btlescan.app.ui.content.StringExt.singleQuote
import uk.co.alt236.btlescan.app.ui.view.compose.DataGrid
import uk.co.alt236.btlescan.app.ui.view.compose.GridRow
import uk.co.alt236.btlescan.app.ui.view.details.compose.preview.PreviewItemsFactory
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
    DeviceDetailsContent(PreviewItemsFactory.get())
}

@Composable
fun DeviceDetailsContent(rows: List<DetailsScreenItems>) {
    LazyColumn(
        Modifier.padding(dimensionResource(R.dimen.screen_margin))
    ) {
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
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = bottomPadding),
    )
}

@Composable
fun DetailsText(data: TextItem) {
    val bottomPadding = dimensionResource(R.dimen.space_after_sections)
    val horizontalPadding = dimensionResource(R.dimen.content_block_indent)
    Text(
        text = data.text.toString(),
        style = MaterialTheme.typography.bodyMedium,
        fontFamily = FontFamily.Monospace,
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
            GridRow(stringResource(R.string.label_device_name), data.name.singleQuote()),
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
    DataGrid(rows, 3, 7)
}

@Composable
fun DetailsRssiBlock(data: AdRecordItem) {
    val horizontalPadding = dimensionResource(R.dimen.content_block_indent)

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
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(horizontal = horizontalPadding),
    )
    DataGrid(rows, 2, 6)
}



