package uk.co.alt236.bluetoothlelib.device.beacon.ibeacon;

import junit.framework.TestCase;

import uk.co.alt236.bluetoothlelib.device.beacon.BeaconManufacturerData;

/**
 *
 */
public class IBeaconManufacturerDataTest extends TestCase {
    private static final byte[] NON_BEACON =
            {2, 1, 26, 11, -1, 76, 0, 9, 6, 3, -32, -64, -88,
                    1, 98, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public void testNonIBeaconData() throws Exception{
        try {
            BeaconManufacturerData data = new IBeaconManufacturerData(NON_BEACON);
            fail("Should have thrown an exception");
        } catch (final IllegalArgumentException e){
            // EXPECTED
        }

        try {
            BeaconManufacturerData data = new IBeaconManufacturerData((byte[]) null);
            fail("Should have thrown an exception");
        } catch (final IllegalArgumentException e){
            // EXPECTED
        }

        try {
            BeaconManufacturerData data = new IBeaconManufacturerData(new byte[0]);
            fail("Should have thrown an exception");
        } catch (final IllegalArgumentException e){
            // EXPECTED
        }
    }
}