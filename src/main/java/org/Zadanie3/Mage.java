package org.Zadanie3;

public class Mage extends Hero implements Castable, Restable {
  private final int maxMana;
  private int mana;

  public Mage(String name, int maxHealth, int baseAttack, int maxMana) {
    super(name, maxHealth, baseAttack);
    if (maxMana < 1) {
      maxMana = 1;
    }

    this.maxMana = maxMana;
    this.mana = maxMana;
  }

  public int getMana() {
    return mana;
  }

  public int getMaxMana() {
    return maxMana;
  }

  @Override
  public void attack(Hero target) {
    if (getMana() >= 10) {
      target.takeDamage(getBaseAttack() * 2);
      mana -= 10;
      System.out.println(getName() + " атакует магическим выстрелом " + target.getName());
    } else {
      target.takeDamage(getBaseAttack() / 2);
      mana = Math.min(mana + 5, maxMana);
      System.out.println(getName() + " атакует посохом " + target.getName());
    }
  }

  @Override
  public ActionType makeTurn(Hero target) {
    if (canCast()) {
      castSpecialSkill(target);
      return ActionType.SPECIAL_SKILL;
    } else if (needsRest()) {
      rest();
      return ActionType.REST;
    } else {
      attack(target);
      return ActionType.BASE_ATTACK;
    }
  }

  @Override
  public boolean canCast() {
    return mana >= 25;
  }

  @Override
  public void castSpecialSkill(Hero target) {
    if (canCast()) {
      int damage = getBaseAttack() * 3;
      target.takeDamage(damage);
      mana -= 25;
      System.out.println(getName() + " использует Огненную глыбу против " + target.getName());
    } else {
      System.out.println(getName() + " не может использовать Огненную глыбу: недостаточно маны!");
    }
  }

  @Override
  public boolean needsRest() {
    return mana == 0 || getHealth() < 0.3 * getMaxHealth();
  }

  @Override
  public void rest() {
    mana = maxMana;
    heal(getBaseAttack());
    System.out.println("Использовали исцеляющую медитацию");
  }
}
