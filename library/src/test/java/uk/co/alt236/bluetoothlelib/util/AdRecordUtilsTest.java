package uk.co.alt236.bluetoothlelib.util;

import junit.framework.TestCase;

import java.util.List;
import java.util.Map;

import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecord;

/**
 *
 */
public class AdRecordUtilsTest extends TestCase {
    private static final byte[] NON_IBEACON =
            {2, 1, 26, 11, -1, 76, 0, 9, 6, 3, -32, -64, -88,
                    1, 98, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public void testParseScanRecordAsList() throws Exception {
        final List<AdRecord> adRecords = AdRecordUtils.parseScanRecordAsList(NON_IBEACON);
        assertNotNull(adRecords);
        assertEquals(2, adRecords.size());

        int type = AdRecord.TYPE_FLAGS;
        assertEquals(type, adRecords.get(0).getType());
        assertEquals(2, adRecords.get(0).getLength());

        type = AdRecord.TYPE_MANUFACTURER_SPECIFIC_DATA;
        assertEquals(type, adRecords.get(1).getType());
        assertEquals(11, adRecords.get(1).getLength());
    }

    public void testParseScanRecordAsMap() throws Exception {
        final Map<Integer, AdRecord> adRecords = AdRecordUtils.parseScanRecordAsMap(NON_IBEACON);
        assertNotNull(adRecords);
        assertEquals(2, adRecords.size());

        int type = AdRecord.TYPE_FLAGS;
        assertEquals(type, adRecords.get(type).getType());
        assertEquals(2, adRecords.get(type).getLength());

        type = AdRecord.TYPE_MANUFACTURER_SPECIFIC_DATA;
        assertEquals(type, adRecords.get(type).getType());
        assertEquals(11, adRecords.get(type).getLength());
    }

    public void testParseScanRecordAsSparseArray() throws Exception {
        //
        // Cannot be tested here as it relies on Android code...
        //
        //        final SparseArray<AdRecord> adRecords = AdRecordUtils.parseScanRecordAsSparseArray(NON_IBEACON);
        //        assertNotNull(adRecords);
        //        assertEquals(2, adRecords.size());
        //        assertEquals(AdRecord.TYPE_FLAGS, adRecords.get(AdRecord.TYPE_FLAGS).getType());
        //        assertEquals(AdRecord.TYPE_MANUFACTURER_SPECIFIC_DATA, adRecords.get(AdRecord.TYPE_MANUFACTURER_SPECIFIC_DATA).getType());
    }
}