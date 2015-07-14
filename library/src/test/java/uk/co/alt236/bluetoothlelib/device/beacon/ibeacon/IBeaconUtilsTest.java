package uk.co.alt236.bluetoothlelib.device.beacon.ibeacon;

import junit.framework.TestCase;

/**
 *
 */
public class IBeaconUtilsTest extends TestCase {

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

    public void testGetDistanceDescriptor() throws Exception {
        assertEquals(IBeaconDistanceDescriptor.UNKNOWN, IBeaconUtils.getDistanceDescriptor(-1));

        assertEquals(IBeaconDistanceDescriptor.IMMEDIATE, IBeaconUtils.getDistanceDescriptor(0));
        assertEquals(IBeaconDistanceDescriptor.IMMEDIATE, IBeaconUtils.getDistanceDescriptor(0.4));

        assertEquals(IBeaconDistanceDescriptor.NEAR, IBeaconUtils.getDistanceDescriptor(0.5));
        assertEquals(IBeaconDistanceDescriptor.NEAR, IBeaconUtils.getDistanceDescriptor(2.9));

        assertEquals(IBeaconDistanceDescriptor.FAR, IBeaconUtils.getDistanceDescriptor(3));
    }
}