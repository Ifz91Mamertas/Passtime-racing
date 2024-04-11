package com.example.passtime_racing;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    Button optionbutton;
    Boolean isPlaying = false;
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
        optionbutton = (Button) findViewById(R.id.options);
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
    }
}