package uk.co.alt236.bluetoothlelib.resolvers;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * The UUIDS have been collected from the following sources:
 *
 * - http://developer.nokia.com/community/wiki/Bluetooth_Services_for_Windows_Phone
 * - The Bluez project
 *
 * @author Alexandros Schillings
 *
 */
public class GattAttributeResolver {
	public final static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
	public final static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

	private final static Map<String, String> sGattAttributesMap = populateGattAttributesMap();

	public static String getAttributeName(String uuid, String fallback){
		final String name = sGattAttributesMap.get(uuid.toLowerCase(Locale.US));
		return name == null ? fallback : name;
	}

	private static Map<String, String> populateGattAttributesMap() {
		final Map<String, String> map = new HashMap<String, String>();

		map.put("00000000-0000-1000-8000-00805f9b34fb","Base GUID");
		map.put("00000001-0000-1000-8000-00805f9b34fb","Service Discovery Protocol (SDP)");
		map.put("00000002-0000-1000-8000-00805f9b34fb","User Datagram Protocol (UDP)");
		map.put("00000003-0000-1000-8000-00805f9b34fb","Radio Frequency Communication Protocol (RFCOMM)");
		map.put("00000004-0000-1000-8000-00805f9b34fb","TCP");
		map.put("00000005-0000-1000-8000-00805f9b34fb","TCSBIN");
		map.put("00000006-0000-1000-8000-00805f9b34fb","TCSAT");
		map.put("00000008-0000-1000-8000-00805f9b34fb","Object Exchange Protocol (OBEX)");
		map.put("00000009-0000-1000-8000-00805f9b34fb","IP");
		map.put("0000000a-0000-1000-8000-00805f9b34fb","FTP");
		map.put("0000000c-0000-1000-8000-00805f9b34fb","HTTP");
		map.put("0000000e-0000-1000-8000-00805f9b34fb","WSP");
		map.put("0000000f-0000-1000-8000-00805f9b34fb","BNEP_SVC");
		map.put("00000010-0000-1000-8000-00805f9b34fb","UPNP");
		map.put("00000011-0000-1000-8000-00805f9b34fb","HIDP");
		map.put("00000012-0000-1000-8000-00805f9b34fb","Hardcopy Control Channel Protocol");
		map.put("00000014-0000-1000-8000-00805f9b34fb","Hardcopy Data Channel Protocol");
		map.put("00000016-0000-1000-8000-00805f9b34fb","Hardcopy Notification Protocol");
		map.put("00000017-0000-1000-8000-00805f9b34fb","VCTP Protocol");
		map.put("00000019-0000-1000-8000-00805f9b34fb","VDTP Protocol");
		map.put("0000001b-0000-1000-8000-00805f9b34fb","CMPT Protocol");
		map.put("0000001d-0000-1000-8000-00805f9b34fb","UDI C Plane Protocol");
		map.put("0000001e-0000-1000-8000-00805f9b34fb","MCAP Control Channel");
		map.put("0000001f-0000-1000-8000-00805f9b34fb","MCAP Data Channel");
		map.put("00000100-0000-1000-8000-00805f9b34fb","L2CAP");
		map.put("00001000-0000-1000-8000-00805f9b34fb","Service Discovery Server");
		map.put("00001001-0000-1000-8000-00805f9b34fb","Browse Group Descriptor");
		map.put("00001002-0000-1000-8000-00805f9b34fb","Public Browse Group");
		map.put("00001101-0000-1000-8000-00805f9b34fb","SPP");
		map.put("00001102-0000-1000-8000-00805f9b34fb","LAN Access Using PPP");
		map.put("00001103-0000-1000-8000-00805f9b34fb","DUN_GW");
		map.put("00001104-0000-1000-8000-00805f9b34fb","OBEX_SYNC");
		map.put("00001105-0000-1000-8000-00805f9b34fb","OBEX Object Push");
		map.put("00001106-0000-1000-8000-00805f9b34fb","OBEX File Transfer");
		map.put("00001107-0000-1000-8000-00805f9b34fb","IrMC Sync Command");
		map.put("00001108-0000-1000-8000-00805f9b34fb","HSP_HS");
		map.put("00001109-0000-1000-8000-00805f9b34fb","Cordless Telephony");
		map.put("0000110a-0000-1000-8000-00805f9b34fb","Audio Source");
		map.put("0000110b-0000-1000-8000-00805f9b34fb","Audio Sink");
		map.put("0000110c-0000-1000-8000-00805f9b34fb","AV Remote Control Target");
		map.put("0000110d-0000-1000-8000-00805f9b34fb","ADVANCED_AUDIO");
		map.put("0000110e-0000-1000-8000-00805f9b34fb","AVRCP_REMOTE");
		map.put("0000110f-0000-1000-8000-00805f9b34fb","Video Conferencing");
		map.put("00001110-0000-1000-8000-00805f9b34fb","Intercom");
		map.put("00001111-0000-1000-8000-00805f9b34fb","FAX");
		map.put("00001112-0000-1000-8000-00805f9b34fb","Headset Profile (HSP) - Audio Gateway");
		map.put("00001113-0000-1000-8000-00805f9b34fb","WAP");
		map.put("00001114-0000-1000-8000-00805f9b34fb","WAP Client");
		map.put("00001115-0000-1000-8000-00805f9b34fb","PANU");
		map.put("00001116-0000-1000-8000-00805f9b34fb","NAP");
		map.put("00001117-0000-1000-8000-00805f9b34fb","GN");
		map.put("00001118-0000-1000-8000-00805f9b34fb","Direct Printing");
		map.put("00001119-0000-1000-8000-00805f9b34fb","Reference Printing");
		map.put("0000111a-0000-1000-8000-00805f9b34fb","Imaging");
		map.put("0000111b-0000-1000-8000-00805f9b34fb","Imaging Responder");
		map.put("0000111c-0000-1000-8000-00805f9b34fb","Imaging Automatic Archive");
		map.put("0000111d-0000-1000-8000-00805f9b34fb","Imaging Reference Objects");
		map.put("0000111e-0000-1000-8000-00805f9b34fb","Hands Free Profile (HFP)");
		map.put("0000111f-0000-1000-8000-00805f9b34fb","Hands Free Profile (HFP) – Audio Gateway");
		map.put("00001120-0000-1000-8000-00805f9b34fb","Direct Printing Reference Objects");
		map.put("00001121-0000-1000-8000-00805f9b34fb","Reflected UI");
		map.put("00001122-0000-1000-8000-00805f9b34fb","Basic Printing");
		map.put("00001123-0000-1000-8000-00805f9b34fb","Printing Status");
		map.put("00001124-0000-1000-8000-00805f9b34fb","HID");
		map.put("00001125-0000-1000-8000-00805f9b34fb","Hardcopy Cable Replacement");
		map.put("00001126-0000-1000-8000-00805f9b34fb","HCR Print");
		map.put("00001127-0000-1000-8000-00805f9b34fb","HCR Scan");
		map.put("00001128-0000-1000-8000-00805f9b34fb","Common ISDN Access");
		map.put("00001129-0000-1000-8000-00805f9b34fb","Video Conferencing Gateway");
		map.put("0000112a-0000-1000-8000-00805f9b34fb","UDIMT");
		map.put("0000112b-0000-1000-8000-00805f9b34fb","UDITA");
		map.put("0000112c-0000-1000-8000-00805f9b34fb","Audio Video");
		map.put("0000112d-0000-1000-8000-00805f9b34fb","SIM Access");
		map.put("0000112e-0000-1000-8000-00805f9b34fb","OBEX PCE");
		map.put("0000112f-0000-1000-8000-00805f9b34fb","OBEX PSE");
		map.put("00001130-0000-1000-8000-00805f9b34fb","OBEX PBAP");
		map.put("00001132-0000-1000-8000-00805f9b34fb","OBEX MAS");
		map.put("00001133-0000-1000-8000-00805f9b34fb","OBEX MNS");
		map.put("00001134-0000-1000-8000-00805f9b34fb","OBEX MAP");
		map.put("00001200-0000-1000-8000-00805f9b34fb","PNP");
		map.put("00001201-0000-1000-8000-00805f9b34fb","Generic Networking");
		map.put("00001202-0000-1000-8000-00805f9b34fb","Generic File Transfer");
		map.put("00001203-0000-1000-8000-00805f9b34fb","Generic Audio");
		map.put("00001204-0000-1000-8000-00805f9b34fb","Generic Telephony");
		map.put("00001205-0000-1000-8000-00805f9b34fb","UPNP");
		map.put("00001206-0000-1000-8000-00805f9b34fb","UPNP IP");
		map.put("00001300-0000-1000-8000-00805f9b34fb","ESDP UPnP IP PAN");
		map.put("00001301-0000-1000-8000-00805f9b34fb","ESDP UPnP IP LAP");
		map.put("00001302-0000-1000-8000-00805f9b34fb","ESDP Upnp L2CAP");
		map.put("00001303-0000-1000-8000-00805f9b34fb","Video Distribution Profile (VDP) - Source");
		map.put("00001304-0000-1000-8000-00805f9b34fb","Video Distribution Profile (VDP) - Sink");
		map.put("00001305-0000-1000-8000-00805f9b34fb","Video Distribution Profile (VDP)");
		map.put("00001400-0000-1000-8000-00805f9b34fb","Health Device Profile (HDP)");
		map.put("00001401-0000-1000-8000-00805f9b34fb","Health Device Profile (HDP) - Source");
		map.put("00001402-0000-1000-8000-00805f9b34fb","Health Device Profile (HDP) - Sink");
		map.put("00001800-0000-1000-8000-00805f9b34fb","GAP");
		map.put("00001801-0000-1000-8000-00805f9b34fb","GATT");
		map.put("00001802-0000-1000-8000-00805f9b34fb","IMMEDIATE_ALERT");
		map.put("00001803-0000-1000-8000-00805f9b34fb","LINK_LOSS");
		map.put("00001804-0000-1000-8000-00805f9b34fb","TX_POWER");
		map.put("00001809-0000-1000-8000-00805f9b34fb","Health Thermometer");
		map.put("0000180a-0000-1000-8000-00805f9b34fb","Device Information");
		map.put("0000180d-0000-1000-8000-00805f9b34fb","HEART_RATE");
		map.put("00001816-0000-1000-8000-00805f9b34fb","CYCLING_SC");
		map.put(CLIENT_CHARACTERISTIC_CONFIG,"Client Characteristic Config");
		map.put("00002a00-0000-1000-8000-00805f9b34fb","Device Name");
		map.put("00002a01-0000-1000-8000-00805f9b34fb","Appearance");
		map.put("00002a02-0000-1000-8000-00805f9b34fb","Peripheral Privacy Flag");
		map.put("00002a03-0000-1000-8000-00805f9b34fb","Reconnection Address");
		map.put("00002a04-0000-1000-8000-00805f9b34fb","Peripheral Preferred Connection Parameters");
		map.put("00002a05-0000-1000-8000-00805f9b34fb","Service Changed");
		map.put("00002a06-0000-1000-8000-00805f9b34fb","Alert Level");
		map.put("00002a07-0000-1000-8000-00805f9b34fb","Tx Power Level");
		map.put("00002a08-0000-1000-8000-00805f9b34fb","Date Time");
		map.put("00002a09-0000-1000-8000-00805f9b34fb","Day of Week");
		map.put("00002a0a-0000-1000-8000-00805f9b34fb","Day Date Time");
		map.put("00002a0c-0000-1000-8000-00805f9b34fb","Exact Time 256");
		map.put("00002a0d-0000-1000-8000-00805f9b34fb","DST Offset");
		map.put("00002a0e-0000-1000-8000-00805f9b34fb","Time Zone");
		map.put("00002a0f-0000-1000-8000-00805f9b34fb","Local Time Information");
		map.put("00002a11-0000-1000-8000-00805f9b34fb","Time with DST");
		map.put("00002a12-0000-1000-8000-00805f9b34fb","Time Accuracy");
		map.put("00002a13-0000-1000-8000-00805f9b34fb","Time Source");
		map.put("00002a14-0000-1000-8000-00805f9b34fb","Reference Time Information");
		map.put("00002a16-0000-1000-8000-00805f9b34fb","Time Update Control Point");
		map.put("00002a17-0000-1000-8000-00805f9b34fb","Time Update State");
		map.put("00002a1c-0000-1000-8000-00805f9b34fb","Temperature Measurement");
		map.put("00002a1d-0000-1000-8000-00805f9b34fb","Temperature Type");
		map.put("00002a1e-0000-1000-8000-00805f9b34fb","Intermediate Temperature");
		map.put("00002a21-0000-1000-8000-00805f9b34fb","Measurement Interval");
		map.put("00002a23-0000-1000-8000-00805f9b34fb","System ID");
		map.put("00002a24-0000-1000-8000-00805f9b34fb","Model Number String");
		map.put("00002a25-0000-1000-8000-00805f9b34fb","Serial Number String");
		map.put("00002a26-0000-1000-8000-00805f9b34fb","Firmware Revision String");
		map.put("00002a27-0000-1000-8000-00805f9b34fb","Hardware Revision String");
		map.put("00002a28-0000-1000-8000-00805f9b34fb","Software Revision String");
		map.put("00002a29-0000-1000-8000-00805f9b34fb","Manufacturer Name String");
		map.put("00002a2a-0000-1000-8000-00805f9b34fb","IEEE 11073-20601 Regulatory");
		map.put("00002a2b-0000-1000-8000-00805f9b34fb","Current Time");
		map.put("00002a35-0000-1000-8000-00805f9b34fb","Blood Pressure Measurement");
		map.put("00002a36-0000-1000-8000-00805f9b34fb","Intermediate Cuff Pressure");
		map.put(HEART_RATE_MEASUREMENT,"Heart Rate Measurement");
		map.put("00002a38-0000-1000-8000-00805f9b34fb","Body Sensor Location");
		map.put("00002a39-0000-1000-8000-00805f9b34fb","Heart Rate Control Point");
		map.put("00002a3f-0000-1000-8000-00805f9b34fb","Alert Status");
		map.put("00002a40-0000-1000-8000-00805f9b34fb","Ringer Control Point");
		map.put("00002a41-0000-1000-8000-00805f9b34fb","Ringer Setting");
		map.put("00002a42-0000-1000-8000-00805f9b34fb","Alert Category ID Bit Mask");
		map.put("00002a43-0000-1000-8000-00805f9b34fb","Alert Category ID");
		map.put("00002a44-0000-1000-8000-00805f9b34fb","Alert Notification Control Point");
		map.put("00002a45-0000-1000-8000-00805f9b34fb","Unread Alert Status");
		map.put("00002a46-0000-1000-8000-00805f9b34fb","New Alert");
		map.put("00002a47-0000-1000-8000-00805f9b34fb","Supported New Alert Category");
		map.put("00002a48-0000-1000-8000-00805f9b34fb","Supported Unread Alert Category");
		map.put("00002a49-0000-1000-8000-00805f9b34fb","Blood Pressure Feature");
		map.put("00002a50-0000-1000-8000-00805f9b34fb","PNPID");
		map.put("00002a55-0000-1000-8000-00805f9b34fb","SC_CONTROL_POINT");
		map.put("00002a5b-0000-1000-8000-00805f9b34fb","CSC_MEASUREMENT");
		map.put("00002a5c-0000-1000-8000-00805f9b34fb","CSC_FEATURE");
		map.put("00002a5d-0000-1000-8000-00805f9b34fb","SENSOR_LOCATION");
		map.put("831c4071-7bc8-4a9c-a01c-15df25a4adbc","ActiveSync");

		return map;
	}
}
