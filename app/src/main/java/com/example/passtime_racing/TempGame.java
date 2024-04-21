package com.example.passtime_racing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class TempGame extends AppCompatActivity {
    Button backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_game);
        SharedPreferences pref = getSharedPreferences("Upgrade1", MODE_PRIVATE);
        SharedPreferences pref1 = getSharedPreferences("Upgrade2", MODE_PRIVATE);
        SharedPreferences pref2 = getSharedPreferences("Upgrade3", MODE_PRIVATE);
        SharedPreferences pref3 = getSharedPreferences("Upgrade4", MODE_PRIVATE);
        SharedPreferences pref4 = getSharedPreferences("Money", MODE_PRIVATE);
        SharedPreferences pref5 = getSharedPreferences("MPS", MODE_PRIVATE);
        String defaultString="0";

        backbutton = (Button) findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), MainMenu.class);
                startActivity(intent);
            }
        });
        ///==============Receved data method=====================
        Intent intent = getIntent();
        String recevedMoney = pref4.getString("Money", defaultString);
        TextView textView = findViewById(R.id.moneyCount);
        if (recevedMoney != null){
            textView.setText("0");
        }
        else {
            textView.setText(recevedMoney);
        }
        String recevedMps = pref5.getString("MPS", defaultString);
        TextView textView2 = findViewById(R.id.mps);
        if (recevedMps != null){
            textView2.setText("0");
        }
        else {
            textView2.setText(recevedMps);
        }
        String reccevedUpgrade1_count = pref.getString("Upgrade1", defaultString);
        TextView textView3 = findViewById(R.id.upgrade1_count);
        if (reccevedUpgrade1_count != null){
            textView3.setText("0");
        }
        else {
            textView3.setText(reccevedUpgrade1_count);
        }
        String reccevedUpgrade2_count = pref1.getString("Upgrade2", defaultString);
        TextView textView4 = findViewById(R.id.upgrade2_count);
        if (reccevedUpgrade2_count != null){
            textView4.setText("");
        }
        else {
            textView4.setText(reccevedUpgrade2_count);
        }

        String reccevedUpgrade3_count = pref2.getString("Upgrade3", defaultString);
        TextView textView5 = findViewById(R.id.upgrade3_count);
        if (reccevedUpgrade3_count != null){
            textView5.setText("");
        }
        else {
            textView5.setText(reccevedUpgrade3_count);
        }
        String reccevedUpgrade4_count = pref3.getString("Upgrade4", defaultString);
        TextView textView6 = findViewById(R.id.upgrade4_count);
        if (reccevedUpgrade4_count != null){
            textView6.setText("0");
        }
        else {
            textView6.setText(reccevedUpgrade4_count);
        }


       // TextView textView1 = findViewById(R.id.Optiontext);





    }
}