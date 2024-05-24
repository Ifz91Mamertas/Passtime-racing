package com.example.passtime_racing;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class RaceMap extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Button race1;
    Button race2;
    Button race3;

    private static final String PREFS_KEY = "money_value";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_map);

        race1 = findViewById(R.id.race1);
        race2 = findViewById(R.id.race2);
        race3 = findViewById(R.id.race3);

        ///==============Drawer settings=====================
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.subtext);
        navUsername.setText("Race Map");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_settings)
                {
                    Intent intent = new Intent(getBaseContext(), Options.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.action_maingame)
                {
                    Intent intent = new Intent(getBaseContext(), MainMenu.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.action_project)
                {
                    Intent intent = new Intent(getBaseContext(), ProjectCar.class);
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

        race1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RaceActivity.class);
                intent.putExtra("Race", "Race1");
                startActivity(intent);
            }
        });

        race2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RaceActivity.class);
                intent.putExtra("Race", "Race2");
                startActivity(intent);
            }
        });

        race3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RaceActivity.class);
                intent.putExtra("Race", "Race3");
                startActivity(intent);
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