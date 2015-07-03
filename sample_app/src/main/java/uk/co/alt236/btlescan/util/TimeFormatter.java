package uk.co.alt236.btlescan.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeFormatter {
    private final static String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS zzz";
    private final static SimpleDateFormat ISO_FORMATTER = new UtcDateFormatter(ISO_FORMAT, Locale.US);

    public static String getIsoDateTime(final Date date) {
        return ISO_FORMATTER.format(date);
    }

    public static String getIsoDateTime(final long millis) {
        return getIsoDateTime(new Date(millis));
    }
}
