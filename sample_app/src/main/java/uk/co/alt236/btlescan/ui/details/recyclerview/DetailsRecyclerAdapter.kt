package uk.co.alt236.btlescan.ui.details.recyclerview

import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseRecyclerViewAdapter
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewBinderCore
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem

internal class DetailsRecyclerAdapter(
    core: RecyclerViewBinderCore,
    items: List<RecyclerViewItem>,
) : BaseRecyclerViewAdapter(core, items)
