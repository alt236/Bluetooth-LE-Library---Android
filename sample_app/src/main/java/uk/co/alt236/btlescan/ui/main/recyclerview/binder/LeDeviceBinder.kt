package uk.co.alt236.btlescan.ui.main.recyclerview.binder

import android.content.Context
import uk.co.alt236.btlescan.ui.common.Navigation
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ui.main.recyclerview.holder.LeDeviceHolder
import uk.co.alt236.btlescan.ui.main.recyclerview.model.LeDeviceItem

class LeDeviceBinder(
    context: Context,
    private val navigation: Navigation,
) : BaseViewBinder<LeDeviceItem>(context) {
    override fun bind(
        holder: BaseViewHolder<LeDeviceItem>,
        item: LeDeviceItem,
    ) {
        val actualHolder = holder as LeDeviceHolder
        val device = item.device

        CommonBinding.bind(context, actualHolder, device)
        actualHolder.view.setOnClickListener { navigation.openDetailsActivity(device) }
    }

    override fun canBind(item: RecyclerViewItem): Boolean = item is LeDeviceItem
}
