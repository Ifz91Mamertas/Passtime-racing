package com.example.passtime_racing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class Options extends AppCompatActivity {
    Button backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        backbutton = (Button) findViewById(R.id.back);
        backbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), MainMenu.class);
                //intent.putExtra("Data", "Laikinas žaidimo meniu");
                startActivity(intent);
            }
        });
        TextView textView = findViewById(R.id.Option_text);
        String data = getIntent().getStringExtra("Data");
        textView.setText(data);
    }
}