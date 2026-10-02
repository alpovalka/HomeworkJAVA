package org.Zadanie3;

public class Warrior extends Hero implements Castable, Restable {
  private final int armor;

  public Warrior(String name, int maxHealth, int baseAttack, int armor) {
    super(name, maxHealth, baseAttack);
    if (armor < 1) {
      armor = 1;
    }
    this.armor = armor;
  }

  public int getArmor() {
    return armor;
  }

  @Override
  public void takeDamage(int damage) {
    if (damage < 0) {
      damage = 0;
    }
    int finalDamage = damage - armor;
    if (finalDamage < 1) {
      finalDamage = 1;
    }
    super.takeDamage(finalDamage);
  }

  @Override
  public void attack(Hero target) {
    target.takeDamage(getBaseAttack());
    System.out.println(getName() + " атакует " + target.getName());
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
    return getHealth() < 0.5 * getMaxHealth();
  }

  @Override
  public void castSpecialSkill(Hero target) {
    if (canCast()) {
      int damage = getBaseAttack() + 2 * getArmor();
      target.takeDamage(damage);
      System.out.println(getName() + " использует Удар щитом против " + target.getName());
    } else {
      System.out.println("Воин попытался ударить щитом, но потерял равновесие!");
    }
  }

  @Override
  public boolean needsRest() {
    return getHealth() < 0.15 * getMaxHealth();
  }

  @Override
  public void rest() {
    heal(armor * 2);
    System.out.println(getName() + " использует второе дыхание!");
  }
}
