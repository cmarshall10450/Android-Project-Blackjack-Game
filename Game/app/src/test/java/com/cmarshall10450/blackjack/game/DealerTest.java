package com.cmarshall10450.blackjack.game;

import com.cmarshall10450.blackjack.cards.Card;
import com.cmarshall10450.blackjack.cards.Rank;
import com.cmarshall10450.blackjack.cards.Suit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DealerTest {

  Dealer dealer;
  Card card;

  @Before
  public void setUp() {
    dealer = new Dealer();
    card = new Card(Rank.ACE, Suit.SPADES);
  }

  @Test
  public void getFirstCard() {
    dealer.takeCard(card);
    Card expected = card;
    Card actual = dealer.getFirstCard();

    assertEquals(expected, actual);
  }

}