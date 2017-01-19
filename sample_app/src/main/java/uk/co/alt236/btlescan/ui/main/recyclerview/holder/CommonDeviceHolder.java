package uk.co.alt236.btlescan.ui.main.recyclerview.holder;

import android.widget.TextView;

public interface CommonDeviceHolder {
    TextView getDeviceName();

    TextView getDeviceAddress();

    TextView getDeviceRssi();

    TextView getDeviceLastUpdated();
}
