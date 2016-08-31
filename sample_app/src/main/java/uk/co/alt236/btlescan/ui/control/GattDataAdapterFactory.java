package uk.co.alt236.btlescan.ui.control;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.alt236.bluetoothlelib.resolvers.GattAttributeResolver;
import uk.co.alt236.btlescan.R;

/*package*/ class GattDataAdapterFactory {
    private static final String LIST_NAME = "NAME";
    private static final String LIST_UUID = "UUID";

    public static GattDataAdapter createAdapter(final Context context,
                                                final List<BluetoothGattService> gattServices) {


        final String unknownServiceString = context.getString(R.string.unknown_service);
        final String unknownCharaString = context.getString(R.string.unknown_characteristic);
        final List<Map<String, String>> gattServiceData = new ArrayList<>();
        final List<List<Map<String, String>>> gattCharacteristicData = new ArrayList<>();
        final List<List<BluetoothGattCharacteristic>> fullGattCharacteristics = new ArrayList<>();

        // Loops through available GATT Services.
        String uuid;
        for (final BluetoothGattService gattService : gattServices) {
            final Map<String, String> currentServiceData = new HashMap<>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(LIST_NAME, GattAttributeResolver.getAttributeName(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            final List<Map<String, String>> gattCharacteristicGroupData = new ArrayList<>();
            final List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            final List<BluetoothGattCharacteristic> charas = new ArrayList<>();

            // Loops through available Characteristics.
            for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                final Map<String, String> currentCharaData = new HashMap<>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(LIST_NAME, GattAttributeResolver.getAttributeName(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }

            fullGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }

        return new GattDataAdapter(
                context,
                fullGattCharacteristics,
                gattServiceData,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{LIST_NAME, LIST_UUID},
                new int[]{android.R.id.text1, android.R.id.text2},
                gattCharacteristicData,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{LIST_NAME, LIST_UUID},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
    }


    public static class GattDataAdapter extends SimpleExpandableListAdapter {

        private final List<List<BluetoothGattCharacteristic>> mGattCharacteristics;

        public GattDataAdapter(Context context,
                               List<List<BluetoothGattCharacteristic>> gattCharacteristics,
                               List<Map<String, String>> groupData,
                               int groupLayout, String[] groupFrom,
                               int[] groupTo,
                               List<List<Map<String, String>>> childData,
                               int childLayout,
                               String[] childFrom,
                               int[] childTo) {

            super(context, groupData, groupLayout, groupFrom, groupTo, childData, childLayout, childFrom, childTo);
            mGattCharacteristics = gattCharacteristics;
        }

        public BluetoothGattCharacteristic getBluetoothGattCharacteristic(final int groupPosition, final int childPosition) {
            return mGattCharacteristics.get(groupPosition).get(childPosition);
        }
    }
}
