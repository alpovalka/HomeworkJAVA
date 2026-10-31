package org.Zadanie7;


import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Список на основе динамического массива.
 * Когда место заканчивается, массив увеличивается в 1.5 раза.
 * Значения {@code null} не допускаются.
 *
 * @param <E> тип элементов списка
 */
public class CustomArrayList<E> implements CustomList<E> {

  private Object[] data;
  private int size;

  /**
   * Создаёт пустой список с начальной ёмкостью 10.
   */
  public CustomArrayList() {
    data = new Object[10];
  }

  /**
   * Создаёт пустой список с заданной начальной ёмкостью.
   *
   * @param capacity начальная ёмкость, не может быть отрицательной
   * @throws IllegalArgumentException если {@code capacity} отрицательная
   */
  public CustomArrayList(int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException("ёмкость не может быть отрицательной: " + capacity);
    }
    data = new Object[capacity];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void add(E element) {
    if (element == null) {
      throw new NullPointerException("null нельзя");
    }
    if (size == data.length) {
      if (data.length > 1) {
        data = Arrays.copyOf(data, data.length / 2 + data.length);
      } else {
        data = Arrays.copyOf(data, size + 1);
      }
    }
    data[size] = element;
    size++;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E get(int index) {
    checkIndex(index);
    return (E) data[index];
  }

  private void checkIndex(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("индекс: " + index + ", размер: " + size);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E remove(int index) {
    checkIndex(index);
    E removeElement = (E) data[index];
    for (int i = index; i < size - 1; i++) {
      data[i] = data[i + 1];
    }
    data[size - 1] = null;
    size--;
    return removeElement;
  }

  /**
   * Возвращает итератор по элементам от первого к последнему.
   * Метод {@code remove()} у итератора не поддерживается.
   *
   * @return итератор по элементам списка
   */
  @Override
  public Iterator<E> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<E> {

    private int current = 0;

    @Override
    public boolean hasNext() {
      return current < size;
    }

    @Override
    public E next() {
      if (current >= size) {
        throw new NoSuchElementException();
      }
      E element = (E) data[current];
      current++;
      return element;
    }
  }
}