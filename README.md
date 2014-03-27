# Bluetooth LE Library for Android

This library allows for easy access to a Bluetooth LE device's Advertisement Records.
It also offers:

* A simple running average RSSI reading keeping.
* For iBeacons: Manufacturer data record parser.
* For iBeacons: Distance indicators (Near, Far, Immediate, Unknown).
* For iBeacons: A decently inaccurate (due to real world issues) distance approximation.
* All the new object types are Parcelable.

This will only work on Android 4.3 (API Level 18). 

Sample app available on the [Play Store](https://play.google.com/store/apps/details?id=uk.co.alt236.btlescan) 

## Including the Library in Your Project
There are two ways to use this library:

a. Download a copy of the ReflectiveDrawableLoader library and reference it in your project.
b. Create a Jar file (see Jarification below) and add it into your project.


### Jarification

Type `ant jar` at the root of the Library Project to produce a Jar file.

## Using the Library
In the `onLeScan()` method of your `BluetoothAdapter.LeScanCallback()` create a new BluetoothLeDevice with the given information.

For example:

<pre>
   private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
   
        @Override
		public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
		
			final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());
			
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mDeviceStore.addDevice(deviceLe);
					mLeDeviceListAdapter.replaceData(mDeviceStore.getDeviceList());
				}
				
			});
		}
	};
</pre>

### Device Properties

Once you have created a device, you can access the following methods:

* `getAddress()` : Gets the MAC Address of the device
* `getAdRecordStore()`: Gives access to a device's Ad Records
* `getDevice()`: Gives access to the standard BluetoothDevice object
* `getFirstRssi()`: Retrieves the RSSI value which was used when the object was created
* `getFirstTimestamp()` Retrieves the timestamp (in millis) which was used when the object was created
* `getRssi()` Gets the current RSSI measurement (see note below).
* `getScanRecord()` Retrieves the RAW scan record array
* `getTimestamp()` Gets the timestamp of the last RSSI measurement
* `getRunningAverageRssi()` Retrieves the internally calculated running average RSSI value (see note below).


**Note:** The Running Average RSSI is not updated automatically (i.e. the library does not monitor on its own in the background). To add another measurement, you need to call `updateRssiReading(long timestamp, int rssiReading)`.


### Accessing the Advertisment (Ad) Records

Once you've created a BluetoothLe device, you can access the AdRecord store via the `leDevice.getAdRecordStore()`. Once you have the AdRecordStore you can use the following methods:

* `getRecord(int recordNo)`: Gets the AdRecord object corresponding to the recordNumber.
* `getRecordDataAsString(int recordNo)` : Gets the AdRecord contents as a String (expect non printable characters in most cases).
* `isRecordPresent(int recordNo)`: Checks to see if a record exists.

**Note:** Record numbers are declared in the Bluetooth 4 spec which can be found [here](https://developer.bluetooth.org/TechnologyOverview/Pages/core-specification.aspx).
They are also declared as constants in `AdRecord.java`.

### Fun with iBeacons
You can check if a device is an iBeacon by using `IBeaconUtils.isThisAnIBeacon(BluetootLeDevice device)`. Once you have confirmed that it is, you can create a new IBeaconDevice via the IBeaconDevice constructor.

An IBeaconDevice extends BluetoothLeDevice, so you still have access to the same methods as before. In addition you can do the following:

* `getAccuracy()`: Gets the estimated Accuracy of the reading in meters based on a simple running average calculation
* `getCalibratedTxPower()`: Gets the calibrated TX power of the iBeacon device as reported
* `getCompanyIdentifier()`: Gets the iBeacon company identifier (this should always be 0x004C for Apple)
* `getDistanceDescriptor()`: Gets the estimated Distance descriptor (an enum)
* `getIBeaconData()`: Gets the raw IBeaconManufacturerData object.
* `getUUID()`: Gets the device's UUID
* `getMajor()`: Gets the device's Major value
* `getMinor()`: Gets the device's Major value


### Lookup Functions
You can also lookup values and convert them to human friendly strings:
* `BluetoothClassResolver.resolveDeviceClass(int btClass)`: Will try to resolve a Blueotooth Device class
* `CompanyIdentifierResolver.getCompanyName(int companyId, String fallback)`: Will try to resolve a Company identifier to the company name
* `GattAttributeResolver.getAttributeName(String uuid, String fallback)`: Will try to convert a UUID to its name.

**Note:** The data can be found as ODS (Open Office Spreadsheets) in the documents folder. 

## Changelog
* v0.0.1 First public release

## Permission Explanation
You will need the following permissions to access the Bluetooth Hardware

* `android.permission.BLUETOOTH`
* `android.permission.BLUETOOTH_ADMIN`

## TODO

* Tidy up Javadoc. There is quite a lot of it that is template
* Add parsers for common Ad Records.

## Sample App Screenshots

![screenshot1](https://github.com/alt236/Bluetooth-LE-Library---Android/raw/master/screenshots/screenshot_1.png)
![screenshot2](https://github.com/alt236/Bluetooth-LE-Library---Android/raw/master/screenshots/screenshot_2.png)
![screenshot3](https://github.com/alt236/Bluetooth-LE-Library---Android/raw/master/screenshots/screenshot_3.png)

## Links
* Github: [https://github.com/alt236/Bluetooth-LE-Library---Android]()

## Credits
Author: [Alexandros Schillings](https://github.com/alt236).

* The Accuracy calculation algorithm was taken from: http://stackoverflow.com/questions/20416218/understanding-ibeacon-distancing
* The AdRecord parser was taken from: https://github.com/devunwired/accessory-samples
* The sample application has been adapted from Android's Bluetooth LE example

All logos are the property of their respective owners.

The code in this project is licensed under the Apache Software License 2.0.

Copyright (c) 2014 Alexandros Schillings.