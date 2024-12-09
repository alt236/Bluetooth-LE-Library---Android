package uk.co.alt236.btlescan.ui.details.recyclerview.binder

import android.content.Context
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.DeviceInfoHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.model.DeviceInfoItem

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

    private fun createSupportedDevicesString(item: DeviceInfoItem): String {
        val retVal: String
        retVal =
            if (item.bluetoothDeviceKnownSupportedServices.isEmpty()) {
                context.getString(R.string.no_known_services)
            } else {
                val sb = StringBuilder()
                for (service in item.bluetoothDeviceKnownSupportedServices) {
                    if (sb.isNotEmpty()) {
                        sb.append(", ")
                    }
                    sb.append(service)
                }
                sb.toString()
            }
        return retVal
    }

    override fun canBind(item: RecyclerViewItem): Boolean = item is DeviceInfoItem
}
