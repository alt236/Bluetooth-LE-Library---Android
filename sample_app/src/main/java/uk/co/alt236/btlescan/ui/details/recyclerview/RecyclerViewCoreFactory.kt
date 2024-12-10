package uk.co.alt236.btlescan.ui.details.recyclerview

import android.content.Context
import uk.co.alt236.btlescan.R
import uk.co.alt236.btlescan.app.ui.view.recyclerview.RecyclerViewBinderCore
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.AdRecordBinder
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.DeviceInfoBinder
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.HeaderBinder
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.IBeaconBinder
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.RssiBinder
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.TextBinder
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.AdRecordHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.DeviceInfoHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.HeaderHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.IBeaconHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.RssiInfoHolder
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.TextHolder

internal object RecyclerViewCoreFactory {
    fun create(context: Context): RecyclerViewBinderCore {
        val core = RecyclerViewBinderCore()

        core.add(
            TextBinder(context),
            TextHolder::class.java,
            R.layout.list_item_view_textview,
        )
        core.add(
            HeaderBinder(context),
            HeaderHolder::class.java,
            R.layout.list_item_view_header,
        )
        core.add(
            AdRecordBinder(context),
            AdRecordHolder::class.java,
            R.layout.list_item_view_adrecord,
        )
        core.add(
            RssiBinder(context),
            RssiInfoHolder::class.java,
            R.layout.list_item_view_rssi_info,
        )
        core.add(
            DeviceInfoBinder(context),
            DeviceInfoHolder::class.java,
            R.layout.list_item_view_device_info,
        )
        core.add(
            IBeaconBinder(context),
            IBeaconHolder::class.java,
            R.layout.list_item_view_ibeacon_details,
        )

        return core
    }
}
