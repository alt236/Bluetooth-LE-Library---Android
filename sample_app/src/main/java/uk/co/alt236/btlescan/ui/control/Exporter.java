package uk.co.alt236.btlescan.ui.control;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import java.util.List;

import uk.co.alt236.bluetoothlelib.resolvers.GattAttributeResolver;
import uk.co.alt236.btlescan.R;

/*package*/ class Exporter {
    private final Context mContext;

    public Exporter(final Context context) {
        mContext = context.getApplicationContext();
    }

    public String generateExportString(final String deviceName,
                                       final String deviceAddress,
                                       final List<BluetoothGattService> gattServices) {

        final String unknownServiceString = mContext.getString(R.string.unknown_service);
        final String unknownCharaString = mContext.getString(R.string.unknown_characteristic);
        final StringBuilder exportBuilder = new StringBuilder();

        exportBuilder.append("Device Name: ");
        exportBuilder.append(deviceName);
        exportBuilder.append('\n');
        exportBuilder.append("Device Address: ");
        exportBuilder.append(deviceAddress);
        exportBuilder.append('\n');
        exportBuilder.append('\n');

        exportBuilder.append("Services:");
        exportBuilder.append("--------------------------");
        exportBuilder.append('\n');

        String uuid = null;
        for (final BluetoothGattService gattService : gattServices) {
            uuid = gattService.getUuid().toString();

            exportBuilder.append(GattAttributeResolver.getAttributeName(uuid, unknownServiceString));
            exportBuilder.append(" (");
            exportBuilder.append(uuid);
            exportBuilder.append(')');
            exportBuilder.append('\n');

            final List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                uuid = gattCharacteristic.getUuid().toString();

                exportBuilder.append('\t');
                exportBuilder.append(GattAttributeResolver.getAttributeName(uuid, unknownCharaString));
                exportBuilder.append(" (");
                exportBuilder.append(uuid);
                exportBuilder.append(')');
                exportBuilder.append('\n');
            }

            exportBuilder.append('\n');
            exportBuilder.append('\n');
        }

        exportBuilder.append("--------------------------");
        exportBuilder.append('\n');

        return exportBuilder.toString();
    }
}
