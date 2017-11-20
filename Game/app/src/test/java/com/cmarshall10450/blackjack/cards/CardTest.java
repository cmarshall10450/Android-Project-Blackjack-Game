package com.cmarshall10450.blackjack.cards;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {

  Card card;

  @Before
  public void setUp() {
    card = new Card(Rank.ACE, Suit.SPADES);
  }

  @Test
  public void getRank() {
    Rank expected = Rank.ACE;
    Rank actual = card.getRank();

    assertEquals(expected, actual);
  }

  @Test
  public void getSuit() {
    Suit expected = Suit.SPADES;
    Suit actual = card.getSuit();

    assertEquals(expected, actual);
  }

  @Test
  public void getRankValue() {
    int expected = 11;
    int actual = card.getRankValue();

    assertEquals(expected, actual);
  }

  @Test
  public void getRankName() {
    String expected = "A";
    String actual = card.getRankName();

    assertEquals(expected, actual);
  }

  @Test
  public void getSuitName() {
    String expected = "S";
    String actual = card.getSuitName();

    assertEquals(expected, actual);
  }

}