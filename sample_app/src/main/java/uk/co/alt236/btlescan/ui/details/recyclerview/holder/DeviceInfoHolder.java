package uk.co.alt236.btlescan.ui.details.recyclerview.holder;

import android.view.View;
import android.widget.TextView;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.ui.common.recyclerview.BaseViewHolder;
import uk.co.alt236.btlescan.ui.details.recyclerview.model.DeviceInfoItem;

public class DeviceInfoHolder extends BaseViewHolder<DeviceInfoItem> {

    private final TextView mName;
    private final TextView mAddress;
    private final TextView mClass;
    private final TextView mMajorClass;
    private final TextView mServices;
    private final TextView mBondingState;

    public DeviceInfoHolder(View itemView) {
        super(itemView);

        mName = (TextView) itemView.findViewById(R.id.deviceName);
        mAddress = (TextView) itemView.findViewById(R.id.deviceAddress);
        mClass = (TextView) itemView.findViewById(R.id.deviceClass);
        mMajorClass = (TextView) itemView.findViewById(R.id.deviceMajorClass);
        mServices = (TextView) itemView.findViewById(R.id.deviceServiceList);
        mBondingState = (TextView) itemView.findViewById(R.id.deviceBondingState);
    }

    public TextView getName() {
        return mName;
    }

    public TextView getAddress() {
        return mAddress;
    }

    public TextView getDeviceClass() {
        return mClass;
    }

    public TextView getMajorClass() {
        return mMajorClass;
    }

    public TextView getServices() {
        return mServices;
    }

    public TextView getBondingState() {
        return mBondingState;
    }
}
