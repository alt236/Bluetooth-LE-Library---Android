package uk.co.alt236.btlescan.ui.details.recyclerview.binder;

import android.content.Context;

import uk.co.alt236.bluetoothlelib.device.BluetoothService;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewBinder;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.common.recyclerview.RecyclerViewItem;
import uk.co.alt236.btlescan.ui.details.recyclerview.holder.DeviceInfoHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.DeviceInfoItem;

public class DeviceInfoBinder extends BaseViewBinder<DeviceInfoItem> {

    public DeviceInfoBinder(Context context) {
        super(context);
    }

    @Override
    public void bind(BaseViewHolder<DeviceInfoItem> holder, DeviceInfoItem item) {
        final DeviceInfoHolder actualHolder = (DeviceInfoHolder) holder;

        actualHolder.getName().setText(item.getName());
        actualHolder.getAddress().setText(item.getAddress());
        actualHolder.getDeviceClass().setText(item.getBluetoothDeviceClassName());
        actualHolder.getMajorClass().setText(item.getBluetoothDeviceMajorClassName());
        actualHolder.getBondingState().setText(item.getBluetoothDeviceBondState());
        actualHolder.getServices().setText(createSupportedDevicesString(item));
    }


    private String createSupportedDevicesString(DeviceInfoItem item) {
        final String retVal;

        if (item.getBluetoothDeviceKnownSupportedServices().isEmpty()) {
            retVal = getContext().getString(R.string.no_known_services);
        } else {
            final StringBuilder sb = new StringBuilder();

            for (final BluetoothService service : item.getBluetoothDeviceKnownSupportedServices()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }

                sb.append(service);
            }
            retVal = sb.toString();
        }

        return retVal;
    }

    @Override
    public boolean canBind(RecyclerViewItem item) {
        return item instanceof DeviceInfoItem;
    }
}
