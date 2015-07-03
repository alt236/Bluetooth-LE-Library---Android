package uk.co.alt236.bluetoothlelib.util;

import junit.framework.TestCase;

/**
 *
 */
public class IBeaconUtilsTest extends TestCase {

    public void testIsThisAnIBeacon() throws Exception {
        assertFalse(IBeaconUtils.isThisAnIBeacon((byte[]) null));
        assertFalse(IBeaconUtils.isThisAnIBeacon(new byte[0]));
        assertFalse(IBeaconUtils.isThisAnIBeacon(new byte[25]));

        assertTrue(IBeaconUtils.isThisAnIBeacon(new byte[]{
                0x4C, 0x00, 0x02, 0x15, 0x00, // <- Magic iBeacon header
                0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00
        }));
    }

    public void testGetDistanceDescriptor() throws Exception {
        assertEquals(IBeaconDistanceDescriptor.UNKNOWN, IBeaconUtils.getDistanceDescriptor(-1));

        assertEquals(IBeaconDistanceDescriptor.IMMEDIATE, IBeaconUtils.getDistanceDescriptor(0));
        assertEquals(IBeaconDistanceDescriptor.IMMEDIATE, IBeaconUtils.getDistanceDescriptor(0.4));

        assertEquals(IBeaconDistanceDescriptor.NEAR, IBeaconUtils.getDistanceDescriptor(0.5));
        assertEquals(IBeaconDistanceDescriptor.NEAR, IBeaconUtils.getDistanceDescriptor(2.9));

        assertEquals(IBeaconDistanceDescriptor.FAR, IBeaconUtils.getDistanceDescriptor(3));
    }

    public void testCalculateUuidString() throws Exception {
        assertEquals("00", IBeaconUtils.calculateUuidString(new byte[]{0}));
        assertEquals("0a", IBeaconUtils.calculateUuidString(new byte[]{10}));
        assertEquals("0f", IBeaconUtils.calculateUuidString(new byte[]{15}));
        assertEquals("10", IBeaconUtils.calculateUuidString(new byte[]{16}));
        assertEquals("7f", IBeaconUtils.calculateUuidString(new byte[]{127}));
        assertEquals(
                "00000000-0000-0000-0000-00",
                IBeaconUtils.calculateUuidString(new byte[]{0,0,0,0,0,0,0,0,0,0,0}));
    }
}