package uk.co.alt236.bluetoothlelib.device.adrecord;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by Dave Smith
 * Double Encore, Inc.
 * <p>
 * Expanded by Alexandros Schillings
 */
public final class AdRecord implements Parcelable {
    //	02 # Number of bytes that follow in first AD structure
    //	01 # Flags AD type
    //	1A # Flags value 0x1A = 000011010
    //	   bit 0 (OFF) LE Limited Discoverable Mode
    //	   bit 1 (ON) LE General Discoverable Mode
    //	   bit 2 (OFF) BR/EDR Not Supported
    //	   bit 3 (ON) Simultaneous LE and BR/EDR to Same Device Capable (controller)
    //	   bit 4 (ON) Simultaneous LE and BR/EDR to Same Device Capable (Host)
    //	1A # Number of bytes that follow in second (and last) AD structure
    //	FF # Manufacturer specific data AD type
    //	4C 00 # Company identifier code (0x004C == Apple)
    //	02 # Byte 0 of iBeacon advertisement indicator
    //	15 # Byte 1 of iBeacon advertisement indicator
    //	e2 c5 6d b5 df fb 48 d2 b0 60 d0 f5 a7 10 96 e0 # iBeacon proximity uuid
    //	00 00 # major
    //	00 00 # minor
    //	c5 # The 2's complement of the calibrated Tx Power


    /**
     * General FLAGS
     * <p>
     * Description: Flags
     * <p>
     * Information:
     * Bit 0: LE Limited Discoverable Mode
     * Bit 1: LE General Discoverable Mode
     * Bit 2: BR/EDR Not Supported (i.e. bit 37 of LMP Extended Feature bits Page 0)
     * Bit 3: Simultaneous LE and BR/EDR to Same Device Capable (Controller) (i.e. bit 49 of LMP Extended Feature bits Page 0)
     * Bit 4: Simultaneous LE and BR/EDR to Same Device Capable (Host) (i.e. bit 66 of LMP Extended Feature bits Page 1)
     * Bits 5-7 Reserved
     */
    public static final int TYPE_FLAGS = 0x01;
    // SERVICE
    public static final int TYPE_UUID16_INC = 0x02;
    public static final int TYPE_UUID16 = 0x03;
    public static final int TYPE_UUID32_INC = 0x04;
    public static final int TYPE_UUID32 = 0x05;
    public static final int TYPE_UUID128_INC = 0x06;
    public static final int TYPE_UUID128 = 0x07;
    // Local name
    public static final int TYPE_LOCAL_NAME_SHORT = 0x08;
    public static final int TYPE_LOCAL_NAME_COMPLETE = 0x09;
    // TX Power Level
    public static final int TYPE_TX_POWER_LEVEL = 0x0A;
    // SIMPLE PAIRING OPTIONAL OOB TAGS
    public static final int TYPE_DEVICE_CLASS = 0x0D;
    public static final int TYPE_SIMPLE_PAIRING_HASH_C = 0x0E;
    public static final int TYPE_SIMPLE_PAIRING_RANDOMIZER_R = 0x0F;
    // SECURITY MANAGER TK VALUE
    public static final int TYPE_TK_VALUE = 0x10;
    /* SECURITY MANAGER OOB FLAGS
     *
     * Description: Flag (1 octet)
     *
     * Information:
     * Bit 0: OOB Flags Field: (0 = OOB data not present, 1 = OOB data present)
     * Bit 1: LE supported (Host) (i.e. bit 65 of LMP Extended Feature bits Page 1
     * Bit 2: Simultaneous LE and BR/EDR to Same Device Capable (Host) (i.e. bit 66 of LMP Extended Feature bits Page 1)
     * Bit 3: Address type (0 = Public Address, 1 = Random Address)
     * Bits 4-7 Reserved
     */
    public static final int TYPE_SECURITY_MANAGER_OOB_FLAGS = 0x11;
    /* SLAVE CONNECTION INTERVAL RANGE
     *
     * Description: Slave Connection Interval Range
     *
     * Information:
     * The first 2 octets defines the minimum value for the connection interval in the following manner:
     *	connInterval min = Conn_Interval_Min * 1.25 ms
     *	Conn_Interval_Min range: 0x0006 to 0x0C80
     *	Value of 0xFFFF indicates no specific minimum.
     *	Values outside the range are reserved. (excluding 0xFFFF)
     *
     * The second 2 octets defines the maximum value for the connection interval in the following manner:
     *  connInterval max = Conn_Interval_Max * 1.25 ms
     *  Conn_Interval_Max range: 0x0006 to 0x0C80
     *  Conn_Interval_Max shall be equal to or greater
     *  than the Conn_Interval_Min.
     *  Value of 0xFFFF indicates no specific maximum.
     *  Values outside the range are reserved (excluding 0xFFFF)
     */
    public static final int TYPE_CONNECTION_INTERVAL_RANGE = 0x12;
    // SERVICE SOLICITATION
    public static final int TYPE_SERVICE_UUIDS_LIST_16BIT = 0x14;
    public static final int TYPE_SERVICE_UUIDS_LIST_128BIT = 0x15;
    /* SERVICE DATA
     *
     * Description: Service Data (2 or more octets)
     * Information: The first 2 octets contain the 16 bit Service UUID followed by additional service data
     */
    public static final int TYPE_SERVICE_DATA = 0x16;
    /* MANUFACTURER SPECIFIC DATA
     *
     * Description: Manufacturer Specific Data (2 or more octets)
     * Information: The first 2 octets contain the Company Identifier Code followed by additional manufacturer specific data
     */
    public static final int TYPE_MANUFACTURER_SPECIFIC_DATA = 0xFF;
    public static final Parcelable.Creator<AdRecord> CREATOR = new Parcelable.Creator<AdRecord>() {
        public AdRecord createFromParcel(final Parcel in) {
            return new AdRecord(in);
        }

        public AdRecord[] newArray(final int size) {
            return new AdRecord[size];
        }
    };
    private static final String PARCEL_RECORD_DATA = "record_data";
    private static final String PARCEL_RECORD_TYPE = "record_type";
    private static final String PARCEL_RECORD_LENGTH = "record_length";
    /* Model Object Definition */
    private final int mLength;
    private final int mType;
    private final byte[] mData;

