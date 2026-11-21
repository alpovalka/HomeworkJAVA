package org.timeapi;

import java.time.*;
import java.time.zone.ZoneRules;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class MaintenanceScheduler {
  private final Clock clock;

  public MaintenanceScheduler(Clock clock) {
    this.clock = Objects.requireNonNull(clock, "Clock не может быть null");
  }

  public MaintenanceWindow nextWindow(
    LocalDate date,
    LocalTime localStart,
    Duration duration,
    ZoneId zone,
    OverlapPolicy policy
  ) {
    Objects.requireNonNull(date, "Дата не может быть null");
    Objects.requireNonNull(localStart, "Время начала не может быть null");
    Objects.requireNonNull(duration, "Длительность не может быть null");
    Objects.requireNonNull(zone, "Часовой пояс не может быть null");
    Objects.requireNonNull(policy, "Политика не может быть null");
    if (duration.isNegative() || duration.isZero()) {
      throw new IllegalArgumentException("Длительность должна быть положительной");
    }
    LocalDateTime localDateTime = LocalDateTime.of(date, localStart);
    ZoneRules rules = zone.getRules();

    if (rules.getValidOffsets(localDateTime).isEmpty()) {
      throw new DateTimeException("Указанное локальное время не существует в часовом поясе");
    }
    ZonedDateTime zonedDateTime = localDateTime.atZone(zone);

    if (policy == OverlapPolicy.EARLIER) {
      zonedDateTime = zonedDateTime.withEarlierOffsetAtOverlap();
    } else {
      zonedDateTime = zonedDateTime.withLaterOffsetAtOverlap();
    }
    Instant start = zonedDateTime.toInstant();
    Instant end = start.plus(duration);
    return new MaintenanceWindow(start, end);
  }

  public List<MaintenanceWindow> upcomingWindows(
    int days,
    LocalTime localStart,
    Duration duration,
    ZoneId zone,
    OverlapPolicy policy
  ) {
    Objects.requireNonNull(localStart, "Время начала не может быть null");
    Objects.requireNonNull(duration, "Длительность не может быть null");
    Objects.requireNonNull(zone, "Часовой пояс не может быть null");
    Objects.requireNonNull(policy, "Политика не может быть null");

    if (days < 0) {
      throw new IllegalArgumentException("Количество дней не может быть отрицательным");
    }

    if (duration.isNegative() || duration.isZero()) {
      throw new IllegalArgumentException("Длительность должна быть положительной");
    }

    if (days == 0) {
      return List.of();
    }
    LocalDate today = LocalDate.now(clock.withZone(zone));
    List<MaintenanceWindow> windows = new ArrayList<>();
    for (int i = 0; i < days; i++) {
      LocalDate date = today.plusDays(i);
      MaintenanceWindow window = nextWindow(date, localStart, duration, zone, policy);
      windows.add(window);
    }
    return windows;
  }

}