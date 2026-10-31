package org.Zadanie7;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListTest {

  @Test
  void newListIsEmpty() {
    CustomList<Integer> list = new CustomArrayList<>();
    assertTrue(list.isEmpty());
    assertEquals(0, list.size());
  }

  @Test
  void addIncreasesSize() {
    CustomList<Integer> list = new CustomArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    assertEquals(3, list.size());
    assertFalse(list.isEmpty());
  }

  @Test
  void addNullThrows() {
    CustomList<Integer> list = new CustomArrayList<>();
    assertThrows(NullPointerException.class, () -> list.add(null));
  }

  @Test
  void getReturnsFirstMiddleAndLast() {
    CustomList<Integer> list = new CustomArrayList<>();
    for (int i = 0; i < 5; i++) {
      list.add(i * 10);
    }
    assertEquals(0, list.get(0));
    assertEquals(20, list.get(2));
    assertEquals(40, list.get(4));
  }

  @Test
  void addMoreThanInitialCapacityKeepsData() {
    CustomList<Integer> list = new CustomArrayList<>();
    for (int i = 0; i < 25; i++) {
      list.add(i * 10);
    }
    assertEquals(25, list.size());
    for (int i = 0; i < 25; i++) {
      assertEquals(i * 10, list.get(i));
    }
  }

  @Test
  void addWorksWithZeroCapacity() {
    CustomList<String> list = new CustomArrayList<>(0);
    list.add("a");
    list.add("b");
    assertEquals(2, list.size());
    assertEquals("a", list.get(0));
    assertEquals("b", list.get(1));
  }

  @Test
  void constructorRejectsNegativeCapacity() {
    assertThrows(IllegalArgumentException.class, () -> new CustomArrayList<>(-5));
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, -100, 3, 4, 100})
  void getRejectsInvalidIndex(int index) {
    CustomList<Integer> list = new CustomArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(index));
  }

  private CustomList<Integer> listOf(int... values) {
    CustomList<Integer> list = new CustomArrayList<>();
    for (int v : values) {
      list.add(v);
    }
    return list;
  }

  @Test
  void removeFirstReturnsElementAndShiftsTail() {
    CustomList<Integer> list = listOf(10, 20, 30, 40, 50);

    assertEquals(10, list.remove(0));

    assertEquals(4, list.size());
    assertEquals(20, list.get(0));
    assertEquals(30, list.get(1));
    assertEquals(40, list.get(2));
    assertEquals(50, list.get(3));
  }

  @Test
  void removeMiddleReturnsElementAndShiftsTail() {
    CustomList<Integer> list = listOf(10, 20, 30, 40, 50);

    assertEquals(30, list.remove(2));

    assertEquals(4, list.size());
    assertEquals(10, list.get(0));
    assertEquals(20, list.get(1));
    assertEquals(40, list.get(2));
    assertEquals(50, list.get(3));
  }

  @Test
  void removeLastReturnsElement() {
    CustomList<Integer> list = listOf(10, 20, 30, 40, 50);

    assertEquals(50, list.remove(4));

    assertEquals(4, list.size());
    assertEquals(40, list.get(3));
  }

  @Test
  void removeOnlyElementLeavesEmptyList() {
    CustomList<Integer> list = listOf(7);

    assertEquals(7, list.remove(0));

    assertTrue(list.isEmpty());
    assertEquals(0, list.size());
  }

  @Test
  void removedLastIndexIsNoLongerAccessible() {
    CustomList<Integer> list = listOf(10, 20, 30);

    list.remove(0);

    assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
  }

  @Test
  void removeThenAddKeepsOrder() {
    CustomList<Integer> list = listOf(10, 20, 30);

    list.remove(1);
    list.add(40);

    assertEquals(3, list.size());
    assertEquals(10, list.get(0));
    assertEquals(30, list.get(1));
    assertEquals(40, list.get(2));
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, -100, 3, 4, 100})
  void removeRejectsInvalidIndex(int index) {
    CustomList<Integer> list = listOf(1, 2, 3);

    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(index));
    assertEquals(3, list.size());
  }

  @Test
  void iteratorWalksInListOrder() {
    CustomList<Integer> list = listOf(10, 20, 30);
    List<Integer> collected = new ArrayList<>();

    Iterator<Integer> it = list.iterator();
    while (it.hasNext()) {
      collected.add(it.next());
    }

    assertEquals(List.of(10, 20, 30), collected);
  }

  @Test
  void forEachWorksOnCustomList() {
    CustomList<Integer> list = listOf(1, 2, 3);
    int sum = 0;

    for (Integer x : list) {
      sum += x;
    }

    assertEquals(6, sum);
  }

  @Test
  void iteratorOnEmptyListHasNoNext() {
    CustomList<Integer> list = new CustomArrayList<>();
    assertFalse(list.iterator().hasNext());
  }

  @Test
  void nextOnFinishedIteratorThrows() {
    CustomList<Integer> list = listOf(1);
    Iterator<Integer> it = list.iterator();
    it.next();

    assertThrows(NoSuchElementException.class, it::next);
  }
}
