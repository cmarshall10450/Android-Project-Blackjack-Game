package com.cmarshall10450.blackjack.game;

import com.cmarshall10450.blackjack.cards.Card;
import com.cmarshall10450.blackjack.cards.Rank;
import com.cmarshall10450.blackjack.cards.Suit;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

  Player player;
  Card card1;
  Card card2;

  @Before
  public void setUp() {
    player = new Player();
    card1 = new Card(Rank.TEN, Suit.SPADES);
    card2 = new Card(Rank.EIGHT, Suit.CLUBS);
  }

  @Test
  public void getMoney() {
    double expected = 1000;
    double actual = player.getMoney();

    assertEquals(expected, actual, 0.01);
  }

  @Test
  public void increaseMoney() {
    double expected = 1010;
    player.increaseMoney(10);

    double actual = player.getMoney();
    assertEquals(expected, actual, 0.01);
  }

  @Test
  public void reduceMoney() {
    double expected = 990;
    player.reduceMoney(10);

    double actual = player.getMoney();
    assertEquals(expected, actual, 0.01);
  }

  @Test
  public void takeCard() {
    player.takeCard(card1);

    Card playerCard = player.getHand().get(0);
    assertEquals(playerCard, card1);
  }

  @Test
  public void getTotalValueOfCards() {
    Card card1 = new Card(Rank.TEN, Suit.SPADES);
    Card card2 = new Card(Rank.EIGHT, Suit.CLUBS);

    player.takeCard(card1);
    player.takeCard(card2);

    int expected = 18;
    int actual = player.getTotalValueOfCards();

    assertEquals(expected, actual);
  }

  @Test
  public void clearHand() {
    Card card1 = new Card(Rank.TEN, Suit.SPADES);
    Card card2 = new Card(Rank.EIGHT, Suit.CLUBS);

    player.takeCard(card1);
    player.takeCard(card2);
    player.clearHand();

    assertEquals(0, player.getHand().size());
  }

  @Test
  public void getHand() {
    ArrayList<Card> cards = new ArrayList<>();
    cards.add(card1);
    cards.add(card2);

    player.takeCard(card1);
    player.takeCard(card2);

    ArrayList<Card> playerCards = player.getHand();

    assertArrayEquals(cards.toArray(), playerCards.toArray());
  }

}