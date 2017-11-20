package com.cmarshall10450.blackjack.game;

import com.cmarshall10450.blackjack.cards.Card;

import java.util.ArrayList;

public class Player {

  private double money;
  private ArrayList<Card> hand;

  public Player() {
    this.money = 1000;
    hand = new ArrayList<>();
  }

  public double getMoney() {
    return money;
  }

  public void increaseMoney(double amount) {
    if (amount > 0) {
      money += amount;
    }
  }

  public void reduceMoney(double amount) {
    if (amount > 0) {
      money -= amount;
    }
  }

  public void takeCard(Card card) {
    hand.add(card);
  }

  public int getTotalValueOfCards() {
    int total = 0;
    for (Card card : hand) {
      total += card.getRankValue();
    }

    return total;
  }

  public void clearHand() {
    hand.clear();
  }

  public ArrayList<Card> getHand() {
    return hand;
  }

}