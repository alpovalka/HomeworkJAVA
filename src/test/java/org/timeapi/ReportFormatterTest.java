
package org.timeapi;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportFormatterTest {

  @Test
  void testFormatWindow() {
    MaintenanceWindow window = new MaintenanceWindow(
      Instant.parse("2024-03-30T10:00:00Z"),
      Instant.parse("2024-03-30T11:30:00Z")
    );

    String result = ReportFormatter.formatWindow(
      window,
      ZoneId.of("Europe/Berlin"),
      Locale.GERMANY
    );

    assertEquals(
      "30.03.2024 11:00 +01:00 (Europe/Berlin) – " +
        "30.03.2024 12:30 +01:00 (Europe/Berlin)",
      result
    );
  }
}
