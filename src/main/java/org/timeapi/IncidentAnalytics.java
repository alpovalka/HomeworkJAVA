package org.timeapi;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class IncidentAnalytics {
  public static Duration totalDowntime(List<Incident> incidents) {
    if (incidents == null) {
      throw new IllegalArgumentException("Список не может быть null");
    }

    Duration total = Duration.ZERO;

    for (Incident incident : incidents) {
      if (incident == null) {
        throw new IllegalArgumentException("Инцидент не может быть null");
      }

      total = total.plus(incident.duration());
    }

    return total;
  }

  public static Duration averageDowntime(List<Incident> incidents) {
    if (incidents == null) {
      throw new IllegalArgumentException("Список не может быть null");
    }

    if (incidents.isEmpty()) {
      return Duration.ZERO;
    }

    Duration total = totalDowntime(incidents);

    return total.dividedBy(incidents.size());

  }

  public static Optional<Incident> longestIncident(List<Incident> incidents) {
    if (incidents == null) {
      throw new IllegalArgumentException("Список не может быть null");
    }

    if (incidents.isEmpty()) {
      return Optional.empty();
    }

    for (Incident incident : incidents) {
      if (incident == null) {
        throw new IllegalArgumentException("Инцидент не может быть null");
      }
    }
    Incident longest = incidents.get(0);

    for (Incident incident : incidents) {
      if (incident.duration().compareTo(longest.duration()) > 0) {
        longest = incident;
      } else if (incident.duration().compareTo(longest.duration()) == 0) {
        if (incident.startedAt().isBefore(longest.startedAt())) {
          longest = incident;
        } else if (incident.startedAt().equals(longest.startedAt()) && incident.id().compareTo(longest.id()) < 0) {
          longest = incident;
        }
      }
    }

    return Optional.of(longest);
  }

  public static Map<LocalDate, List<Incident>> groupByStartDate(List<Incident> incidents, ZoneId zone) {

    if (incidents == null || zone == null) {
      throw new IllegalArgumentException("Аргументы не могут быть null");
    }

    Map<LocalDate, List<Incident>> result = new TreeMap<>();

    for (Incident incident : incidents) {
      if (incident == null) {
        throw new IllegalArgumentException("Инцидент не может быть null");
      }
      LocalDate date = incident.startedAt().atZone(zone).toLocalDate();
      result.computeIfAbsent(date, key -> new ArrayList<>()).add(incident);
    }
    for (List<Incident> group : result.values()) {
      group.sort(Comparator.comparing(Incident::startedAt).thenComparing(Incident::id));
    }

    return result;
  }
}
