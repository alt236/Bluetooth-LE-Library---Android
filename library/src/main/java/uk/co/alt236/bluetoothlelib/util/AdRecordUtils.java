package uk.co.alt236.bluetoothlelib.util;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecord;

public final class AdRecordUtils {

    private AdRecordUtils(){
        // TO AVOID INSTANTIATION
    }

    public static String getRecordDataAsString(final AdRecord record) {
        if (record == null) {
            return "";
        }

        return toString(record.getData());
    }

    public static byte[] getServiceData(final AdRecord record) {
        if (record == null) {
            return null;
        }
        if (record.getType() != AdRecord.TYPE_SERVICE_DATA) return null;

        final byte[] raw = record.getData();
        //Chop out the uuid
        return Arrays.copyOfRange(raw, 2, raw.length);
    }

    public static int getServiceDataUuid(final AdRecord record) {
        if (record == null) {
            return -1;
        }
        if (record.getType() != AdRecord.TYPE_SERVICE_DATA) return -1;

        final byte[] raw = record.getData();
        //Find UUID data in byte array
        int uuid = (raw[1] & 0xFF) << 8;
        uuid += (raw[0] & 0xFF);

        return uuid;
    }

    /*
     * Read out all the AD structures from the raw scan record
     */
    public static List<AdRecord> parseScanRecordAsList(final byte[] scanRecord) {
        final List<AdRecord> records = new ArrayList<>();

        int index = 0;
        while (index < scanRecord.length) {
            final int length = scanRecord[index++];
            //Done once we run out of records
            if (length == 0) break;

            final int type = ByteUtils.getIntFromByte(scanRecord[index]);

            //Done if our record isn't a valid type
            if (type == 0) break;

            final byte[] data = Arrays.copyOfRange(scanRecord, index + 1, index + length);

            records.add(new AdRecord(length, type, data));

            //Advance
            index += length;
        }

        return Collections.unmodifiableList(records);
    }

    @SuppressLint("UseSparseArrays")
    public static Map<Integer, AdRecord> parseScanRecordAsMap(final byte[] scanRecord) {
        final Map<Integer, AdRecord> records = new HashMap<>();

        int index = 0;
        while (index < scanRecord.length) {
            final int length = scanRecord[index++];
            //Done once we run out of records
            if (length == 0) break;

            final int type = ByteUtils.getIntFromByte(scanRecord[index]);

            //Done if our record isn't a valid type
            if (type == 0) break;

            final byte[] data = Arrays.copyOfRange(scanRecord, index + 1, index + length);

            records.put(type, new AdRecord(length, type, data));

            //Advance
            index += length;
        }

        return Collections.unmodifiableMap(records);
    }

    public static SparseArray<AdRecord> parseScanRecordAsSparseArray(final byte[] scanRecord) {
        final SparseArray<AdRecord> records = new SparseArray<>();

        int index = 0;
        while (index < scanRecord.length) {
            final int length = scanRecord[index++];
            //Done once we run out of records
            if (length == 0) break;

            final int type = ByteUtils.getIntFromByte(scanRecord[index]);

            //Done if our record isn't a valid type
            if (type == 0) break;

            final byte[] data = Arrays.copyOfRange(scanRecord, index + 1, index + length);

            records.put(type, new AdRecord(length, type, data));

            //Advance
            index += length;
        }

        return records;
    }

    private static String toString(byte[] array) {
        try {
            //noinspection CharsetObjectCanBeUsed
            return new String(array, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
