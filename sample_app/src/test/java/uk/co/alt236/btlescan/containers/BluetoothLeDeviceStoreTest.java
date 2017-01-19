package uk.co.alt236.btlescan.containers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class BluetoothLeDeviceStoreTest {
    private BluetoothLeDeviceStore cut;

    @Before
    public void setUp() {
        cut = new BluetoothLeDeviceStore();
    }

    @Test
    public void testAddOne() {
        assertStoreSize(0);

        cut.addDevice(createDevice("foo"));
        assertStoreSize(1);

        cut.clear();
        assertStoreSize(0);
    }

    @Test
    public void testAddTwo() {
        assertStoreSize(0);

        cut.addDevice(createDevice("foo"));
        assertStoreSize(1);

        cut.addDevice(createDevice("bar"));
        assertStoreSize(2);

        cut.clear();
        assertStoreSize(0);
    }

    @Test
    public void testUpdateOne() {
        assertStoreSize(0);

        cut.addDevice(createDevice("foo", 100, 101));
        assertStoreSize(1);
        final BluetoothLeDevice device1 = cut.getDeviceList().get(0);
        assertEquals(100, device1.getTimestamp());
        assertEquals(101, device1.getRssi());

        cut.addDevice(createDevice("foo", 200, 201));
        assertStoreSize(1);
        final BluetoothLeDevice device2 = cut.getDeviceList().get(0);
        assertSame(device1, device2);
        Mockito
                .verify(device2, Mockito.times(1))
                .updateRssiReading(200, 201);

        cut.clear();
        assertStoreSize(0);
    }


    private BluetoothLeDevice createDevice(final String mac) {
        final BluetoothLeDevice mock = Mockito.mock(BluetoothLeDevice.class);
        return createDevice(mac, 0, 0);
    }

    private BluetoothLeDevice createDevice(final String mac, long rssiTime, int rssi) {
        final BluetoothLeDevice mock = Mockito.mock(BluetoothLeDevice.class);

        Mockito.when(mock.getAddress()).thenReturn(mac);
        Mockito.when(mock.getTimestamp()).thenReturn(rssiTime);
        Mockito.when(mock.getRssi()).thenReturn(rssi);

        return mock;
    }

    private void assertStoreSize(final int expected) {
        assertEquals(expected, cut.getSize());
        assertEquals(expected, cut.getDeviceCursor().getCount());
        assertEquals(expected, cut.getDeviceList().size());
    }
}