package uk.co.alt236.btlescan.util;

public class ByteUtils {
	static final String HEXES = "0123456789ABCDEF";

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


	public static int convertByteToInt(byte bite){
		return Integer.valueOf(bite & 0xFF);
	}
}
