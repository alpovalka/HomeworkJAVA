
package org.timeapi;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LegacyDateAdapterTest {

  @Test
  void testRoundTrip() {
    Instant original = Instant.parse(
      "2024-03-30T10:15:30.123Z"
    );

    // Instant -> Date -> Instant
    Date date = LegacyDateAdapter.toDate(original);
    Instant restored = LegacyDateAdapter.toInstant(date);

    assertEquals(original, restored);

    // ZonedDateTime -> GregorianCalendar -> ZonedDateTime
    ZonedDateTime originalZoned = original.atZone(
      ZoneId.of("Europe/Berlin")
    );

    GregorianCalendar calendar =
      LegacyDateAdapter.toGregorianCalendar(originalZoned);

    ZonedDateTime restoredZoned =
      LegacyDateAdapter.toZonedDateTime(calendar);

    assertEquals(
      originalZoned.toInstant(),
      restoredZoned.toInstant()
    );

    assertEquals(
      originalZoned.getZone(),
      restoredZoned.getZone()
    );
  }
}
