package org.example;

public enum ActionType {
  BASE_ATTACK("Базовая атака"), SPECIAL_SKILL("Особое умение"), REST("Отдых");

  private final String russianName;

  ActionType(String s) {
    this.russianName = s;
  }

  String getRussianName() {
    return russianName;
  }

}
