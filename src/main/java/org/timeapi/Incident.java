package org.timeapi;
import java.time.Duration;
import java.time.Instant;

public record Incident(
  String id,
  String service,
  Instant startedAt,
  Instant resolvedAt
) {
  public Incident {
    if (id == null || id.isBlank()) {
      throw new IllegalArgumentException("Пустой id");
    }

    if (service == null || service.isBlank()) {
      throw new IllegalArgumentException("Пустой service");
    }
    if (startedAt == null) {
      throw new IllegalArgumentException("Пустой start");
    }
    if (resolvedAt == null) {
      throw new IllegalArgumentException("Пустой resolved");
    }

    if (resolvedAt.isBefore(startedAt)) {
      throw new IllegalArgumentException("Окончание инцидента не может быть раньше начала");
    }
  }

  public Duration duration() {
    return Duration.between(startedAt, resolvedAt);
  }


}