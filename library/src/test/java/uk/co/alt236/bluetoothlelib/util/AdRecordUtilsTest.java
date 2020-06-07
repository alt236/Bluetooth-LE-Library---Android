package uk.co.alt236.bluetoothlelib.util;

import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import uk.co.alt236.bluetoothlelib.device.adrecord.AdRecord;

/**
 * Advertisement records related tests.
 */
public class AdRecordUtilsTest extends TestCase {
    private static final byte[] NON_IBEACON = {
        2, 1, 26, 11, -1, 76, 0, 9, 6, 3, -32, -64, -88, 1, 98, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0
    };

    public void testParseScanRecordAsList() {
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

    public void testParseScanRecordAsMap() {
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

    /**
     * Test negative record lengths. In Java all primitive types are signed, and we could receive
     * a negative length in the record. Thus, we always have to convert it into unsigned.
     */
    public void testNegativeRecordLength() {
        // (byte)0x80 & 0xFF = 170. In Java (byte)0x80 = -128.
        Map<Integer, AdRecord> adRecords =
            AdRecordUtils.parseScanRecordAsMap(new byte[] { -128, 1, 0 });
        assertNotNull(adRecords);
        assertEquals(1, adRecords.size());
        assertEquals(128, adRecords.get(1).getLength());

        // (byte)0xAA & 0xFF = 170. In Java (byte)0xAA = -86.
        adRecords =
            AdRecordUtils.parseScanRecordAsMap(new byte[] { -86, 1, 0 });
        assertNotNull(adRecords);
        assertEquals(1, adRecords.size());
        assertEquals(170, adRecords.get(1).getLength());
    }

    public void testParseScanRecordAsSparseArray() {
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