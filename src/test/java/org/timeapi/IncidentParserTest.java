package org.timeapi;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IncidentParserTest {

  @Test
  void parseValidIncident() {
    String line = "INC-1;billing;2024-03-30T22:30:00Z;2024-03-31T01:00:00Z";

    Incident incident = IncidentParser.parse(line);

    assertEquals("INC-1", incident.id());
    assertEquals("billing", incident.service());
    assertEquals(Duration.ofMinutes(150), incident.duration());
  }

  @Test
  void parseEmptyId() {
    String line = ";billing;2024-03-30T22:30:00Z;2024-03-31T01:00:00Z";

    assertThrows(IllegalArgumentException.class, () -> {
      IncidentParser.parse(line);
    });
  }

  @Test
  void parseEndBeforeStart() {
    String line = "INC-1;billing;2024-03-31T01:00:00Z;2024-03-30T22:30:00Z";

    assertThrows(IllegalArgumentException.class, () -> {
      IncidentParser.parse(line);
    });
  }

  @Test
  void parseInvalidTimestamp() {
    String line = "INC-1;billing;not-a-date;2024-03-31T01:00:00Z";

    assertThrows(DateTimeParseException.class, () -> {
      IncidentParser.parse(line);
    });
  }

  @Test
  void parseWrongNumberOfFields() {
    String line = "INC-1;billing;2024-03-30T22:30:00Z";

    assertThrows(IllegalArgumentException.class, () -> {
      IncidentParser.parse(line);
    });
  }

}