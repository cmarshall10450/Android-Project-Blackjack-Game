package com.cmarshall10450.blackjack.cards;


public enum Suit {

  SPADES("S"),
  HEARTS("H"),
  CLUBS("C"),
  DIAMONDS("D");

  private String name;

  Suit(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
