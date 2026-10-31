package org.Zadanie7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Реестр студентов и выборки по нему.
 */
public class StudentRegistry {

  /**
   * Создаёт пустую карту студентов, где ключ это id студента.
   *
   * @return новая пустая HashMap
   */
  public static Map<Integer, Student> createHashRegistry() {
    return new HashMap<>();
  }

  /**
   * Создаёт пустую карту, в которой ключи отсортированы по убыванию.
   *
   * @return новая пустая TreeMap с обратным порядком ключей
   */
  public static NavigableMap<Integer, Student> createDescendingRegistry() {
    return new TreeMap<>(Comparator.reverseOrder());
  }

  /**
   * Возвращает студентов с оценкой от minGrade до maxGrade, обе границы включены.
   *
   * @param students карта студентов
   * @param minGrade нижняя граница оценки
   * @param maxGrade верхняя граница оценки
   * @return новый список подходящих студентов
   * @throws IllegalArgumentException если minGrade больше maxGrade
   *                                  или границы выходят за диапазон [0.0, 10.0]
   */
  public static List<Student> findStudentsByGradeRange(Map<Integer, Student> students, double minGrade, double maxGrade) {
    if (minGrade > maxGrade) {
      throw new IllegalArgumentException("minGrade больше maxGrade");
    }
    if (minGrade < 0.0 || maxGrade > 10.0) {
      throw new IllegalArgumentException("границы должны быть от 0.0 до 10.0");
    }

    List<Student> result = new ArrayList<>();
    for (Student student : students.values()) {
      if (student.grade() >= minGrade && student.grade() <= maxGrade) {
        result.add(student);
      }
    }
    return result;
  }

  /**
   * Возвращает первые n студентов из карты с ключами по убыванию,
   * то есть студентов с наибольшими id.
   *
   * @param students карта, созданная с сортировкой ключей по убыванию
   * @param n        сколько студентов взять
   * @return новый список, в нём не больше n студентов в порядке убывания id
   * @throws IllegalArgumentException если n отрицательное
   */
  public static List<Student> getTopNStudentsById(NavigableMap<Integer, Student> students, int n) {
    if (n < 0) {
      throw new IllegalArgumentException("n не может быть отрицательным");
    }

    List<Student> result = new ArrayList<>();
    for (Student student : students.values()) {
      if (result.size() == n) {
        break;
      }
      result.add(student);
    }
    return result;
  }
}