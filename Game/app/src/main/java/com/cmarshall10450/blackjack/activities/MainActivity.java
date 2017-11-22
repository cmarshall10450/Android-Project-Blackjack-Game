package com.cmarshall10450.blackjack.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmarshall10450.blackjack.R;
import com.cmarshall10450.blackjack.cards.Card;
import com.cmarshall10450.blackjack.game.Dealer;
import com.cmarshall10450.blackjack.game.Game;
import com.cmarshall10450.blackjack.game.GameStatus;
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

    SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

    double playerCash = sharedPreferences.getFloat("cash", 0);
    player.setStartingCash(playerCash);
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

    // If the dealer starts with blackjack then the game is immediately lost
    if (game.playerHasBlackjack(dealer)) {
      showGameStatus(R.string.game_status_lost);
      isRunning = false;
      return;
    }

    // If the player starts with blackjack then the game is immediately won
    if (game.playerHasBlackjack(player)) {
      showGameStatus(R.string.game_status_won);
      payBet(bet * 2.5);
      isRunning = false;
      return;
    }

    update();
  }

  private void update() {
    showPlayerCards();
    showHandValue();
    showDealerCards();
    showPlayerCash();
    showBet();
  }

  private void showDealerCards() {
    dealerCardsList.removeAllViews();
    showCards(dealer, dealerCardsList, R.anim.slide_in_from_top);
  }

  private void showPlayerCards() {
    playerCardsList.removeAllViews();
    showCards(player, playerCardsList, R.anim.slide_in_from_bottom);
  }

  private void showCards(Player player, LinearLayout linearLayout, int animationId) {
    ArrayList<Card> cards = player.getHand();
    showCards(0, cards, linearLayout, animationId);
  }

  private void showCards(
    final int index,
    final ArrayList<Card> cards,
    final LinearLayout linearLayout,
    final int animationId
  ) {
    if (index >= cards.size()) {
      return;
    }

    final Card card = cards.get(index);

    String rank = card.getRankName();
    String suit = card.getSuitName();
    String cardImage = suit + rank;

    ImageView imageView = new ImageView(this);
    imageView.setBackgroundColor(getResources().getColor(R.color.white));

    LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(350, 500);
    layout.setMargins(8, 0, 8, 0);
    imageView.setLayoutParams(layout);

    Animation animation = AnimationUtils.loadAnimation(
      imageView.getContext(),
      animationId
    );

    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {
      }

      @Override
      public void onAnimationEnd(Animation animation) {
        int newIndex = index + 1;
        MainActivity.this.showCards(newIndex, cards, linearLayout, animationId);
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });

    animation.setDuration(500);

    imageView.startAnimation(animation);


    int resourceId = this.getResources().getIdentifier(
      cardImage.toLowerCase(),
      "drawable",
      this.getPackageName()
    );
    imageView.setImageResource(resourceId);
    linearLayout.addView(imageView);
  }

  private void showHandValue() {
    String value = Integer.toString(player.getTotalValueOfCards());
    handValue.setText(value);
  }

  public void onHitClick(View view) {
    if (isRunning) {
      int currentPos = player.getHand().size();

      game.giveCardToPlayer(player);

      if (game.playerHasBust()) {
        isRunning = false;
        showGameStatus(R.string.game_status_bust);
      }

      showCards(currentPos, player.getHand(), playerCardsList, R.anim.slide_in_from_bottom);
      showHandValue();
    }
  }

  public void onStandClick(View view) {
    int currentPos = dealer.getHand().size();

    if (isRunning) {
      while (dealer.getTotalValueOfCards() <= 16) {

        game.giveCardToPlayer(dealer);

        if (dealer.getTotalValueOfCards() > 21) {
          isRunning = false;
          showGameStatus(R.string.game_status_won);
        }

      }

      showCards(currentPos, dealer.getHand(), dealerCardsList, R.anim.slide_in_from_top);

      isRunning = false;
      compareHands();
    }
  }

  public void onReplayClick(View view) {
    player.reduceMoney(bet);
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
    showBet();
  }

  public void onReduceBet(View view) {
    bet -= BET_INCREMENT;
    player.increaseMoney(BET_INCREMENT);
    showBet();
  }

  private void compareHands() {
    GameStatus winner = game.compareHands();

    switch (winner) {
      case DEALER:
        showGameStatus(R.string.game_status_lost);
        break;
      case TIE:
        showGameStatus(R.string.game_status_tie);
        payBet(bet);
        break;
      case PLAYER:
        showGameStatus(R.string.game_status_won);
        payBet(bet * 2);
        break;
    }
  }

  private void payBet(double bet) {
    player.increaseMoney(bet);
    double cash = player.getMoney();
    cashAmount.setText(Double.toString(cash + bet));
  }

}
