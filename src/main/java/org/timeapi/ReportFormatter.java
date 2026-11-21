package org.timeapi;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class ReportFormatter {

  public static String formatWindow(
    MaintenanceWindow window,
    ZoneId zone,
    Locale locale
  ) {
    Objects.requireNonNull(window, "Окно обслуживания не может быть null");
    Objects.requireNonNull(zone, "Часовой пояс не может быть null");
    Objects.requireNonNull(locale, "Локаль не может быть null");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm XXX '('VV')'", locale);
    ZonedDateTime start = window.start().atZone(zone);
    ZonedDateTime end = window.end().atZone(zone);
    return formatter.format(start)+" – "+formatter.format(end);
  }
}