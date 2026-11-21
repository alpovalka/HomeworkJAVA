
package org.timeapi;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class IncidentAnalyticsTest {

  @Test
  void testTotalAndAverageDowntime() {
    Incident first = new Incident(
      "INC-1", "billing",
      Instant.parse("2024-03-30T10:00:00Z"),
      Instant.parse("2024-03-30T10:00:00.000000001Z")
    );

    Incident second = new Incident(
      "INC-2", "gateway",
      Instant.parse("2024-03-30T11:00:00Z"),
      Instant.parse("2024-03-30T11:00:00.000000002Z")
    );

    List<Incident> incidents = List.of(first, second);

    assertEquals(
      Duration.ofNanos(3),
      IncidentAnalytics.totalDowntime(incidents)
    );

    assertEquals(
      Duration.ofNanos(1),
      IncidentAnalytics.averageDowntime(incidents)
    );
  }

  @Test
  void testLongestIncident() {
    Incident first = new Incident(
      "INC-2", "billing",
      Instant.parse("2024-03-30T10:00:00Z"),
      Instant.parse("2024-03-30T11:00:00Z")
    );

    Incident second = new Incident(
      "INC-1", "gateway",
      Instant.parse("2024-03-30T10:00:00Z"),
      Instant.parse("2024-03-30T11:00:00Z")
    );

    assertEquals(
      second,
      IncidentAnalytics.longestIncident(
        List.of(first, second)
      ).orElseThrow()
    );
  }

  @Test
  void testEmptyList() {
    List<Incident> empty = List.of();

    assertEquals(Duration.ZERO,
      IncidentAnalytics.totalDowntime(empty));

    assertEquals(Duration.ZERO,
      IncidentAnalytics.averageDowntime(empty));

    assertTrue(
      IncidentAnalytics.longestIncident(empty).isEmpty()
    );
  }

  @Test
  void testGroupByStartDate() {
    Incident first = new Incident(
      "INC-1", "billing",
      Instant.parse("2024-03-30T23:30:00Z"),
      Instant.parse("2024-03-31T00:00:00Z")
    );

    Incident second = new Incident(
      "INC-2", "gateway",
      Instant.parse("2024-03-30T12:00:00Z"),
      Instant.parse("2024-03-30T13:00:00Z")
    );

    Map<LocalDate, List<Incident>> groups =
      IncidentAnalytics.groupByStartDate(
        List.of(first, second),
        ZoneId.of("Europe/Berlin")
      );

    assertEquals(
      List.of(
        LocalDate.of(2024, 3, 30),
        LocalDate.of(2024, 3, 31)
      ),
      List.copyOf(groups.keySet())
    );

    assertEquals(
      List.of(second),
      groups.get(LocalDate.of(2024, 3, 30))
    );

    assertEquals(
      List.of(first),
      groups.get(LocalDate.of(2024, 3, 31))
    );
  }
}
