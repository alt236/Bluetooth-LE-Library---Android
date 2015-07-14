package uk.co.alt236.bluetoothlelib.util;

import junit.framework.TestCase;

/**
 *
 */
public class ByteUtilsTest extends TestCase {

    public void testByteArrayToHexString() throws Exception {
        assertEquals("[]", ByteUtils.byteArrayToHexString(new byte[0]));

        final byte[] one = {1, 10, 15, 127};
        assertEquals("[01, 0A, 0F, 7F]", ByteUtils.byteArrayToHexString(one));
    }

    public void testDoesArrayBeginWith() throws Exception {

        // If the prefix is longer than the array,
        // we automatically fail
        byte[] array = new byte[10];
        byte[] prefix = new byte[array.length * 2];
        assertFalse(ByteUtils.doesArrayBeginWith(array, prefix));

        array = new byte[]{1, 2, 3};
        prefix = new byte[]{1, 3};
        assertFalse(ByteUtils.doesArrayBeginWith(array, prefix));

        array = new byte[10];
        prefix = new byte[array.length];
        assertTrue(ByteUtils.doesArrayBeginWith(array, prefix));

        array = new byte[]{1, 2, 3};
        prefix = new byte[]{1, 2};
        assertTrue(ByteUtils.doesArrayBeginWith(array, prefix));
    }

    public void testGetIntFromByte() throws Exception {
        byte bite = 127;
        int integer = ByteUtils.getIntFromByte(bite);
        assertEquals(127, integer);

        bite = -1;
        integer = ByteUtils.getIntFromByte(bite);
        assertEquals(255, integer);
    }

    public void testInvertArray() throws Exception {
        final byte[] original = {1, 2 ,3 ,4};
        final byte[] out = new byte[original.length];

        System.arraycopy( original, 0, out, 0, original.length);
        ByteUtils.invertArray(out);

        assertEquals(original[0], out[3]);
        assertEquals(original[1], out[2]);
        assertEquals(original[2], out[1]);
        assertEquals(original[3], out[0]);
    }
}