package com.cmarshall10450.blackjack.cards;


public enum Rank {

  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  TEN(10),
  JACK(10, "J"),
  QUEEN(10, "Q"),
  KING(10, "K"),
  ACE(11, "A");

  private int value;
  private String name;

  Rank(int value) {
    this.value = value;
    this.name = Integer.toString(value);
  }

  Rank(int value, String name) {
    this.value = value;
    this.name = name;
  }

  public int getValue() {
    return value;
  }

  public String getName() {
    return name;
  }
}
