package uk.co.alt236.btlescan.ui.details.recyclerview.binder

import android.content.Context
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.app.ui.content.DetailsUiExt.getPrettyServices
import uk.co.alt236.btlescan.app.ui.view.details.model.DeviceInfoItem
import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseViewBinder
import uk.co.alt236.btlescan.app.ui.view.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.DeviceInfoHolder

class DeviceInfoBinder(
    context: Context,
) : BaseViewBinder<DeviceInfoItem>(context) {
    override fun bind(
        holder: BaseViewHolder<DeviceInfoItem>,
        item: DeviceInfoItem,
    ) {
        val actualHolder = holder as DeviceInfoHolder
        actualHolder.name.text = item.name
        actualHolder.address.text = item.address
        actualHolder.deviceClass.text = item.bluetoothDeviceClassName
        actualHolder.majorClass.text = item.bluetoothDeviceMajorClassName
        actualHolder.bondingState.text = item.bluetoothDeviceBondState
        actualHolder.services.text = createSupportedDevicesString(item)
    }

    private fun createSupportedDevicesString(item: DeviceInfoItem): String =
        item.getPrettyServices(context.getString(R.string.no_known_services))

    override fun canBind(item: RecyclerViewItem): Boolean = item is DeviceInfoItem
}
