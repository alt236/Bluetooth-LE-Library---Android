package uk.co.alt236.btlescan.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.btlescan.ui.control.DeviceControlActivity;
import uk.co.alt236.btlescan.ui.details.DeviceDetailsActivity;

public class Navigation {

    private final Activity mActivity;

    public Navigation(final Activity activity) {
        mActivity = activity;
    }

    public void openDetailsActivity(final BluetoothLeDevice device) {
        final Intent intent = DeviceDetailsActivity.createIntent(mActivity, device);

        startActivity(intent);
    }

    public void startControlActivity(final BluetoothLeDevice device) {
        final Intent intent = DeviceControlActivity.createIntent(mActivity, device);

        startActivity(intent);
    }

    private void startActivity(final Intent intent) {
        ActivityCompat.startActivity(mActivity, intent, null);
    }
}
