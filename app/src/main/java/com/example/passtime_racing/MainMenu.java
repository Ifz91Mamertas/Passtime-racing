package com.example.passtime_racing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {
    Button clicker;
    double money = 0;
    TextView moneyText;
    Button optionbutton;
    Boolean isPlaying = false;
    private static final String PREFS_KEY = "money_value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.cutmefree);
        if(isPlaying == false)
        {
            startService(new Intent(this, BackgroundMusic.class));
            isPlaying = true;
        }
        moneyText = (TextView) findViewById(R.id.moneyCount);
        clicker = (Button) findViewById(R.id.clicker);
        optionbutton = (Button) findViewById(R.id.options);
        money = getSavedMoney();
        updateMoneyText();

        optionbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), Options.class);
                //intent.putExtra("Data", "Laikinas žaidimo meniu");
                startActivity(intent);
            }
        });

        clicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                money = money + 1;
                updateMoneyText();
                saveMoney();
            }
        });
    }

    private void updateMoneyText() {
        moneyText.setText("Money: " + String.format("%.0f", money));
    }

    // Method to save money value to SharedPreferences
    private void saveMoney() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(PREFS_KEY, (float) money);
        editor.apply();
    }

    private double getSavedMoney() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return prefs.getFloat(PREFS_KEY, 0);
    }
}