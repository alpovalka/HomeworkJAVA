package org.timeapi;
import java.time.Instant;

public class IncidentParser {
  public static Incident parse(String line) {
    if (line == null || line.isBlank()) {
      throw new IllegalArgumentException("Пустая строка");
    }

    String[] parts = line.split(";", -1);

    if (parts.length != 4) {
      throw new IllegalArgumentException("Неверный формат");
    }
    for (String part : parts) {
      if (part.isBlank()) {
        throw new IllegalArgumentException("Пустое обязательное поле");
      }
    }
    Instant start = Instant.parse(parts[2]);
    Instant end = Instant.parse(parts[3]);
    return new Incident(parts[0], parts[1], start, end);
  }
}