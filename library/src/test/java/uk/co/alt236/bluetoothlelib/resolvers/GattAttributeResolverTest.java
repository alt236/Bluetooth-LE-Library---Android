package uk.co.alt236.bluetoothlelib.resolvers;

import junit.framework.TestCase;

/**
 *
 */
public class GattAttributeResolverTest extends TestCase {
    private static final String UKNOWN = "unknown";

    public void testGetAttributeName() throws Exception {
        assertEquals(UKNOWN, GattAttributeResolver.getAttributeName("foo", UKNOWN));
        assertEquals("Estimote Advertising Vector", GattAttributeResolver.getAttributeName("b9402002-f5f8-466e-aff9-25556b57fe6d", UKNOWN));
        assertEquals("LINK_LOSS", GattAttributeResolver.getAttributeName("00001803-0000-1000-8000-00805f9b34fb", UKNOWN));
        assertEquals("Base GUID", GattAttributeResolver.getAttributeName("00000000-0000-1000-8000-00805f9b34fb", UKNOWN));
        assertEquals("PNPID", GattAttributeResolver.getAttributeName("00002a50-0000-1000-8000-00805f9b34fb", UKNOWN));
        assertEquals("HTTP", GattAttributeResolver.getAttributeName("0000000c-0000-1000-8000-00805f9b34fb", UKNOWN));

    }
}