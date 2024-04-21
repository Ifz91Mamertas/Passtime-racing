package com.example.passtime_racing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Stats extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    private static final String PREFS_KEY = "money_value";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        SharedPreferences prefs = getSharedPreferences("Money", MODE_PRIVATE);
        double money = Double.parseDouble(prefs.getString(PREFS_KEY, "0.0"));
        SharedPreferences prefs2 = getSharedPreferences("MPS", MODE_PRIVATE);
        double mps = Double.parseDouble(prefs2.getString(PREFS_KEY, "0.0"));
        SharedPreferences prefs3 = getSharedPreferences("Upgrade1", MODE_PRIVATE);
        double upgrade1_count = Double.parseDouble(prefs3.getString(PREFS_KEY, "0.0"));
        SharedPreferences prefs4 = getSharedPreferences("Upgrade2", MODE_PRIVATE);
        double upgrade2_count = Double.parseDouble(prefs4.getString(PREFS_KEY, "0.0"));
        SharedPreferences prefs5 = getSharedPreferences("Upgrade3", MODE_PRIVATE);
        double upgrade3_count = Double.parseDouble(prefs5.getString(PREFS_KEY, "0.0"));
        SharedPreferences prefs6 = getSharedPreferences("Upgrade4", MODE_PRIVATE);
        double upgrade4_count = Double.parseDouble(prefs6.getString(PREFS_KEY, "0.0"));

        TextView moneyTextView = findViewById(R.id.money);
        TextView mpsTextView = findViewById(R.id.mps);
        TextView u1TextView = findViewById(R.id.u1);
        TextView u2TextView = findViewById(R.id.u2);
        TextView u3TextView = findViewById(R.id.u3);
        TextView u4TextView = findViewById(R.id.u4);

        moneyTextView.setText(String.format("%.0f", money));
        mpsTextView.setText(String.format("%.1f",mps));
        u1TextView.setText(String.format("%.0f", upgrade1_count));
        u2TextView.setText(String.format("%.0f",upgrade2_count));
        u3TextView.setText(String.format("%.0f",upgrade3_count));
        u4TextView.setText(String.format("%.0f",upgrade4_count));

        ///==============Drawer settings=====================
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.subtext);
        navUsername.setText("Stats");
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

                return false;
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