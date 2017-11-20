package com.cmarshall10450.blackjack.cards;


import java.util.ArrayList;
import java.util.Collections;

public class Deck {

  private ArrayList<Card> cards;

  public Deck() {
    cards = new ArrayList<>();
    generateDeck();
  }

  public void generateDeck() {
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        Card card = new Card(rank, suit);
        cards.add(card);
      }
    }
  }

  public ArrayList<Card> getCards() {
    return new ArrayList<>(cards);
  }

  public void shuffle() {
    Collections.shuffle(cards);
  }

  public Card removeCard() {
    return cards.remove(0);
  }
}
