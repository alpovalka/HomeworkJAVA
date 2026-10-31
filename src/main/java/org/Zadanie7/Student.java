package org.Zadanie7;

/**
 * Студент: неизменяемая модель.
 */
public record Student(int id, String name, double grade) {

  public Student {
    if (id <= 0) {
      throw new IllegalArgumentException("id должен быть > 0");
    }
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("name не должно быть пустым");
    }
    if (Double.isNaN(grade) || grade < 0.0 || grade > 10.0) {
      throw new IllegalArgumentException("grade должна быть от 0.0 до 10.0");
    }
  }
}