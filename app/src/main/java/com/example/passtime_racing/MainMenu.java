package com.example.passtime_racing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class MainMenu extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Button clicker;
    Button upgrade1;
    long upgrade1_count = 0;
    TextView upgrade1_text;
    long upgrade1_cost = 10;
    Button upgrade2;
    long upgrade2_count = 0;
    TextView upgrade2_text;
    long upgrade2_cost = 25;
    long money = 0;
    TextView moneyText;
    Button optionbutton;
    Boolean isPlaying = false;
    private static final String PREFS_KEY = "money_value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ///==============Music player=====================
        setContentView(R.layout.activity_main_menu);
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.cutmefree);

        if(isPlaying == false)
        {
            startService(new Intent(this, BackgroundMusic.class));
            isPlaying = true;
        }
        ///==============XML file finder=====================
        moneyText = (TextView) findViewById(R.id.moneyCount);
        clicker = (Button) findViewById(R.id.clicker);
        upgrade1 = (Button) findViewById(R.id.upgrade1);
        upgrade1_text = (TextView) findViewById(R.id.cost_u1);
        upgrade2 = (Button) findViewById(R.id.upgrade2);
        upgrade2_text = (TextView) findViewById(R.id.cost_u2);
        money = getSavedMoney();
        setUpgradeCount();
        updateMoneyText();

        checkUpgradeButtons();

        Toast.makeText(MainMenu.this, String.valueOf(upgrade2_cost), Toast.LENGTH_SHORT).show();

        ///==============Drawer settings=====================
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.subtext);
        navUsername.setText("Main office");
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_settings)
                {
                    Intent intent = new Intent(getBaseContext(), Options.class);
                    startActivity(intent);
                }

                return false;
            }
        });

        ///==============Click listeners=====================
        clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money + (1 + upgrade1_count);
                updateMoneyText();
                saveMoney();
                updateUpgradeText();
                checkUpgradeButtons();
            }
        });

        upgrade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgrade1_count = upgrade1_count + 1;
                money = money - upgrade1_cost;
                saveUpgrades();
                updateMoneyText();
                saveMoney();
                updateUpgradeText();
                checkUpgradeButtons();
            }
        });

        upgrade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgrade2_count = upgrade2_count + 1;
                money = money - upgrade2_cost;
                saveUpgrades();
                updateMoneyText();
                saveMoney();
                updateUpgradeText();
                checkUpgradeButtons();
                startMoneyUpdate();
            }
        });
    }

    private static final long DELAY_TIME_UPGRADE2 = 1500;
    private Handler handler = new Handler();

    ///==============Money update method=====================
    private void updateMoneyText() {
        moneyText.setText("Money: " + String.format("%d", money));
    }

    private void updateUpgradeText()
    {
        ///Updating upgrade 1
        double cost = upgrade1_count * 5 + 10;
        upgrade1_cost = (long)cost;
        upgrade1_text.setText("Cost: " + String.format("%d", upgrade1_cost));

        ///Updating upgrade 2
        cost = upgrade2_count * 2.5 + 25;
        upgrade2_cost = (long)cost;
        upgrade2_text.setText("Cost: " + String.format("%d", upgrade2_cost));
    }

    ///==============Save money between launches method=====================
    private void saveMoney() {
        SharedPreferences prefs = getSharedPreferences("Money", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(PREFS_KEY, (long) money);
        editor.apply();
    }

    ///==============Read saved money count method=====================
    private long getSavedMoney() {
        SharedPreferences prefs = getSharedPreferences("Money", MODE_PRIVATE);
        return prefs.getLong(PREFS_KEY, 0);
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
    ///==============ALL upgrade counts get set here=====================
    public void setUpgradeCount()
    {
        SharedPreferences prefs = getSharedPreferences("Upgrade1", MODE_PRIVATE);
        upgrade1_count = upgrade1_count + prefs.getLong(PREFS_KEY, 0);
        double costCount = upgrade1_count * 5 + 10;
        upgrade1_text.setText("Cost: " + String.format("%.0f", costCount));

        prefs = getSharedPreferences("Upgrade2", MODE_PRIVATE);
        upgrade2_count = upgrade2_count + prefs.getLong(PREFS_KEY, 0);
        costCount = upgrade2_count * 2.5 + 25;
        upgrade2_text.setText("Cost: " + String.format("%.0f", costCount));
    }
    ///==============ALL upgrade counts get saved here=====================
    public void saveUpgrades()
    {
        SharedPreferences prefs = getSharedPreferences("Upgrade1", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(PREFS_KEY, (long) upgrade1_count);

        prefs = getSharedPreferences("Upgrade2", MODE_PRIVATE);
        editor = prefs.edit();
        editor.putLong(PREFS_KEY, (long) upgrade2_count);

        editor.apply();
    }

    private Runnable moneyUpdater = new Runnable() {
        @Override
        public void run() {
            if (upgrade2_count >= 1) {
                money += upgrade2_count;
                updateMoneyText();
                checkUpgradeButtons();
            }
            handler.postDelayed(this, DELAY_TIME_UPGRADE2);
        }
    };

    private void startMoneyUpdate() {
        handler.postDelayed(moneyUpdater, DELAY_TIME_UPGRADE2);
    }

    private void stopMoneyUpdate() {
        handler.removeCallbacks(moneyUpdater);
    }

    private void checkUpgradeButtons() {
        if (money >= upgrade1_cost) {
            upgrade1.setEnabled(true);
        } else {
            upgrade1.setEnabled(false);
        }

        if (money >= upgrade2_cost) {
            upgrade2.setEnabled(true);
        } else {
            upgrade2.setEnabled(false);
        }
    }
}