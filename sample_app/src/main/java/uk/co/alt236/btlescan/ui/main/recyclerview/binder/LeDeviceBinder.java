package uk.co.alt236.btlescan.ui.main.recyclerview.binder;

import android.content.Context;
import android.view.View;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.btlescan.ui.common.Navigation;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.main.recyclerview.holder.LeDeviceHolder;
import uk.co.alt236.btlescan.ui.main.recyclerview.model.LeDeviceItem;

public class LeDeviceBinder extends BaseViewBinder<LeDeviceItem> {

    private final Navigation navigation;

    public LeDeviceBinder(Context context, Navigation navigation) {
        super(context);
        this.navigation = navigation;
    }

    @Override
    public void bind(BaseViewHolder<LeDeviceItem> holder, LeDeviceItem item) {

        final LeDeviceHolder actualHolder = (LeDeviceHolder) holder;
        final BluetoothLeDevice device = item.getDevice();

        CommonBinding.bind(getContext(), actualHolder, device);
        actualHolder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigation.openDetailsActivity(device);
            }
        });
    }

    @Override
    public boolean canBind(RecyclerViewItem item) {
        return item instanceof LeDeviceItem;
    }
}
