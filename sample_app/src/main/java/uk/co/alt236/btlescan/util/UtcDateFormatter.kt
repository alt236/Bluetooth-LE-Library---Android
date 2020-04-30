package uk.co.alt236.btlescan.util

import android.annotation.SuppressLint
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

@Suppress("unused")
class UtcDateFormatter : SimpleDateFormat {

    @SuppressLint("SimpleDateFormat")
    constructor(template: String) : super(template) {
        super.setTimeZone(TIME_ZONE_UTC)
    }

    @SuppressLint("SimpleDateFormat")
    constructor(template: String, symbols: DateFormatSymbols) : super(template, symbols) {
        super.setTimeZone(TIME_ZONE_UTC)
    }

    constructor(template: String, locale: Locale) : super(template, locale) {
        super.setTimeZone(TIME_ZONE_UTC)
    }

    /*
     * This function will throw an UnsupportedOperationException.
     * You are not be able to change the TimeZone of this object
      *
      * (non-Javadoc)
     * @see java.text.DateFormat#setTimeZone(java.util.TimeZone)
     */
    override fun setTimeZone(timezone: TimeZone) {
        throw UnsupportedOperationException("This SimpleDateFormat can only be in $TIME_ZONE_STRING")
    }

    private companion object {
        private const val serialVersionUID = 1L
        private const val TIME_ZONE_STRING = "UTC"
        private val TIME_ZONE_UTC = TimeZone.getTimeZone(TIME_ZONE_STRING)
    }
}