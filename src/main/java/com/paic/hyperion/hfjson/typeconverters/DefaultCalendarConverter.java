package com.paic.hyperion.hfjson.typeconverters;

import java.text.DateFormat;

/** The default CalendarTypeConverter implementation. Attempts to parse ISO8601-formatted dates. */
public class DefaultCalendarConverter extends CalendarTypeConverter {

    private DateFormat mDateFormat;

    public DefaultCalendarConverter() {
        mDateFormat = new com.paic.hyperion.hfjson.typeconverters.DefaultDateFormatter();
    }

    public DateFormat getDateFormat() {
        return mDateFormat;
    }

}
