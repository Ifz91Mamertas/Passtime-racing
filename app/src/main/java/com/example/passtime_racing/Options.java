package com.example.passtime_racing;

import androidx.appcompat.app.AppCompatActivity;
import android.media.AudioManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class Options extends AppCompatActivity {
    Button backbutton;
    AudioManager audioManager;
    ToggleButton togglebutton;
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
                startActivity(intent);
            }
        });
        ToggleButton toggleButton = findViewById(R.id.toggleVolume);

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

        int volume_level = audioManager.getStreamVolume(audioManager.STREAM_MUSIC);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!toggleButton.isChecked())
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0, 0);
                    seekBar.setProgress(0);
                    seekBar.setEnabled(false);
                }
                else
                {
                    seekBar.setProgress(volume_level);
                    seekBar.setEnabled(true);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volume_level, 0);
                }
            }
        });
    }
}