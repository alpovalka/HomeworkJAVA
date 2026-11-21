package org.timeapi;

import java.time.Instant;
import java.time.Duration;

public record MaintenanceWindow(
  Instant start,
  Instant end
) {
  public MaintenanceWindow {
    if (start == null) {
      throw new IllegalArgumentException("Пустой start");
    }
    if (end == null) {
      throw new IllegalArgumentException("Пустой end");
    }
    if (end.isBefore(start)) {
      throw new IllegalArgumentException("Окончание не может быть раньше начала");
    }
  }

  public Duration duration() {
    return Duration.between(start, end);
  }
}