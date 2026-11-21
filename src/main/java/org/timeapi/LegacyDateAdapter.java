package org.timeapi;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public final class LegacyDateAdapter {

  public static Instant toInstant(Date date) {
    Objects.requireNonNull(date, "Date не может быть null");
    return date.toInstant();
  }

  public static Date toDate(Instant instant) {
    Objects.requireNonNull(instant, "Instant не может быть null");
    return Date.from(instant);
  }

  public static ZonedDateTime toZonedDateTime(GregorianCalendar calendar) {
    Objects.requireNonNull(calendar, "Calendar не может быть null");
    return calendar.toZonedDateTime();
  }

  public static GregorianCalendar toGregorianCalendar(ZonedDateTime zonedDateTime) {
    Objects.requireNonNull(zonedDateTime, "zonedDateTime не может быть null");
    return GregorianCalendar.from(zonedDateTime);
  }
}