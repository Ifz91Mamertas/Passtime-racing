package com.example.passtime_racing;

import androidx.appcompat.app.AppCompatActivity;
import android.media.AudioManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.SeekBar;

public class Options extends AppCompatActivity {
    Button backbutton;
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        backbutton = (Button) findViewById(R.id.back);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
               int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
               int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
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

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(maxVolume);
        seekBar.setProgress(currentVolume);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

           // @Override
            //public void onCheckedChanged(RadioGroup group, int checkedId)
            //{
            //}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        }
        );
       // TextView textView1 = findViewById(R.id.textView2_text);
        //String data = getIntent().getStringExtra("Data");
        //textView1.setText(data);
    }
}