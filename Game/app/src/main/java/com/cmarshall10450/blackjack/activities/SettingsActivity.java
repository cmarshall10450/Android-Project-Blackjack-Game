package com.cmarshall10450.blackjack.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.cmarshall10450.blackjack.R;

public class SettingsActivity extends AppCompatActivity {

  EditText playerStartingCash;

  SharedPreferences sharedPreferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    playerStartingCash = findViewById(R.id.player_starting_cash_setting);

    sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
    double playerCash = sharedPreferences.getFloat("cash", 1000);

    playerStartingCash.setText(Double.toString(playerCash));
  }

  public void onSaveClick(View view) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    double cash = Double.parseDouble(playerStartingCash.getText().toString());

    editor.putFloat("cash", (float) cash);
    editor.apply();

    finish();
  }
}
