package org.Zadanie2;

import java.util.Scanner;
import java.math.BigDecimal;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Добро пожаловать в банкомат!");
    System.out.print("Введите номер карты: ");
    int cardNumber;
    int pinCode;
    if (scanner.hasNextInt()) {
      cardNumber = scanner.nextInt();
      if (cardNumber < 10000 || cardNumber > 99999) {
        System.out.println("Ошибка: номер карты должен содержать 5 цифр");
        return;
      }
      System.out.println("Введите пин-код карты: ");
      if (scanner.hasNextInt()) {
        pinCode = scanner.nextInt();
        if (pinCode < 100 || pinCode > 999) {
          System.out.println("Ошибка:пин-код должен содержать 3 цифры");
          return;
        }
        Account account = new Account(12345, 999, new BigDecimal("10000.00"), BankType.AUM);
        if (cardNumber == account.getCardNumber() && pinCode == account.getPinCode()) {
          System.out.println("Авторизация успешна!");
        } else {
          System.out.println("Ошибка: неверный номер карты или PIN-код");
          return;
        }
        CashMachine cashMachine = new CashMachine();
        System.out.print("Введите сумму пополнения: ");

        if (scanner.hasNextBigDecimal()) {
          BigDecimal depositAmount = scanner.nextBigDecimal();
          BigDecimal newBalance = cashMachine.deposit(account.getBalance(), depositAmount);
          account.balance = newBalance;
          System.out.println("Новый баланс: " + newBalance);

        } else {
          System.out.println("Ошибка: некорректная сумма");
          return;
        }
        System.out.print("Введите сумму списания: ");

        if (scanner.hasNextBigDecimal()) {
          BigDecimal withdrawalAmount = scanner.nextBigDecimal();
          BigDecimal newBalance = cashMachine.withdraw(account.getBalance(), withdrawalAmount, account.getBankType());
          account.balance = newBalance;
          System.out.println("Новый баланс: " + newBalance);

        } else {
          System.out.println("Ошибка: некорректная сумма");
          return;
        }
      } else {
        System.out.println("Ошибка: некорректный пин-код");
        return;
      }
    } else {
      System.out.println("Ошибка: некорректный номер карты");
      return;
    }

  }

}
