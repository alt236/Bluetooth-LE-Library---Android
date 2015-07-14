/**
 * ****************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * <p/>
 * GenieConnect Ltd. ("COMPANY") CONFIDENTIAL
 * Unpublished Copyright (c) 2010-2013 GenieConnect Ltd., All Rights Reserved.
 * <p/>
 * NOTICE:
 * All information contained herein is, and remains the property of COMPANY.
 * The intellectual and technical concepts contained herein are proprietary to
 * COMPANY and may be covered by U.S. and Foreign Patents, patents in process, and
 * are protected by trade secret or copyright law. Dissemination of this
 * information or reproduction of this material is strictly forbidden unless prior
 * written permission is obtained from COMPANY.  Access to the source code
 * contained herein is hereby forbidden to anyone except current COMPANY employees,
 * managers or contractors who have executed  Confidentiality and Non-disclosure
 * agreements explicitly covering such access.
 * <p/>
 * The copyright notice above does not evidence any actual or intended publication
 * or disclosure  of  this source code, which includes information that is
 * confidential and/or proprietary, and is a trade secret, of  COMPANY.
 * <p/>
 * ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC  PERFORMANCE, OR PUBLIC
 * DISPLAY OF OR THROUGH USE  OF THIS  SOURCE CODE  WITHOUT  THE EXPRESS WRITTEN
 * CONSENT OF COMPANY IS STRICTLY PROHIBITED, AND IN VIOLATION OF APPLICABLE LAWS
 * AND INTERNATIONAL TREATIES. THE RECEIPT OR POSSESSION OF  THIS SOURCE CODE
 * AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS TO REPRODUCE,
 * DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE, USE, OR SELL ANYTHING
 * THAT IT  MAY DESCRIBE, IN WHOLE OR IN PART.
 * ****************************************************************************
 */
package uk.co.alt236.btlescan.util;

import android.annotation.SuppressLint;

import java.text.DateFormatSymbols;
import java.util.Locale;
import java.util.TimeZone;

public class UtcDateFormatter extends java.text.SimpleDateFormat {
    private static final long serialVersionUID = 1L;

    private static final String TIME_ZONE_STRING = "UTC";
    private static final TimeZone TIME_ZONE_UTC = TimeZone.getTimeZone(TIME_ZONE_STRING);

    @SuppressLint("SimpleDateFormat")
    public UtcDateFormatter(final String template) {
        super(template);
        super.setTimeZone(TIME_ZONE_UTC);
    }

    @SuppressLint("SimpleDateFormat")
    public UtcDateFormatter(final String template, final DateFormatSymbols symbols) {
        super(template, symbols);
        super.setTimeZone(TIME_ZONE_UTC);
    }

    public UtcDateFormatter(final String template, final Locale locale) {
        super(template, locale);
        super.setTimeZone(TIME_ZONE_UTC);
    }

    /*
     * This function will throw an UnsupportedOperationException.
     * You are not be able to change the TimeZone of this object
      *
      * (non-Javadoc)
     * @see java.text.DateFormat#setTimeZone(java.util.TimeZone)
     */
    @Override
    public void setTimeZone(final TimeZone timezone) {
        throw new UnsupportedOperationException("This SimpleDateFormat can only be in " + TIME_ZONE_STRING);
    }
}
