package com.cmarshall10450.blackjack.cards;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeckTest {

  Deck deck;

  @Before
  public void setUp() {
    deck = new Deck();
  }

  @Test
  public void generateDeck() {
    int expected = 52;
    int actual = deck.getCards().size();
    assertEquals(expected, actual);
  }

  @Test
  public void removeCard() {
    Card card = deck.removeCard();
    assertEquals(Suit.SPADES, card.getSuit());
    assertEquals(Rank.TWO, card.getRank());
  }

}