    public AdRecord(final int length, final int type, final byte[] data) {
        mLength = length;
        mType = type;
        mData = data;
    }

    public AdRecord(final Parcel in) {
        final Bundle b = in.readBundle(getClass().getClassLoader());
        mLength = b.getInt(PARCEL_RECORD_LENGTH);
        mType = b.getInt(PARCEL_RECORD_TYPE);
        mData = b.getByteArray(PARCEL_RECORD_DATA);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getData() {
        return mData;
    }

    public String getHumanReadableType() {
        return getHumanReadableAdType(mType);
    }

    public int getLength() {
        return mLength;
    }

    public int getType() {
        return mType;
    }

    @Override
    public String toString() {
        return "AdRecord [mLength=" + mLength + ", mType=" + mType + ", mData=" + Arrays.toString(mData) + ", getHumanReadableType()=" + getHumanReadableType() + "]";
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int arg1) {
        final Bundle b = new Bundle(getClass().getClassLoader());

        b.putInt(PARCEL_RECORD_LENGTH, mLength);
        b.putInt(PARCEL_RECORD_TYPE, mType);
        b.putByteArray(PARCEL_RECORD_DATA, mData);

        parcel.writeBundle(b);
    }

    private static String getHumanReadableAdType(final int type) {
        switch (type) {
            case TYPE_CONNECTION_INTERVAL_RANGE:
                return "Slave Connection Interval Range";
            case TYPE_DEVICE_CLASS:
                return "Class of device";
            case TYPE_FLAGS:
                return "Flags";
            case TYPE_MANUFACTURER_SPECIFIC_DATA:
                return "Manufacturer Specific Data";
            case TYPE_LOCAL_NAME_COMPLETE:
                return "Name (Complete)";
            case TYPE_LOCAL_NAME_SHORT:
                return "Name (Short)";
            case TYPE_SECURITY_MANAGER_OOB_FLAGS:
                return "Security Manager OOB Flags";
            case TYPE_SERVICE_UUIDS_LIST_128BIT:
                return "Service UUIDs (128bit)";
            case TYPE_SERVICE_UUIDS_LIST_16BIT:
                return "Service UUIDs (16bit)";
            case TYPE_SERVICE_DATA:
                return "Service Data";
            case TYPE_SIMPLE_PAIRING_HASH_C:
                return "Simple Pairing Hash C";
            case TYPE_SIMPLE_PAIRING_RANDOMIZER_R:
                return "Simple Pairing Randomizer R";
            case TYPE_TK_VALUE:
                return "TK Value";
            case TYPE_TX_POWER_LEVEL:
                return "Transmission Power Level";
            case TYPE_UUID128:
                return "Complete list of 128-bit UUIDs available";
            case TYPE_UUID128_INC:
                return "More 128-bit UUIDs available";
            case TYPE_UUID16:
                return "Complete list of 16-bit UUIDs available";
            case TYPE_UUID16_INC:
                return "More 16-bit UUIDs available";
            case TYPE_UUID32:
                return "Complete list of 32-bit UUIDs available";
            case TYPE_UUID32_INC:
                return "More 32-bit UUIDs available";
            default:
                return "Unknown AdRecord Structure: " + type;
        }
    }
}