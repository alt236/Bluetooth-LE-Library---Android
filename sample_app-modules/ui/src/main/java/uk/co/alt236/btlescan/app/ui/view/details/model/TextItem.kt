package uk.co.alt236.btlescan.app.ui.view.details.model

import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem

interface TextItem :
    DetailsScreenItems,
    RecyclerViewItem {
    val text: CharSequence
}
