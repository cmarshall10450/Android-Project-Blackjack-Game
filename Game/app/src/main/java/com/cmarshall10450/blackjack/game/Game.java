package com.cmarshall10450.blackjack.game;

import com.cmarshall10450.blackjack.cards.Card;
import com.cmarshall10450.blackjack.cards.Deck;

public class Game {
  private static final int BLACKJACK = 21;

  private Dealer dealer;
  private Player player;
  private Deck deck;

  public Game() {
    dealer = new Dealer();
    player = new Player();
  }

  public void run() {
    deck = new Deck();
    deck.shuffle();

    setupGame();
  }

  public void giveCardToPlayer(Player player) {
    Card card = deck.removeCard();
    player.takeCard(card);
  }

  public void giveCardToPlayer(Player player, Card card) {
    player.takeCard(card);
  }

  private void giveCardsToPlayers() {
    for (int i = 0; i < 2; i++) {
      giveCardToPlayer(player);
    }
    giveCardToPlayer(dealer);
  }

  private void setupGame() {
    // Remove the cards that the player and dealer currently have
    player.clearHand();
    dealer.clearHand();

    // Give the player and the dealer two cards each
    giveCardsToPlayers();
  }

  public boolean playerHasBlackjack(Player player) {
    return (player.getTotalValueOfCards() == BLACKJACK && player.getHand().size() == 2);
  }

  public boolean playerHasBust() {
    return (this.player.getTotalValueOfCards() > BLACKJACK);
  }

  public Dealer getDealer() {
    return dealer;
  }

  public Player getPlayer() {
    return player;
  }

  public GameStatus compareHands() {
    if (player.getTotalValueOfCards() > dealer.getTotalValueOfCards()) {
      return GameStatus.PLAYER;
    } else if (player.getTotalValueOfCards() < dealer.getTotalValueOfCards()) {
      return GameStatus.DEALER;
    } else {
      return GameStatus.TIE;
    }
  }
}
