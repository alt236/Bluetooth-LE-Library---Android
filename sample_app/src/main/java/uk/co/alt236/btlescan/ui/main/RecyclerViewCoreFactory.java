package uk.co.alt236.btlescan.ui.main;

import android.content.Context;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.Navigation;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewBinderCore;
import uk.co.alt236.btlescan.ui.main.recyclerview.binder.IBeaconBinder;
import uk.co.alt236.btlescan.ui.main.recyclerview.binder.LeDeviceBinder;
import uk.co.alt236.btlescan.ui.main.recyclerview.holder.IBeaconHolder;
import uk.co.alt236.btlescan.ui.main.recyclerview.holder.LeDeviceHolder;

/*protected*/ final class RecyclerViewCoreFactory {

    public static RecyclerViewBinderCore create(final Context context, final Navigation navigation) {
        final RecyclerViewBinderCore core = new RecyclerViewBinderCore();

        core.add(new IBeaconBinder(context, navigation), IBeaconHolder.class, R.layout.list_item_device_ibeacon);
        core.add(new LeDeviceBinder(context, navigation), LeDeviceHolder.class, R.layout.list_item_device_le);

        return core;
    }

}
