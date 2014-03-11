package uk.co.alt236.btlescan.util;

import java.nio.ByteBuffer;

public class ByteUtils {
	private static final String HEXES = "0123456789ABCDEF";

	/**
	 * Gets a pretty representation of a Byte Array as a HEX String.
	 *
	 * Sample output: [01, 30, FF, AA]
	 *
	 * @param array the array
	 * @return the string
	 */
	public static String byteArrayToHexString(final byte[] array){
		final StringBuffer sb = new StringBuffer();
		boolean firstEntry = true;
		sb.append('[');

		for ( final byte b : array ) {
			if(!firstEntry){
				sb.append(", ");
			}
			sb.append(HEXES.charAt((b & 0xF0) >> 4));
			sb.append(HEXES.charAt((b & 0x0F)));
			firstEntry = false;
		}

		sb.append(']');
		return sb.toString();
	}

	public static byte[] invertArray(byte[] array){
		final int size = array.length;
		byte temp;

		for (int i = 0; i < size/2; i++)
		  {
		     temp = array[i];
		     array[i] = array[size-1 - i];
		     array[size-1 - i] = temp;
		  }

		return array;
	}

	public static int getIntFrom2ByteArray(byte[] input){
		final byte[] result = new byte[4];

		result[0] = 0;
		result[1] = 0;
		result[2] = input[0];
		result[3] = input[1];

		return ByteUtils.getIntFromByteArray(result);
	}

	/**
	 * Converts a byte to an int, preserving the sign.
	 *
	 * For example, FF will be converted to 255 and not -1.
	 *
	 * @param bite the bite
	 * @return the int from byte
	 */
	public static int getIntFromByte(final byte bite){
		return Integer.valueOf(bite & 0xFF);
	}

	/**
	 * Converts a byte array to an integer;
	 *
	 * @param bytes the bytes
	 * @return the int from byte array
	 */
	public static int getIntFromByteArray(final byte[] bytes) {
	     return ByteBuffer.wrap(bytes).getInt();
	}


	/**
	 * Converts a byte array to a long;
	 *
	 * @param bytes the bytes
	 * @return the long from byte array
	 */
	public static long getLongFromByteArray(final byte[] bytes) {
	     return ByteBuffer.wrap(bytes).getLong();
	}
}
