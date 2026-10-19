
package org.Zadanie6;

public class ArrayUtils {
  public static <T> int findFirst(T[] array, T element) {
    if (array == null) {
      return -1;
    }
    for (int i = 0; i < array.length; i++) {
      if (array[i] == null && element == null) {
        return i;
      }
      if (array[i] != null && array[i].equals(element)) {
        return i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    final String[] names = {"Alice", "Bob", "Charlie"};
    final int index = ArrayUtils.findFirst(names, "Bob");
    System.out.println(index);
    final String[] values = {"Hello", null, "World"};
    System.out.println(ArrayUtils.findFirst(values, null));
    System.out.println(ArrayUtils.findFirst(values, "World"));
    System.out.println(ArrayUtils.findFirst(values, "Java"));
    System.out.println(ArrayUtils.findFirst(null, "Java"));
    System.out.println(ArrayUtils.findFirst(values, null));
  }
}
