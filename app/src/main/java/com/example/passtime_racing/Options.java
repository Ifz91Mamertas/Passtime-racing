package com.example.passtime_racing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.media.AudioManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.google.android.material.navigation.NavigationView;

public class Options extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        ///==============Drawer settings=====================
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.subtext);
        navUsername.setText("Options");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_maingame)
                {
                    Intent intent = new Intent(getBaseContext(), MainMenu.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.action_stats)
                {
                    Intent intent = new Intent(getBaseContext(), Stats.class);
                    startActivity(intent);
                }

                return false;
            }
        });
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
               int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
               int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        ToggleButton toggleButton = findViewById(R.id.toggleVolume);

        TextView textView = findViewById(R.id.Volumetoggle);
        String data = getIntent().getStringExtra("Data");


        TextView textView1 = findViewById(R.id.Optiontext);

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

    ///==============Extras for drawer=====================
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}