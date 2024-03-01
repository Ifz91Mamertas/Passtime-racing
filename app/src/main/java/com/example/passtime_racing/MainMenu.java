package com.example.passtime_racing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    Button playbutton;
    Button optionbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        playbutton = (Button) findViewById(R.id.play);
        playbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), TempGame.class);
                intent.putExtra("Data", "Laikinas žaidimo meniu");
                startActivity(intent);
            }
        });
        optionbutton = (Button) findViewById(R.id.options);
        optionbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), TempGame.class);
                intent.putExtra("Data", "Laikinas žaidimo meniu");
                startActivity(intent);
            }
        });
    }
}