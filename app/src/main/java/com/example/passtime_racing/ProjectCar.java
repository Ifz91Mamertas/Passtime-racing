package com.example.passtime_racing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.os.Handler;
import android.widget.Toast;

public class ProjectCar extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    boolean isUpgradeUpdating = false;
    double car_time;
    TextView car_time_text;
    double money;
    double mps;
    double car_upgrade1_cost;
    double car_upgrade2_cost;
    double car_upgrade3_cost;
    double car_upgrade4_cost;
    double car_upgrade5_cost;
    Button car_upgrade1;
    Button car_upgrade2;
    Button car_upgrade3;
    Button car_upgrade4;
    Button button2;
    TextView cu_text1;
    TextView cu_text2;
    TextView cu_text3;
    TextView cu_text4;
    TextView rcu_text1;
    TextView rcu_text2;
    TextView rcu_text3;
    TextView rcu_text4;
    ImageView car_image;
    double upgrade1_count;
    double upgrade2_count;
    double upgrade3_count;
    double upgrade4_count;
    double upgrade5_count;
    double car_upgrade1_count;
    double car_upgrade2_count;
    double car_upgrade3_count;
    double car_upgrade4_count;
    int car_upgrade5_count;
    TextView moneyText;
    private static final String PREFS_KEY = "money_value";
    private int[] carImages = {R.drawable.car, R.drawable.car1, R.drawable.car2};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_car);

        money = getSavedMoney();
        SharedPreferences prefs2 = getSharedPreferences("MPS", MODE_PRIVATE);
        mps = Double.parseDouble(prefs2.getString(PREFS_KEY, "0.0"));


        moneyText = findViewById(R.id.moneyCount);
        car_upgrade1 = (Button) findViewById(R.id.car_upgrade1);
        car_upgrade2 = (Button) findViewById(R.id.car_upgrade2);
        car_upgrade3 = (Button) findViewById(R.id.car_upgrade3);
        car_upgrade4 = (Button) findViewById(R.id.car_upgrade4);
        button2 = findViewById(R.id.button2);
        cu_text1 = findViewById(R.id.cost_cu1);
        cu_text2 = findViewById(R.id.cost_cu2);
        cu_text3 = findViewById(R.id.cost_cu3);
        cu_text4 = findViewById(R.id.cost_cu4);
        rcu_text1 = findViewById(R.id.req_cu1);
        rcu_text2 = findViewById(R.id.req_cu2);
        rcu_text3 = findViewById(R.id.req_cu3);
        rcu_text4 = findViewById(R.id.req_cu4);
        car_image = findViewById(R.id.project_car);
        car_time_text = findViewById(R.id.car_time);

        setMainUpgradeCount();
        checkUpgradeButtons();
        moneyText.setText(String.format("Money: %.0f", money));
        setUpgradeCount();

        if(!isUpgradeUpdating)
        {
            startMoneyUpdate();
        }

        ///==============Click listeners=====================
        checkUpgradeButtons();
        changeUpgrade1();
        changeUpgrade2();
        changeUpgrade3();
        changeUpgrade4();
        setCarTime();
        car_upgrade1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money - car_upgrade1_cost;
                car_upgrade1_count = car_upgrade1_count + 1;
                updateMoneyText();
                saveMoney();
                saveUpgrades();
                updateUpgradeText();
                checkUpgradeButtons();
                changeUpgrade1();
                setCarTime();

                Toast.makeText(ProjectCar.this, String.valueOf(car_upgrade1_count), Toast.LENGTH_SHORT).show();
            }
        });

        car_upgrade2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money - car_upgrade2_cost;
                car_upgrade2_count = car_upgrade2_count + 1;
                updateMoneyText();
                saveMoney();
                saveUpgrades();
                updateUpgradeText();
                checkUpgradeButtons();
                changeUpgrade2();
                setCarTime();

                Toast.makeText(ProjectCar.this, String.valueOf(car_upgrade2_count), Toast.LENGTH_SHORT).show();
            }
        });

        car_upgrade3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money - car_upgrade3_cost;
                car_upgrade3_count = car_upgrade3_count + 1;
                updateMoneyText();
                saveMoney();
                saveUpgrades();
                updateUpgradeText();
                checkUpgradeButtons();
                changeUpgrade3();
                setCarTime();

                Toast.makeText(ProjectCar.this, String.valueOf(car_upgrade3_count), Toast.LENGTH_SHORT).show();
            }
        });

        car_upgrade4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money = money - car_upgrade4_cost;
                car_upgrade4_count = car_upgrade4_count + 1;
                updateMoneyText();
                saveMoney();
                saveUpgrades();
                updateUpgradeText();
                checkUpgradeButtons();
                changeUpgrade4();
                setCarTime();

                Toast.makeText(ProjectCar.this, String.valueOf(car_upgrade4_count), Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("ProjectCar", "ImageView and Button initalized");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d("ProjectCar", "Button clicked, current image index: " + car_upgrade5_count);
                money = money - car_upgrade5_cost;
                car_upgrade5_count = (car_upgrade5_count + 1) % carImages.length;
                //Log.d("ProjectCar", "Button clicked, current image index after increment: " + car_upgrade5_count);
                updateMoneyText();
                saveMoney();
                saveUpgrades();
                //changeUpgrade5();
                car_image.setImageResource(carImages[car_upgrade5_count]);

               // Log.d("ProjectCar", "New image index: " + car_upgrade5_count);
            }
        });

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
                if(item.getItemId() == R.id.action_stats)
                {
                    Intent intent = new Intent(getBaseContext(), Stats.class);
                    startActivity(intent);
                }
                if(item.getItemId() == R.id.action_racemap)
                {
                    Intent intent = new Intent(getBaseContext(), RaceMap.class);
                    startActivity(intent);
                }

                return false;
            }
        });
    }

    private static final long DELAY_TIME_UPGRADE = 100;
    private Handler handler = new Handler();

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

    public void saveUpgrades()
    {
        SharedPreferences prefs1 = getSharedPreferences("Car_Upgrade1", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = prefs1.edit();
        editor1.putString(PREFS_KEY, String.valueOf(car_upgrade1_count));
        editor1.apply();

        SharedPreferences prefs2 = getSharedPreferences("Car_Upgrade2", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = prefs2.edit();
        editor2.putString(PREFS_KEY, String.valueOf(car_upgrade2_count));
        editor2.apply();

        SharedPreferences prefs3 = getSharedPreferences("Car_Upgrade3", MODE_PRIVATE);
        SharedPreferences.Editor editor3 = prefs3.edit();
        editor3.putString(PREFS_KEY, String.valueOf(car_upgrade3_count));
        editor3.apply();

        SharedPreferences prefs4 = getSharedPreferences("Car_Upgrade4", MODE_PRIVATE);
        SharedPreferences.Editor editor4 = prefs4.edit();
        editor4.putString(PREFS_KEY, String.valueOf(car_upgrade4_count));
        editor4.apply();

        SharedPreferences prefs5 = getSharedPreferences("Button2", MODE_PRIVATE);
        SharedPreferences.Editor editor5 = prefs5.edit();
        editor5.putString(PREFS_KEY, String.valueOf(car_upgrade5_count));
        editor5.apply();
    }

    public void setUpgradeCount()
    {
        SharedPreferences prefs1 = getSharedPreferences("Car_Upgrade1", MODE_PRIVATE);
        car_upgrade1_count = Double.parseDouble(prefs1.getString(PREFS_KEY, "0.0"));
        changeUpgrade1();

        SharedPreferences prefs2 = getSharedPreferences("Car_Upgrade2", MODE_PRIVATE);
        car_upgrade2_count = Double.parseDouble(prefs2.getString(PREFS_KEY, "0.0"));
        changeUpgrade2();

        SharedPreferences prefs3 = getSharedPreferences("Car_Upgrade3", MODE_PRIVATE);
        car_upgrade3_count = Double.parseDouble(prefs3.getString(PREFS_KEY, "0.0"));
        changeUpgrade3();

        SharedPreferences prefs4 = getSharedPreferences("Car_Upgrade4", MODE_PRIVATE);
        car_upgrade4_count = Double.parseDouble(prefs4.getString(PREFS_KEY, "0.0"));
        changeUpgrade4();

        SharedPreferences prefs5 = getSharedPreferences("Button2", MODE_PRIVATE);
        car_upgrade5_count = Integer.parseInt(prefs5.getString(PREFS_KEY, "0"));
        //changeUpgrade5();

        updateUpgradeText();
    }

    private void updateUpgradeText()
    {
        ///Updating upgrade 1
        changeUpgrade1();
        cu_text1.setText("Cost: " + String.format("%.0f", car_upgrade1_cost));

        ///Updating upgrade 2
        changeUpgrade2();
        cu_text2.setText("Cost: " + String.format("%.0f", car_upgrade2_cost));

        ///Updating upgrade 3
        changeUpgrade3();
        cu_text3.setText("Cost: " + String.format("%.0f", car_upgrade3_cost));

        ///Updating upgrade 4
        changeUpgrade4();
        cu_text4.setText("Cost: " + String.format("%.0f", car_upgrade4_cost));
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

    private Runnable moneyUpdater_upgade5 = new Runnable() {
        @Override
        public void run() {
            if (upgrade5_count >= 1) {
                money += 0.3 * upgrade5_count;
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
        saveMoney();
        saveUpgrades();
        setMainUpgradeCount();
        saveUpgrades();
        setUpgradeCount();
    }

    private void updateMoneyText() {
        moneyText.setText("Money: " + String.format("%.0f", money));
        saveMoney();
        setMainUpgradeCount();
        saveUpgrades();
        setUpgradeCount();
        checkUpgradeButtons();
    }

    private void saveMoney() {
        SharedPreferences prefs = getSharedPreferences("Money", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFS_KEY, String.valueOf(money));
        editor.apply();
    }

    private void checkUpgradeButtons() {
        car_upgrade1.setEnabled(money >= car_upgrade1_cost && canBuyUpgrade1());
        car_upgrade2.setEnabled(money >= car_upgrade2_cost && canBuyUpgrade2());
        car_upgrade3.setEnabled(money >= car_upgrade3_cost && canBuyUpgrade3());
        car_upgrade4.setEnabled(money >= car_upgrade4_cost && canBuyUpgrade4());
        button2.setEnabled(money >= car_upgrade5_cost);
    }

    private boolean canBuyUpgrade1() {
        return car_upgrade1_count == 0 ||
                (car_upgrade1_count == 1 && upgrade2_count >= 10) ||
                (car_upgrade1_count == 2 && upgrade2_count >= 20 && upgrade3_count >= 2) ||
                (car_upgrade1_count == 3 && upgrade2_count >= 40 && upgrade3_count >= 5 && upgrade4_count >= 2);
    }

    private boolean canBuyUpgrade2() {
        return car_upgrade2_count == 0 ||
                (car_upgrade2_count == 1 && upgrade2_count >= 25) ||
                (car_upgrade2_count == 2 && upgrade3_count >= 25) ||
                (car_upgrade2_count == 3 && upgrade4_count >= 25);
    }

    private boolean canBuyUpgrade3() {
        return car_upgrade3_count == 0 ||
                (car_upgrade3_count == 1 && upgrade1_count >= 1) ||
                (car_upgrade3_count == 2 && upgrade1_count >= 2 && upgrade3_count >= 3) ||
                (car_upgrade3_count == 3 && upgrade1_count >= 5 && upgrade2_count >= 5 && upgrade3_count >= 1);
    }

    private boolean canBuyUpgrade4() {
        return (car_upgrade4_count == 0 && upgrade2_count >= 10)||
                (car_upgrade4_count == 1 && upgrade2_count >= 15 && upgrade3_count >= 5) ||
                (car_upgrade4_count == 2 && upgrade2_count >= 25 && upgrade3_count >= 10 && upgrade4_count >= 1) ||
                (car_upgrade4_count == 3 && upgrade2_count >= 35 && upgrade3_count >= 15 && upgrade4_count >= 5);
    }

    private void changeUpgrade1() {
        switch ((int) car_upgrade1_count) {
            case 0:
                car_upgrade1_cost = 1000;
                rcu_text1.setText("Needs:\n-");
                break;
            case 1:
                car_upgrade1_cost = 2500;
                rcu_text1.setText("Needs:\n\n10 Mechanics\nWorkshop");
                break;
            case 2:
                car_upgrade1_cost = 6000;
                rcu_text1.setText("Needs:\n\n20 Mechanics\nWorkshops\n\n2 Body\nshops");
                break;
            case 3:
                car_upgrade1_cost = 10000;
                rcu_text1.setText("Needs:\n\n40 Mechanics\nWorkshops\n\n5 Body\nshops\n\n2 Race\nSponsors");
                break;
            default:
                car_upgrade1_cost = 0;
                rcu_text1.setText("Max upgrades\nbought");
                break;
        }
    }

    private void changeUpgrade2() {
        switch ((int) car_upgrade2_count) {
            case 0:
                car_upgrade2_cost = 1500;
                rcu_text2.setText("Needs:\n-");
                break;
            case 1:
                car_upgrade2_cost = 2500;
                rcu_text2.setText("Needs:\n\n25 Mechanics\nWorkshops");
                break;
            case 2:
                car_upgrade2_cost = 6000;
                rcu_text2.setText("Needs:\n\n25 Body\nShops");
                break;
            case 3:
                car_upgrade2_cost = 9000;
                rcu_text2.setText("Needs:\n\n25 Race\nSponsors");
                break;
            default:
                car_upgrade2_cost = 0;
                rcu_text2.setText("Max upgrades\nbought");
                break;
        }
    }

    private void changeUpgrade3() {
        switch ((int) car_upgrade3_count) {
            case 0:
                car_upgrade3_cost = 500;
                rcu_text3.setText("Needs:\n-");
                break;
            case 1:
                car_upgrade3_cost = 1500;
                rcu_text3.setText("Needs:\n\n1 Second Hand\nGarage");
                break;
            case 2:
                car_upgrade3_cost = 3000;
                rcu_text3.setText("Needs:\n\n2 Second Hand\nGarages\n\n3 Body\nShops");
                break;
            case 3:
                car_upgrade3_cost = 6500;
                rcu_text3.setText("Needs:\n\n5 Second Hand\nGarages\n\n5 Mechanics\nWorkshops\n\n1 Body\nShops");
                break;
            default:
                car_upgrade3_cost = 0;
                rcu_text3.setText("Max upgrades\nbought");
                break;
        }
    }

    private void changeUpgrade4() {
        switch ((int) car_upgrade4_count) {
            case 0:
                car_upgrade4_cost = 3000;
                rcu_text4.setText("Needs:\n\n10 Mechanics\nWorkshops");
                break;
            case 1:
                car_upgrade4_cost = 7500;
                rcu_text4.setText("Needs:\n\n15 Mechanics\nWorkshops\n\n5 Body\nShops");
                break;
            case 2:
                car_upgrade4_cost = 15000;
                rcu_text4.setText("Needs:\n\n25 Mechanics\nWorkshops\n\n10 Body\nShops\n\n1 Race\nSponsor");
                break;
            case 3:
                car_upgrade4_cost = 30000;
                rcu_text4.setText("Needs:\n\n35 Mechanics\nWorkshops\n\n15 Body\nShops\n\n5 Race\nSponsors");
                break;
            default:
                car_upgrade4_cost = 0;
                rcu_text4.setText("Max upgrades\nbought");
                break;
        }
    }

   /* private void changeUpgrade5() {
        switch ((int) car_upgrade5_count) {
            case 0:
                car_upgrade5_cost = 0;
                car_image.setImageResource(R.drawable.car);
                break;
            case 1:
                car_upgrade5_cost = 500;
                car_image.setImageResource(R.drawable.car1);
                break;
            case 2:
                car_upgrade5_cost = 5000;
                car_image.setImageResource(R.drawable.car2);
                break;
            case 3:
                car_upgrade5_cost = 30000;
                car_image.setImageResource(R.drawable.car);
                break;
            default:
                car_upgrade5_cost = 0;
                car_image.setImageResource(R.drawable.car);
                break;
        }
    }*/

    private void setCarTime()
    {
        car_time = 15f - (0.5f * car_upgrade1_count) - (0.15f * car_upgrade2_count)
                - (0.05f * car_upgrade3_count) - (0.2f * car_upgrade4_count);
        car_time_text.setText(String.format("Current 1/4 mile time:\n%.2f seconds", car_time));
    }


    private double getSavedMoney() {
        SharedPreferences prefs = getSharedPreferences("Money", MODE_PRIVATE);
        String savedMoneyString = prefs.getString(PREFS_KEY, "0.0");
        return Double.parseDouble(savedMoneyString);
    }

    public void setMainUpgradeCount()
    {
        SharedPreferences prefs1 = getSharedPreferences("Upgrade1", MODE_PRIVATE);
        upgrade1_count = Double.parseDouble(prefs1.getString(PREFS_KEY, "0.0"));

        SharedPreferences prefs2 = getSharedPreferences("Upgrade2", MODE_PRIVATE);
        upgrade2_count = Double.parseDouble(prefs2.getString(PREFS_KEY, "0.0"));

        SharedPreferences prefs3 = getSharedPreferences("Upgrade3", MODE_PRIVATE);
        upgrade3_count = Double.parseDouble(prefs3.getString(PREFS_KEY, "0.0"));

        SharedPreferences prefs4 = getSharedPreferences("Upgrade4", MODE_PRIVATE);
        upgrade4_count = Double.parseDouble(prefs4.getString(PREFS_KEY, "0.0"));

        SharedPreferences prefs5 = getSharedPreferences("Button2", MODE_PRIVATE);
        upgrade5_count = Double.parseDouble(prefs5.getString(PREFS_KEY, "0.0"));

        updateUpgradeText();
    }
}