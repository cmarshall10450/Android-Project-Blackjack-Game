package com.cmarshall10450.blackjack.game;

import com.cmarshall10450.blackjack.cards.Card;
import com.cmarshall10450.blackjack.cards.Rank;
import com.cmarshall10450.blackjack.cards.Suit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GameTest {

  Game game;
  Player player;
  Dealer dealer;

  @Before
  public void setUp() {
    game = new Game();
    player = game.getPlayer();
    dealer = game.getDealer();
  }

  @Test
  public void giveCardToPlayer() {
    Card card = new Card(Rank.ACE, Suit.SPADES);
    game.giveCardToPlayer(player, card);

    Card actual = player.getHand().get(0);

    assertEquals(card, actual);
  }

  @Test
  public void playerHasBlackjack() {
    Card card1 = new Card(Rank.ACE, Suit.SPADES);
    Card card2 = new Card(Rank.KING, Suit.DIAMONDS);

    game.giveCardToPlayer(player, card1);
    game.giveCardToPlayer(player, card2);

    assertTrue(game.playerHasBlackjack(player));
  }

  @Test
  public void playerHasBust() {
    Card card1 = new Card(Rank.ACE, Suit.SPADES);
    Card card2 = new Card(Rank.KING, Suit.DIAMONDS);
    Card card3 = new Card(Rank.KING, Suit.CLUBS);

    game.giveCardToPlayer(player, card1);
    game.giveCardToPlayer(player, card2);
    game.giveCardToPlayer(player, card3);

    assertTrue(game.playerHasBust());
  }

  @Test
  public void getDealer() {
    assertNotNull(dealer);
  }

  @Test
  public void getPlayer() {
    assertNotNull(player);
  }

}