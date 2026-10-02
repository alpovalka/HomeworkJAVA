package org.Zadanie3;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Создайте два персонажа.");

    System.out.println("Для начала создадим первого. Укажите его тип (Воин - 1, Маг - 2)");
    int type1 = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Введите имя первого героя:");
    String name1 = scanner.nextLine();

    System.out.println("Введите максимальное здоровье первого героя:");
    int maxHealth1 = scanner.nextInt();

    System.out.println("Введите базовую атаку первого героя:");
    int baseAttack1 = scanner.nextInt();

    Hero fighter1;

    if (type1 == 1) {
      System.out.println("Введите броню:");
      int armor1 = scanner.nextInt();
      fighter1 = new Warrior(name1, maxHealth1, baseAttack1, armor1);
    } else {
      System.out.println("Введите максимальную ману:");
      int maxMana1 = scanner.nextInt();
      fighter1 = new Mage(name1, maxHealth1, baseAttack1, maxMana1);
    }

    System.out.println("\nТеперь создадим второго героя.");
    System.out.println("Укажите его тип (Воин - 1, Маг - 2)");
    int type2 = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Введите имя второго героя:");
    String name2 = scanner.nextLine();

    System.out.println("Введите максимальное здоровье:");
    int maxHealth2 = scanner.nextInt();

    System.out.println("Введите базовую атаку:");
    int baseAttack2 = scanner.nextInt();

    Hero fighter2;

    if (type2 == 1) {
      System.out.println("Введите броню:");
      int armor2 = scanner.nextInt();
      fighter2 = new Warrior(name2, maxHealth2, baseAttack2, armor2);
    } else {
      System.out.println("Введите максимальную ману:");
      int maxMana2 = scanner.nextInt();
      fighter2 = new Mage(name2, maxHealth2, baseAttack2, maxMana2);
    }

    Arena arena = new Arena(fighter1, fighter2);
    arena.startTournament();

    scanner.close();
  }
}
