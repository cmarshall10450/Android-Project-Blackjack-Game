package com.cmarshall10450.blackjack.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

  private TextView handValue;
  private TextView gameStatus;
  private TextView betText;
  private TextView cashAmount;

  private LinearLayout playerCardsList;
  private LinearLayout dealerCardsList;

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

    playerCardsList = findViewById(R.id.player_cards_list);
    dealerCardsList = findViewById(R.id.dealer_cards_list);

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
    showDealerCards();
    showPlayerCash();
    showBet();
  }

  private void showDealerCards() {
    showCards(dealer, dealerCardsList);
  }

  private void showPlayerCards() {
    showCards(player, playerCardsList);
  }

  private void showCards(Player player, LinearLayout linearLayout) {
    ArrayList<Card> cards = player.getHand();

    linearLayout.removeAllViews();

    for (Card card : cards) {
      TextView tv = new TextView(this);
      tv.setTextSize(48);
      tv.setBackgroundColor(getResources().getColor(R.color.white));
      tv.setPadding(16, 32, 16, 32);


      String rank = card.getRankName();
      String suit = card.getSuitName();
      tv.setText(rank + suit);

      linearLayout.addView(tv);
    }
  }

  private void showHandValue() {
    String value = Integer.toString(player.getTotalValueOfCards());
    handValue.setText(value);
  }

  public void onHitClick(View view) {
    if (isRunning) {
      game.giveCardToPlayer(player);

      if (game.playerHasBust()) {
        isRunning = false;
        showGameStatus(R.string.game_status_bust);
      }

      update();
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
