package uk.co.alt236.btlescan.ui.details.recyclerview.binder

import android.content.Context
import dev.alt236.bluetoothlelib.resolvers.CompanyIdentifierResolver
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.IBeaconHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.model.IBeaconItem
import uk.co.alt236.btlescan.util.TimeFormatter
import java.util.Locale

class IBeaconBinder(
    context: Context,
) : BaseViewBinder<IBeaconItem>(context) {
    override fun bind(
        holder: BaseViewHolder<IBeaconItem>,
        item: IBeaconItem,
    ) {
        val actualHolder = holder as IBeaconHolder
        val companyName =
            CompanyIdentifierResolver.getCompanyName(
                item.companyIdentifier,
                context.getString(R.string.unknown),
            )
        actualHolder.companyId.text = getWithHexEncode(companyName, item.companyIdentifier)
        actualHolder.advert.text = getWithHexEncode(item.iBeaconAdvertisement)
        actualHolder.uuid.text = item.uuid
        actualHolder.major.text = getWithHexEncode(item.major)
        actualHolder.minor.text = getWithHexEncode(item.minor)
        actualHolder.txPower.text = getWithHexEncode(item.calibratedTxPower)
    }

    override fun canBind(item: RecyclerViewItem): Boolean = item is IBeaconItem

    companion object {
        private const val STRING_FORMAT = "%s (%s)"

        private fun formatTime(time: Long): String = TimeFormatter.getIsoDateTime(time)

        private fun getWithHexEncode(
            first: String,
            value: Int,
        ): String = createLine(first, hexEncode(value))

        private fun getWithHexEncode(value: Int): String = createLine(value.toString(), hexEncode(value))

        private fun createLine(
            first: String,
            second: String,
        ): String = String.format(Locale.US, STRING_FORMAT, first, second)

        private fun hexEncode(integer: Int): String = "0x" + Integer.toHexString(integer).toUpperCase(Locale.US)
    }
}
