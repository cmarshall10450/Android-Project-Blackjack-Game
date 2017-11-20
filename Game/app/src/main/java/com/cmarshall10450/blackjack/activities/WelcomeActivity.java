package com.cmarshall10450.blackjack.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cmarshall10450.blackjack.R;

public class WelcomeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);
  }

  public void onPlayClick(View view) {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }
}
