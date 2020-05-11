package uk.co.alt236.btlescan.ui.main.share;

import android.app.Activity;
import android.net.Uri;
import android.widget.Toast;

import java.util.Locale;

import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.containers.BluetoothLeDeviceStore;
import uk.co.alt236.btlescan.ui.common.Navigation;
import uk.co.alt236.btlescan.util.TimeFormatter;

public class Sharer {
    private static final String CSV_FILENAME_PREFIX = "bluetooth_le_%d";
    private static final String CSV_FILENAME_SUFFIX = ".csv";

    private final CsvFileWriter csvFileWriter = new CsvFileWriter();

    public void shareDataAsEmail(final Activity activity,
                                 final BluetoothLeDeviceStore store) {

        final long timeInMillis = System.currentTimeMillis();
        final String message = activity.getString(R.string.exporter_email_device_list_body);
        final String[] to = new String[0];
        final String subject = activity.getString(
                R.string.exporter_email_device_list_subject,
                TimeFormatter.getIsoDateTime(timeInMillis));

        final String filename = String.format(Locale.US, CSV_FILENAME_PREFIX, timeInMillis) + CSV_FILENAME_SUFFIX;
        final Uri uri = csvFileWriter.writeCsvFile(activity, filename, store.getDeviceList());

        if (uri == null) {
            Toast.makeText(activity, R.string.error_failed_to_create_csv_to_share, Toast.LENGTH_SHORT).show();
        } else {
            new Navigation(activity)
                    .shareFileViaEmail(uri, to, subject, message);
        }
    }
}
