package org.Zadanie3;

public class Arena {
  private final Hero fighter1;
  private final Hero fighter2;
  private int roundCounter;

  public Arena(Hero fighter1, Hero fighter2) {
    this.fighter1 = fighter1;
    this.fighter2 = fighter2;
    this.roundCounter = 0;
  }
  private void printFightersStatus() {
    if (fighter1 instanceof Warrior) {
      System.out.println("Воин: " + fighter1.getName() + ", здоровье: " + fighter1.getHealth() + ", реcурсы: " + ((Warrior) fighter1).getArmor());
    } else {
      System.out.println("Маг: " + fighter1.getName() + ", здоровье: " + fighter1.getHealth() + ", реcурсы: " + ((Mage) fighter1).getMana());
    }
    if (fighter2 instanceof Warrior) {
      System.out.println("Воин: " + fighter2.getName() + ", здоровье: " + fighter2.getHealth() + ", реcурсы: " + ((Warrior) fighter2).getArmor());
    } else {
      System.out.println("Маг: " + fighter2.getName() + ", здоровье: " + fighter2.getHealth() + ", реcурсы: " + ((Mage) fighter2).getMana());
    }
  }

  public void startTournament() {
    while (fighter2.isAlive() && fighter1.isAlive()) {
      roundCounter++;
      System.out.println("         Раунд " + roundCounter + "          ");
      Hero attacker;
      Hero defender;
      if (Math.random() < 0.5) {
        attacker = fighter1;
        defender = fighter2;
      } else {
        attacker = fighter2;
        defender = fighter1;
      }
      ActionType action = attacker.makeTurn(defender);
      System.out.println("Игрок " + attacker.getName() + " совершил действие: " + action.getRussianName());
      if (!defender.isAlive()) {
        printFightersStatus();
        break;
      }
      action = defender.makeTurn(attacker);
      System.out.println("Игрок " + defender.getName() + " совершил действие: " + action.getRussianName());
      if (!attacker.isAlive()) {
        printFightersStatus();
        break;
      }
      printFightersStatus();
    }
    if (fighter1.isAlive()) {
      System.out.println("Победитель: " + fighter1.getName());
    } else {
      System.out.println("Победитель: " + fighter2.getName());
    }
  }
}

