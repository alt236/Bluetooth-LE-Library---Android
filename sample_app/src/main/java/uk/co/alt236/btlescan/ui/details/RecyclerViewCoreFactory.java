package uk.co.alt236.btlescan.ui.details;

import android.content.Context;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewBinderCore;
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.AdRecordBinder;
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.DeviceInfoBinder;
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.HeaderBinder;
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.IBeaconBinder;
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.RssiBinder;
import uk.co.alt236.btlescan.ui.details.recyclerview.binder.TextBinder;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.AdRecordHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.DeviceInfoHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.HeaderHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.IBeaconHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.RssiInfoHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.TextHolder;

/*protected*/ final class RecyclerViewCoreFactory {

    public static RecyclerViewBinderCore create(final Context context) {
        final RecyclerViewBinderCore core = new RecyclerViewBinderCore();

        core.add(new TextBinder(context), TextHolder.class, R.layout.list_item_view_textview);
        core.add(new HeaderBinder(context), HeaderHolder.class, R.layout.list_item_view_header);
        core.add(new AdRecordBinder(context), AdRecordHolder.class, R.layout.list_item_view_adrecord);
        core.add(new RssiBinder(context), RssiInfoHolder.class, R.layout.list_item_view_rssi_info);
        core.add(new DeviceInfoBinder(context), DeviceInfoHolder.class, R.layout.list_item_view_device_info);
        core.add(new IBeaconBinder(context), IBeaconHolder.class, R.layout.list_item_view_ibeacon_details);

        return core;
    }

}
