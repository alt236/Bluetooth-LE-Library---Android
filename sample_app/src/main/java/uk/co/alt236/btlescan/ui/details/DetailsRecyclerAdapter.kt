package uk.co.alt236.btlescan.ui.details

import uk.co.alt236.btlescan.ui.common.recyclerview.BaseRecyclerViewAdapter
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewBinderCore
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem

internal class DetailsRecyclerAdapter(
    core: RecyclerViewBinderCore,
    items: List<RecyclerViewItem>,
) : BaseRecyclerViewAdapter(core, items)
