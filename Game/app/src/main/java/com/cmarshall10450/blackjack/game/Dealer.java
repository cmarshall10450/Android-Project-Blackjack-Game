package com.cmarshall10450.blackjack.game;

import com.cmarshall10450.blackjack.cards.Card;

public class Dealer extends Player {

  public Dealer() {
    super();
  }

  public Card getFirstCard() {
    return getHand().get(0);
  }
}
