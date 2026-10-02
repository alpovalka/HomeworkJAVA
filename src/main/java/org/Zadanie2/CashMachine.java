package org.Zadanie2;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CashMachine implements DepositOperations, WithdrawalOperations {
  @Override
  public BigDecimal deposit(BigDecimal balance, BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      return balance;
    }

    if (balance == null) {
      System.out.println("Ошибка: баланс не может быть null");
      return BigDecimal.ZERO;
    }

    return balance.add(amount).setScale(2, RoundingMode.HALF_UP);
  }


  @Override
  public BigDecimal withdraw(BigDecimal balance, BigDecimal amount, BankType bankType) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      return balance;
    }
    if (balance == null) {
      System.out.println("Ошибка: баланс не может быть null");
      return BigDecimal.ZERO;
    }
    BigDecimal commission = applyCommission(amount, bankType);
    BigDecimal cashOut = commission.add(amount);
    if (balance.compareTo(cashOut) < 0) {
      System.out.println("не хватает денег для снятия");
      return balance;
    } else {
      return balance.subtract(cashOut).setScale(2, RoundingMode.HALF_UP);
    }
  }
}
