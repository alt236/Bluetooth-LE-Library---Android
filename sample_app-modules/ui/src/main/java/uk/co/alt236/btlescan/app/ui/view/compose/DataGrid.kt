package uk.co.alt236.btlescan.app.ui.view.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.alt236.btlescan.app.ui.R

@Composable
internal fun DataGrid(
    data: List<GridRow>,
    titleColSpan: Int = 2,
    dataColSpan: Int = 3,
) {
    val actualItems = remember(data) { data.flatMap { listOf(it.title, it.value) } }

    val firstGridItemSpan = GridItemSpan(titleColSpan)
    val secondGridItemSpan = GridItemSpan(dataColSpan)
    val totalSpan = titleColSpan + dataColSpan

    LazyVerticalGrid(
        columns = GridCells.Fixed(totalSpan),
        modifier =
            Modifier
                .heightIn(max = 1000.dp) // This is to allow the nesting of this inside a lazy column
                .padding(
                    start = dimensionResource(R.dimen.content_indent),
                    end = dimensionResource(R.dimen.content_indent),
                    bottom = dimensionResource(R.dimen.space_after_sections),
                ),
    ) {
        itemsIndexed(
            items = actualItems,
            span = { index, _ ->
                if (index.mod(2) == 0) {
                    firstGridItemSpan
                } else {
                    secondGridItemSpan
                }
            },
        ) { _, item ->
            Text(text = item, modifier = Modifier.fillMaxWidth())
        }
    }
}

internal data class GridRow(
    val title: String,
    val value: String,
) {
    @Deprecated("Stuff need to be formatted")
    constructor(title: String, value: Any) : this(title, value.toString())
}

@Preview
@Composable
fun preview() {
    DataGrid(
        data =
            listOf(
                GridRow("Title 1", "Data 1"),
                GridRow("Title 2", "Data 3"),
            ),
    )
}
