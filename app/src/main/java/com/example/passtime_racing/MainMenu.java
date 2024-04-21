package com.example.passtime_racing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainMenu extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Button clicker;
    Button upgrade1;
    double upgrade1_count = 0.0;
    TextView upgrade1_text;
    double upgrade1_cost = 10.0;
    Button upgrade2;
    double upgrade2_count = 0.0;
    TextView upgrade2_text;
    double upgrade2_cost = 25;
    Button upgrade3;
    double upgrade3_count = 0.0;
    TextView upgrade3_text;
    double upgrade3_cost = 100;
    Button upgrade4;
    double upgrade4_count = 0.0;
    TextView upgrade4_text;
    double upgrade4_cost = 250;
    double money = 0.0;
    TextView moneyText;
    double mps = 0.0;
    TextView mpsText;
    Boolean isPlaying = false;
    boolean isUpgradeUpdating = false;
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
        mpsText = (TextView) findViewById(R.id.mps);
        clicker = (Button) findViewById(R.id.clicker);
        upgrade1 = (Button) findViewById(R.id.upgrade1);
        upgrade1.setEnabled(false);
        upgrade1_text = (TextView) findViewById(R.id.cost_u1);
        upgrade2 = (Button) findViewById(R.id.upgrade2);
        upgrade2.setEnabled(false);
        upgrade2_text = (TextView) findViewById(R.id.cost_u2);
        upgrade3 = (Button) findViewById(R.id.upgrade3);
        upgrade3.setEnabled(false);
        upgrade3_text = (TextView) findViewById(R.id.cost_u3);
        upgrade4 = (Button) findViewById(R.id.upgrade4);
        upgrade4.setEnabled(false);
        upgrade4_text = (TextView) findViewById(R.id.cost_u4);
        mps = getSavedMps();
        money = getSavedMoney();
        setUpgradeCount();
        updateMoneyText();


        if(!isUpgradeUpdating)
        {
            startMoneyUpdate();
        }

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
        checkUpgradeButtons();
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

                Toast.makeText(MainMenu.this, String.valueOf(upgrade1_count), Toast.LENGTH_SHORT).show();
            }
        });

        upgrade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mps = mps + 0.1;
                upgrade2_count = upgrade2_count + 1;
                money = money - upgrade2_cost;
                saveUpgrades();
                updateMoneyText();
                saveMoney();
                updateUpgradeText();
                checkUpgradeButtons();
                if(isUpgradeUpdating)
                {
                    stopMoneyUpdate();
                    startMoneyUpdate();
                }

                Toast.makeText(MainMenu.this, String.valueOf(upgrade2_count), Toast.LENGTH_SHORT).show();
            }
        });

        upgrade3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mps = mps + 1;
                upgrade3_count = upgrade3_count + 1;
                money = money - upgrade3_cost;
                saveUpgrades();
                updateMoneyText();
                saveMoney();
                updateUpgradeText();
                checkUpgradeButtons();
                if(isUpgradeUpdating)
                {
                    stopMoneyUpdate();
                    startMoneyUpdate();
                }

                Toast.makeText(MainMenu.this, String.valueOf(upgrade3_count), Toast.LENGTH_SHORT).show();
            }
        });

        upgrade4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mps = mps + 3;
                upgrade4_count = upgrade4_count + 1;
                money = money - upgrade4_cost;
                saveUpgrades();
                updateMoneyText();
                saveMoney();
                updateUpgradeText();
                checkUpgradeButtons();
                if(isUpgradeUpdating)
                {
                    stopMoneyUpdate();
                    startMoneyUpdate();
                }

                Toast.makeText(MainMenu.this, String.valueOf(upgrade4_count), Toast.LENGTH_SHORT).show();
            }
        });

        ///==============Send data method=====================
        Intent intent = new Intent(MainMenu.this, TempGame.class);
        intent.putExtra("money", money);
        intent.putExtra("mps", mps);
        intent.putExtra("upgrade1_count", upgrade1_count);
        intent.putExtra("upgrade2_count", upgrade2_count);
        intent.putExtra("upgrade3_count", upgrade3_count);
        intent.putExtra("upgrade4_count", upgrade4_count);
        startActivity(intent);
    }


    ///==============Variables for methods below=====================
    private static final long DELAY_TIME_UPGRADE = 100;
    private Handler handler = new Handler();

    ///==============Money update method=====================
    private void updateMoneyText() {
        moneyText.setText("Money: " + String.format("%.0f", money));
        mpsText.setText("Money Per Second: " + String.format("%.1f", mps));
        saveMoney();
    }

    private void updateUpgradeText()
    {
        ///Updating upgrade 1
        double cost = 10 * Math.pow(3, upgrade1_count);
        upgrade1_cost = cost;
        upgrade1_text.setText("Cost: " + String.format("%.0f", upgrade1_cost));

        ///Updating upgrade 2
        cost = 30 * Math.pow(1.1, upgrade2_count);
        upgrade2_cost = cost;
        upgrade2_text.setText("Cost: " + String.format("%.0f", upgrade2_cost));

        ///Updating upgrade 3
        cost = 100 * Math.pow(1.2, upgrade3_count);
        upgrade3_cost = cost;
        upgrade3_text.setText("Cost: " + String.format("%.0f", upgrade3_cost));

        ///Updating upgrade 4
        cost = 250 * Math.pow(1.3, upgrade4_count);
        upgrade4_cost = cost;
        upgrade4_text.setText("Cost: " + String.format("%.0f", upgrade4_cost));
    }

    ///==============Save money between launches method=====================
    private void saveMoney() {
        SharedPreferences prefs = getSharedPreferences("Money", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFS_KEY, String.valueOf(money));
        editor.apply();

        saveMps();
    }

    ///==============Save money between launches method=====================
    private void saveMps() {
        SharedPreferences prefs = getSharedPreferences("MPS", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFS_KEY, String.valueOf(mps));
        editor.apply();
    }

    ///==============Read saved money count method=====================
    private double getSavedMoney() {
        SharedPreferences prefs = getSharedPreferences("Money", MODE_PRIVATE);
        String savedMoneyString = prefs.getString(PREFS_KEY, "0.0");
        return Double.parseDouble(savedMoneyString);
    }

    ///==============Read saved Money Per Second count method=====================
    private double getSavedMps() {
        SharedPreferences prefs = getSharedPreferences("MPS", MODE_PRIVATE);
        String savedMpsString = prefs.getString(PREFS_KEY, "0.0");

        return Double.parseDouble(savedMpsString);
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
        SharedPreferences prefs1 = getSharedPreferences("Upgrade1", MODE_PRIVATE);
        upgrade1_count = Double.parseDouble(prefs1.getString(PREFS_KEY, "0.0"));
        double costCount = 10 * Math.pow(3, upgrade1_count);
        upgrade1_text.setText("Cost: " + String.format("%.0f", costCount));

        SharedPreferences prefs2 = getSharedPreferences("Upgrade2", MODE_PRIVATE);
        upgrade2_count = Double.parseDouble(prefs2.getString(PREFS_KEY, "0.0"));
        costCount = 30 * Math.pow(1.1, upgrade2_count);
        upgrade2_text.setText("Cost: " + String.format("%.0f", costCount));

        SharedPreferences prefs3 = getSharedPreferences("Upgrade3", MODE_PRIVATE);
        upgrade3_count = Double.parseDouble(prefs3.getString(PREFS_KEY, "0.0"));
        costCount = 100 * Math.pow(1.2, upgrade3_count);
        upgrade3_text.setText("Cost: " + String.format("%.0f", costCount));

        SharedPreferences prefs4 = getSharedPreferences("Upgrade4", MODE_PRIVATE);
        upgrade4_count = Double.parseDouble(prefs4.getString(PREFS_KEY, "0.0"));
        costCount = 250 * Math.pow(1.3, upgrade4_count);
        upgrade4_text.setText("Cost: " + String.format("%.0f", costCount));

        updateUpgradeText();
    }



    ///==============ALL upgrade counts get saved here=====================
    public void saveUpgrades()
    {
        SharedPreferences prefs1 = getSharedPreferences("Upgrade1", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = prefs1.edit();
        editor1.putString(PREFS_KEY, String.valueOf(upgrade1_count));
        editor1.apply();

        SharedPreferences prefs2 = getSharedPreferences("Upgrade2", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = prefs2.edit();
        editor2.putString(PREFS_KEY, String.valueOf(upgrade2_count));
        editor2.apply();

        SharedPreferences prefs3 = getSharedPreferences("Upgrade3", MODE_PRIVATE);
        SharedPreferences.Editor editor3 = prefs3.edit();
        editor3.putString(PREFS_KEY, String.valueOf(upgrade3_count));
        editor3.apply();

        SharedPreferences prefs4 = getSharedPreferences("Upgrade4", MODE_PRIVATE);
        SharedPreferences.Editor editor4 = prefs4.edit();
        editor4.putString(PREFS_KEY, String.valueOf(upgrade4_count));
        editor4.apply();
    }

    private Runnable moneyUpdater_upgade2 = new Runnable() {
        @Override
        public void run() {
            if (upgrade2_count >= 1) {
                money += 0.01 * upgrade2_count;
                updateMoneyText();
                checkUpgradeButtons();
            }
            handler.postDelayed(this, DELAY_TIME_UPGRADE);
        }
    };

    private Runnable moneyUpdater_upgade3 = new Runnable() {
        @Override
        public void run() {
            if (upgrade3_count >= 1) {
                money += 0.1 * upgrade3_count;
                updateMoneyText();
                checkUpgradeButtons();
            }
            handler.postDelayed(this, DELAY_TIME_UPGRADE);
        }
    };

    private Runnable moneyUpdater_upgade4 = new Runnable() {
        @Override
        public void run() {
            if (upgrade4_count >= 1) {
                money += 0.3 * upgrade4_count;
                updateMoneyText();
                checkUpgradeButtons();
            }
            handler.postDelayed(this, DELAY_TIME_UPGRADE);
        }
    };

    private void startMoneyUpdate() {
        handler.postDelayed(moneyUpdater_upgade2, DELAY_TIME_UPGRADE);
        handler.postDelayed(moneyUpdater_upgade3, DELAY_TIME_UPGRADE);
        handler.postDelayed(moneyUpdater_upgade4, DELAY_TIME_UPGRADE);
        isUpgradeUpdating = true;
        checkUpgradeButtons();
    }

    private void stopMoneyUpdate() {
        handler.removeCallbacks(moneyUpdater_upgade2);
        handler.removeCallbacks(moneyUpdater_upgade3);
        handler.removeCallbacks(moneyUpdater_upgade4);
        isUpgradeUpdating = false;
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

        if (money >= upgrade3_cost) {
            upgrade3.setEnabled(true);
        } else {
            upgrade3.setEnabled(false);
        }

        if (money >= upgrade4_cost) {
            upgrade4.setEnabled(true);
        } else {
            upgrade4.setEnabled(false);
        }
    }
}