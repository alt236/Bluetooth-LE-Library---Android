package uk.co.alt236.bluetoothlelib.device.beacon;

import junit.framework.TestCase;

/**
 *
 */
public class BeaconUtilsTest extends TestCase {

    public void testGetBeaconTypeIBeacon() throws Exception {
        assertEquals(BeaconType.IBEACON, BeaconUtils.getBeaconType(new byte[]{
                0x4C, 0x00, 0x02, 0x15, 0x00, // <- Magic iBeacon header
                0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00
        }));
    }

    public void testGetBeaconTypeInvalid() throws Exception {
        assertEquals(BeaconType.NOT_A_BEACON, BeaconUtils.getBeaconType((byte[]) null));
        assertEquals(BeaconType.NOT_A_BEACON, BeaconUtils.getBeaconType(new byte[0]));
        assertEquals(BeaconType.NOT_A_BEACON, BeaconUtils.getBeaconType(new byte[25]));
    }
}