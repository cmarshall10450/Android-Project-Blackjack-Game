package com.cmarshall10450.blackjack.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cmarshall10450.blackjack.R;

public class RulesActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rules);
  }

  public void onBackClick(View view) {
    finish();
  }
}
