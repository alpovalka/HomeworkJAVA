package org.Zadanie7;

/**
 * Простой список с доступом по индексу.
 *
 * @param <E> тип элементов списка
 */
public interface CustomList<E> extends Iterable<E> {

  /**
   * Добавляет элемент в конец списка.
   *
   * @param element добавляемый элемент, не может быть {@code null}
   * @throws NullPointerException если {@code element} равен {@code null}
   */
  void add(E element);

  /**
   * Возвращает элемент по индексу.
   *
   * @param index индекс элемента, от 0 до {@code size() - 1}
   * @return элемент с указанным индексом
   * @throws IndexOutOfBoundsException если индекс вне диапазона
   */
  E get(int index);

  /**
   * Удаляет элемент по индексу. Элементы правее сдвигаются на одну позицию влево.
   *
   * @param index индекс удаляемого элемента, от 0 до {@code size() - 1}
   * @return удалённый элемент
   * @throws IndexOutOfBoundsException если индекс вне диапазона
   */
  E remove(int index);

  /**
   * Возвращает количество элементов в списке.
   *
   * @return размер списка
   */
  int size();

  /**
   * Проверяет, пуст ли список.
   *
   * @return {@code true}, если в списке нет элементов
   */
  boolean isEmpty();
}