
package org.timeapi;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MaintenanceSchedulerTest {

  private final ZoneId berlin = ZoneId.of("Europe/Berlin");

  private final Clock clock = Clock.fixed(
    Instant.parse("2024-03-30T00:00:00Z"),
    ZoneOffset.UTC
  );

  private final MaintenanceScheduler scheduler =
    new MaintenanceScheduler(clock);

  @Test
  void testGap() {
    assertThrows(DateTimeException.class, () ->
      scheduler.nextWindow(
        LocalDate.of(2024, 3, 31),
        LocalTime.of(2, 30),
        Duration.ofMinutes(30),
        berlin,
        OverlapPolicy.EARLIER
      )
    );
  }

  @Test
  void testOverlap() {
    LocalDate date = LocalDate.of(2024, 10, 27);
    LocalTime time = LocalTime.of(2, 30);

    MaintenanceWindow earlier = scheduler.nextWindow(
      date, time, Duration.ofMinutes(30),
      berlin, OverlapPolicy.EARLIER
    );

    MaintenanceWindow later = scheduler.nextWindow(
      date, time, Duration.ofMinutes(30),
      berlin, OverlapPolicy.LATER
    );

    assertEquals(
      ZoneOffset.ofHours(2),
      earlier.start().atZone(berlin).getOffset()
    );

    assertEquals(
      ZoneOffset.ofHours(1),
      later.start().atZone(berlin).getOffset()
    );

    assertEquals(
      Duration.ofHours(1),
      Duration.between(earlier.start(), later.start())
    );
  }

  @Test
  void testUpcomingWindowsAcrossDST() {
    List<MaintenanceWindow> windows =
      scheduler.upcomingWindows(
        2,
        LocalTime.of(3, 30),
        Duration.ofMinutes(30),
        berlin,
        OverlapPolicy.EARLIER
      );

    assertEquals(2, windows.size());

    assertEquals(
      LocalTime.of(3, 30),
      windows.get(0).start().atZone(berlin).toLocalTime()
    );

    assertEquals(
      LocalTime.of(3, 30),
      windows.get(1).start().atZone(berlin).toLocalTime()
    );

    assertEquals(
      Duration.ofHours(23),
      Duration.between(
        windows.get(0).start(),
        windows.get(1).start()
      )
    );

    assertEquals(
      windows,
      scheduler.upcomingWindows(
        2,
        LocalTime.of(3, 30),
        Duration.ofMinutes(30),
        berlin,
        OverlapPolicy.EARLIER
      )
    );
  }

  @Test
  void testInvalidDuration() {
    assertThrows(IllegalArgumentException.class, () ->
      scheduler.nextWindow(
        LocalDate.of(2024, 3, 30),
        LocalTime.of(3, 30),
        Duration.ZERO,
        berlin,
        OverlapPolicy.EARLIER
      )
    );
  }

  @Test
  void testInvalidDays() {
    assertTrue(
      scheduler.upcomingWindows(
        0, LocalTime.of(3, 30),
        Duration.ofMinutes(30),
        berlin, OverlapPolicy.EARLIER
      ).isEmpty()
    );

    assertThrows(IllegalArgumentException.class, () ->
      scheduler.upcomingWindows(
        -1, LocalTime.of(3, 30),
        Duration.ofMinutes(30),
        berlin, OverlapPolicy.EARLIER
      )
    );
  }
}
