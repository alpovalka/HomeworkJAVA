package org.Zadanie2;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account {
  public int cardNumber;
  public int pinCode;
  public BigDecimal balance;
  public BankType bankType;

  public Account(int cardNumber, int pinCode, BigDecimal balance, BankType bankType) {
    if (cardNumber < 10000 || cardNumber > 99999) {
      System.out.println("Ошибка: номер карты должен содержать 5 цифр");
    }
    this.cardNumber = cardNumber;

    if (pinCode < 100 || pinCode > 999) {
      System.out.println("Ошибка: PIN-код должен содержать 3 цифры");
    }
    this.pinCode = pinCode;
    if (balance == null) {
      System.out.println("Ошибка: баланс не может быть null");
    } else if (balance.compareTo(BigDecimal.ZERO) < 0) {
      System.out.println("Ошибка: баланс не может быть отрицательным");
    }
    this.balance = balance;
    if (bankType == null) {
      this.bankType = BankType.NEO;
    } else {
      this.bankType = bankType;
    }

  }

  public int getCardNumber() {
    return cardNumber;
  }

  public int getPinCode() {
    return pinCode;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BankType getBankType() {
    return bankType;
  }

  @Override
  public String toString() {if (balance == null) {
    return bankType.getRussianName() + " Карта: " + cardNumber + ", Баланс: null руб.";
  }
    return bankType.getRussianName() + " Карта: " + cardNumber + ", Баланс: " + balance.setScale(2, RoundingMode.HALF_UP) + " руб.";
  }
}
