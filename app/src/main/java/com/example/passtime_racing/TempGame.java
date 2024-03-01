package com.example.passtime_racing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TempGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_game);

        TextView textView = findViewById(R.id.text);
        String data = getIntent().getStringExtra("Data");
        textView.setText(data);
    }
}