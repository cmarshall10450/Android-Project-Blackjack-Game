package com.cmarshall10450.blackjack.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmarshall10450.blackjack.R;
import com.cmarshall10450.blackjack.cards.Card;
import com.cmarshall10450.blackjack.game.Dealer;
import com.cmarshall10450.blackjack.game.Game;
import com.cmarshall10450.blackjack.game.Player;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  public static final int BET_INCREMENT = 5;

  private int bet;
  private boolean isRunning;

  private ConstraintLayout screen;

  private TextView dealerCard;
  private TextView playerCard1;
  private TextView playerCard2;
  private TextView handValue;
  private TextView gameStatus;
  private TextView betText;
  private TextView cashAmount;

  private Button replayButton;

  private Dealer dealer;
  private Player player;
  private Game game;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    game = new Game();
    game.run();
    dealer = game.getDealer();
    player = game.getPlayer();

    bet = 0;
    isRunning = true;

    screen = findViewById(R.id.screen);

    dealerCard = findViewById(R.id.dealer_card);
    playerCard1 = findViewById(R.id.player_card_1);
    playerCard2 = findViewById(R.id.player_card_2);
    handValue = findViewById(R.id.hand_value);

    double playerCash = player.getMoney();
    cashAmount = findViewById(R.id.cash_amount);
    cashAmount.setText(Double.toString(playerCash));

    betText = findViewById(R.id.bet_text);
    betText.setText(Integer.toString(bet));

    gameStatus = findViewById(R.id.game_status);
    gameStatus.setVisibility(View.INVISIBLE);

    replayButton = findViewById(R.id.replay_btn);
    replayButton.setVisibility(View.INVISIBLE);

    update();

    // If the dealer starts with blackjack then the game is immediately lost
    if (game.playerHasBlackjack(dealer)) {
      showGameStatus(R.string.game_status_lost);
    }

    // If the player starts with blackjack then the game is immediately won
    if (game.playerHasBlackjack(player)) {
      showGameStatus(R.string.game_status_won);
      payBet(bet * 2.5);
    }
  }

  private void update() {
    showPlayerCards();
    showHandValue();
    showDealerCard();
    showPlayerCash();
    showBet();
  }

  private void showDealerCard() {
    String rank = dealer.getFirstCard().getRankName();
    String suit = dealer.getFirstCard().getSuitName();

    dealerCard.setText(rank + suit);
  }

  private void showPlayerCards() {
    ArrayList<Card> cards = player.getHand();

    String rank1 = cards.get(0).getRankName();
    String suit1 = cards.get(0).getSuitName();
    playerCard1.setText(rank1 + suit1);

    String rank2 = cards.get(1).getRankName();
    String suit2 = cards.get(1).getSuitName();
    playerCard2.setText(rank2 + suit2);

//    for (Card card : cards) {
//      TextView tv = new TextView(this);
//      String rank = card.getRankName();
//      String suit = card.getSuitName();
//      tv.setText(rank + suit);
//
//      screen.addView(tv);
//    }
  }

  private void showHandValue() {
    String value = Integer.toString(player.getTotalValueOfCards());
    handValue.setText(value);
  }

  public void onHitClick(View view) {
    if (isRunning) {
      game.giveCardToPlayer(player);

      update();

      if (game.playerHasBust()) {
        isRunning = false;
        showGameStatus(R.string.game_status_bust);
      }

      showPlayerCards();
    }
  }

  public void onStandClick(View view) {
    if (isRunning) {
      while (dealer.getTotalValueOfCards() <= 16) {
        game.giveCardToPlayer(dealer);
        update();
      }
      isRunning = false;
      compareHands();
    }
  }

  public void onReplayClick(View view) {
    hideGameStatus();
    payBet(0);
    game.run();
    update();
    isRunning = true;
  }

  private void showGameStatus(int stringId) {
    gameStatus.setText(stringId);
    gameStatus.setVisibility(View.VISIBLE);
    replayButton.setVisibility(View.VISIBLE);
  }

  private void hideGameStatus() {
    gameStatus.setVisibility(View.INVISIBLE);
    replayButton.setVisibility(View.INVISIBLE);
  }

  private void showPlayerCash() {
    double cash = player.getMoney();
    cashAmount.setText(Double.toString(cash));
  }

  private void showBet() {
    betText.setText(Integer.toString(bet));
  }

  public void onIncreaseBet(View view) {
    bet += BET_INCREMENT;
    player.reduceMoney(BET_INCREMENT);
    update();
  }

  public void onReduceBet(View view) {
    bet -= BET_INCREMENT;
    player.increaseMoney(BET_INCREMENT);
    update();
  }

  private void compareHands() {
    int winner = game.compareHands();

    switch (winner) {
      case Game.DEALER:
        showGameStatus(R.string.game_status_lost);
        break;
      case Game.TIE:
        showGameStatus(R.string.game_status_tie);
        payBet(bet);
        break;
      case Game.PLAYER:
        showGameStatus(R.string.game_status_won);
        payBet(bet * 2);
        break;
    }
  }

  private void payBet(double bet) {
    player.increaseMoney(bet);
    bet = 0;
    update();
  }

}
