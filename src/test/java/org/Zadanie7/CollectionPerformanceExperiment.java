package org.Zadanie7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Сравнение ArrayList и LinkedList на одинаковых наборах операций.
 */
public class CollectionPerformanceExperiment {

  private static final int N = 10_000;
  private static long checksum;

  /**
   * Запускает все сценарии и печатает таблицу времени в наносекундах.
   *
   * @param args не используются
   */
  public static void main(String[] args) {
    String[] names = {
      "Добавление в конец",
      "Добавление в начало",
      "Вставка в середину",
      "Доступ по индексу",
      "Удаление из начала",
      "Удаление из конца"
    };

    System.out.printf("%-22s %10s %15s %15s%n",
      "Операция", "Элементов", "ArrayList, нс", "LinkedList, нс");

    for (int scenario = 0; scenario < names.length; scenario++) {
      long arrayTime = measure(false, scenario);
      long linkedTime = measure(true, scenario);
      System.out.printf("%-22s %10d %15d %15d%n", names[scenario], N, arrayTime, linkedTime);
    }

    System.out.println("Контрольная сумма: " + checksum);
  }

  /**
   * Делает 2 прогрева и 5 замеров, возвращает медиану.
   */
  private static long measure(boolean linked, int scenario) {
    long[] times = new long[5];

    for (int run = 0; run < 7; run++) {
      List<Integer> list;
      if (linked) {
        list = new LinkedList<>();
      } else {
        list = new ArrayList<>();
      }

      // подготовка: для сценариев 3, 4, 5 список должен быть уже заполнен
      if (scenario >= 3) {
        for (int i = 0; i < N; i++) {
          list.add(i);
        }
      }

      long start = System.nanoTime();
      checksum += doOperation(list, scenario);
      long time = System.nanoTime() - start;

      if (run >= 2) {
        times[run - 2] = time;
      }
    }

    Arrays.sort(times);
    return times[2];
  }

  /**
   * Выполняет одну операцию и возвращает число для контрольной суммы.
   */
  private static long doOperation(List<Integer> list, int scenario) {
    long sum = 0;

    if (scenario == 0) {
      for (int i = 0; i < N; i++) {
        list.add(i);
        sum += i;
      }
    } else if (scenario == 1) {
      for (int i = 0; i < N; i++) {
        list.add(0, i);
        sum += i;
      }
    } else if (scenario == 2) {
      for (int i = 0; i < N; i++) {
        list.add(list.size() / 2, i);
        sum += i;
      }
    } else if (scenario == 3) {
      for (int i = 0; i < list.size(); i++) {
        sum += list.get(i);
      }
    } else if (scenario == 4) {
      while (!list.isEmpty()) {
        sum += list.remove(0);
      }
    } else {
      while (!list.isEmpty()) {
        sum += list.remove(list.size() - 1);
      }
    }

    return sum;
  }
}