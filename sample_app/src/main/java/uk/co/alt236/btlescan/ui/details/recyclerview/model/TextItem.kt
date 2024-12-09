package uk.co.alt236.btlescan.ui.details.recyclerview.model

import uk.co.alt236.btlescan.app.ui.view.details.model.TextItem
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem

class TextItem(
    override val text: CharSequence,
) : RecyclerViewItem,
    TextItem
