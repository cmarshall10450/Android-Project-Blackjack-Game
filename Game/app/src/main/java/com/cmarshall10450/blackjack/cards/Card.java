package com.cmarshall10450.blackjack.cards;


public class Card {

  Rank rank;
  Suit suit;

  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  public Rank getRank() {
    return rank;
  }

  public Suit getSuit() {
    return suit;
  }

  public int getRankValue() {
    return rank.getValue();
  }

  public String getRankName() {
    return rank.getName();
  }

  public String getSuitName() {
    return suit.getName();
  }
}
