package com.example.passtime_racing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        TextView textView = findViewById(R.id.Option_text);
        String data = getIntent().getStringExtra("Data");
        textView.setText(data);
    }
}