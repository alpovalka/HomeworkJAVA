package org.Zadanie7;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import static org.junit.jupiter.api.Assertions.*;

class StudentRegistryTest {

  // ---------- findStudentsByGradeRange ----------

  @Test
  void gradeRangeIncludesBothBounds() {
    Map<Integer, Student> map = StudentRegistry.createHashRegistry();
    Student anya = new Student(1, "Аня", 5.0);
    Student boris = new Student(2, "Боря", 7.0);
    Student vera = new Student(3, "Вера", 9.0);
    Student gleb = new Student(4, "Глеб", 10.0);
    map.put(1, anya);
    map.put(2, boris);
    map.put(3, vera);
    map.put(4, gleb);

    List<Student> result = StudentRegistry.findStudentsByGradeRange(map, 5.0, 9.0);

    assertEquals(3, result.size());
    assertTrue(result.contains(anya));
    assertTrue(result.contains(boris));
    assertTrue(result.contains(vera));
  }

  @Test
  void gradeRangeOnEmptyMapReturnsEmptyList() {
    Map<Integer, Student> map = StudentRegistry.createHashRegistry();

    List<Student> result = StudentRegistry.findStudentsByGradeRange(map, 0.0, 10.0);

    assertTrue(result.isEmpty());
  }

  @Test
  void gradeRangeRejectsMinGreaterThanMax() {
    Map<Integer, Student> map = StudentRegistry.createHashRegistry();

    assertThrows(IllegalArgumentException.class,
      () -> StudentRegistry.findStudentsByGradeRange(map, 8.0, 3.0));
  }

  @Test
  void gradeRangeRejectsBoundsOutsideScale() {
    Map<Integer, Student> map = StudentRegistry.createHashRegistry();

    assertThrows(IllegalArgumentException.class,
      () -> StudentRegistry.findStudentsByGradeRange(map, -1.0, 5.0));
    assertThrows(IllegalArgumentException.class,
      () -> StudentRegistry.findStudentsByGradeRange(map, 5.0, 11.0));
  }

  // ---------- getTopNStudentsById ----------

  @Test
  void topNReturnsLargestIdsInDescendingOrder() {
    NavigableMap<Integer, Student> map = StudentRegistry.createDescendingRegistry();
    Student anya = new Student(1, "Аня", 5.0);
    Student boris = new Student(2, "Боря", 7.0);
    Student vera = new Student(3, "Вера", 9.0);
    map.put(1, anya);
    map.put(3, vera);
    map.put(2, boris);

    List<Student> result = StudentRegistry.getTopNStudentsById(map, 2);

    assertEquals(List.of(vera, boris), result);
  }

  @Test
  void topNGreaterThanSizeReturnsAll() {
    NavigableMap<Integer, Student> map = StudentRegistry.createDescendingRegistry();
    Student anya = new Student(1, "Аня", 5.0);
    Student boris = new Student(2, "Боря", 7.0);
    map.put(1, anya);
    map.put(2, boris);

    List<Student> result = StudentRegistry.getTopNStudentsById(map, 10);

    assertEquals(List.of(boris, anya), result);
  }

  @Test
  void topNWithZeroReturnsEmptyList() {
    NavigableMap<Integer, Student> map = StudentRegistry.createDescendingRegistry();
    map.put(1, new Student(1, "Аня", 5.0));

    List<Student> result = StudentRegistry.getTopNStudentsById(map, 0);

    assertTrue(result.isEmpty());
  }

  @Test
  void topNRejectsNegativeN() {
    NavigableMap<Integer, Student> map = StudentRegistry.createDescendingRegistry();

    assertThrows(IllegalArgumentException.class,
      () -> StudentRegistry.getTopNStudentsById(map, -1));
  }
}