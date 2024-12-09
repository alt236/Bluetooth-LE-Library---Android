package uk.co.alt236.btlescan.ui.main.share

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import dev.alt236.bluetoothlelib.device.BluetoothLeDevice
import dev.alt236.bluetoothlelib.device.beacon.BeaconType
import dev.alt236.bluetoothlelib.device.beacon.BeaconUtils
import dev.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice
import dev.alt236.bluetoothlelib.util.ByteUtils
import uk.co.alt236.btlescan.BuildConfig
import uk.co.alt236.btlescan.util.TimeFormatter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.Locale

class CsvFileWriter {
    fun writeCsvFile(
        context: Context,
        fileName: String,
        devices: List<BluetoothLeDevice>,
    ): Uri? {
        val filePath = getOutputFile(context, fileName)
        val contents = getListAsCsv(devices)

        return if (saveToFile(filePath, contents)) {
            return FileProvider.getUriForFile(context, AUTHORITY, filePath)
        } else {
            null
        }
    }

    private fun getOutputFile(
        context: Context,
        fileName: String,
    ): File {
        val root = context.filesDir
        val shareableFolder = File(root, "shared_files/")
        shareableFolder.mkdirs()
        return File(shareableFolder, fileName)
    }

    @SuppressLint("MissingPermission") // We check before this is called
    private fun getListAsCsv(deviceList: List<BluetoothLeDevice>): String {
        val sb = StringBuilder()
        sb.append(CsvWriterHelper.addStuff("mac"))
        sb.append(CsvWriterHelper.addStuff("name"))
        sb.append(CsvWriterHelper.addStuff("firstTimestamp"))
        sb.append(CsvWriterHelper.addStuff("firstRssi"))
        sb.append(CsvWriterHelper.addStuff("currentTimestamp"))
        sb.append(CsvWriterHelper.addStuff("currentRssi"))
        sb.append(CsvWriterHelper.addStuff("adRecord"))
        sb.append(CsvWriterHelper.addStuff("iBeacon"))
        sb.append(CsvWriterHelper.addStuff("uuid"))
        sb.append(CsvWriterHelper.addStuff("major"))
        sb.append(CsvWriterHelper.addStuff("minor"))
        sb.append(CsvWriterHelper.addStuff("txPower"))
        sb.append(CsvWriterHelper.addStuff("distance"))
        sb.append(CsvWriterHelper.addStuff("accuracy"))
        sb.append('\n')

        for (device in deviceList) {
            sb.append(CsvWriterHelper.addStuff(device.address))
            sb.append(CsvWriterHelper.addStuff(device.name))
            sb.append(CsvWriterHelper.addStuff(TimeFormatter.getIsoDateTime(device.firstTimestamp)))
            sb.append(CsvWriterHelper.addStuff(device.firstRssi))
            sb.append(CsvWriterHelper.addStuff(TimeFormatter.getIsoDateTime(device.timestamp)))
            sb.append(CsvWriterHelper.addStuff(device.rssi))
            sb.append(CsvWriterHelper.addStuff(ByteUtils.byteArrayToHexString(device.scanRecord)))
            val isIBeacon = BeaconUtils.getBeaconType(device) == BeaconType.IBEACON
            val uuid: String
            val minor: String
            val major: String
            val txPower: String
            val distance: String
            val accuracy: String
            if (isIBeacon) {
                val beacon =
                    IBeaconDevice(
                        device,
                    )
                uuid = beacon.uuid.toString()
                minor = beacon.minor.toString()
                major = beacon.major.toString()
                txPower = beacon.calibratedTxPower.toString()
                distance = beacon.distanceDescriptor.toString().toLowerCase(Locale.US)
                accuracy = beacon.accuracy.toString()
            } else {
                uuid = ""
                minor = ""
                major = ""
                txPower = ""
                distance = ""
                accuracy = ""
            }
            sb.append(CsvWriterHelper.addStuff(isIBeacon))
            sb.append(CsvWriterHelper.addStuff(uuid))
            sb.append(CsvWriterHelper.addStuff(minor))
            sb.append(CsvWriterHelper.addStuff(major))
            sb.append(CsvWriterHelper.addStuff(txPower))
            sb.append(CsvWriterHelper.addStuff(distance))
            sb.append(CsvWriterHelper.addStuff(accuracy))
            sb.append('\n')
        }
        return sb.toString()
    }

    private fun saveToFile(
        file: File,
        contents: String,
    ): Boolean {
        var writer: FileWriter? = null
        return try {
            writer = FileWriter(file)
            writer.append(contents)
            writer.flush()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        } finally {
            try {
                writer?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private companion object {
        const val AUTHORITY = BuildConfig.APPLICATION_ID + ".fileprovider"
    }
}
