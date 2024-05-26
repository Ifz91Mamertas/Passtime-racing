package com.example.passtime_racing;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RaceActivity extends AppCompatActivity {

    private ImageView imageView1, imageView2;
    private Button button, raceMapButton;
    private float animationDuration;
    private float racer1 = 16.00f;
    private float racer2 = 12.55f;
    private float racer3 = 11.00f;
    private ObjectAnimator animator1, animator2;
    private TextView countdownTextView;

    double car_upgrade1_count;
    double car_upgrade2_count;
    double car_upgrade3_count;
    double car_upgrade4_count;
    private static final String PREFS_KEY = "money_value";
    private Handler handler = new Handler();
    String race;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        button = findViewById(R.id.button);
        raceMapButton = findViewById(R.id.map_button);
        countdownTextView = findViewById(R.id.countdownTextView);
        setUpgradeCount();
        setCarTime();

        Intent intent = getIntent();
        race = intent.getStringExtra("Race");

        startAnimations();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reduceAnimationDuration();
            }
        });

        raceMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RaceActivity.this, RaceMap.class);
                startActivity(intent);
            }
        });
    }

    private void setCarTime()
    {
        animationDuration = (float) (15f - (0.2f * car_upgrade1_count) - (0.15f * car_upgrade2_count)
                        - (0.05f * car_upgrade3_count) - (0.1f * car_upgrade4_count));
    }

    public void setUpgradeCount()
    {
        SharedPreferences prefs1 = getSharedPreferences("Car_Upgrade1", MODE_PRIVATE);
        car_upgrade1_count = Double.parseDouble(prefs1.getString(PREFS_KEY, "0.0"));

        SharedPreferences prefs2 = getSharedPreferences("Car_Upgrade2", MODE_PRIVATE);
        car_upgrade2_count = Double.parseDouble(prefs2.getString(PREFS_KEY, "0.0"));

        SharedPreferences prefs3 = getSharedPreferences("Car_Upgrade3", MODE_PRIVATE);
        car_upgrade3_count = Double.parseDouble(prefs3.getString(PREFS_KEY, "0.0"));

        SharedPreferences prefs4 = getSharedPreferences("Car_Upgrade4", MODE_PRIVATE);
        car_upgrade4_count = Double.parseDouble(prefs4.getString(PREFS_KEY, "0.0"));
    }

    private void startAnimations() {
        countdownTextView.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        new CountDownTimer(5000, 1000)
        {
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                if (secondsLeft > 1) {
                    countdownTextView.setText(String.valueOf(secondsLeft - 1));
                }
                else if (secondsLeft == 1) {
                    countdownTextView.setText("GO!");
                }
            }

            public void onFinish() {
                countdownTextView.setVisibility(View.GONE);
                float screenWidth = getResources().getDisplayMetrics().widthPixels;

                animator1 = ObjectAnimator.ofFloat(imageView1, "translationX", 0f, screenWidth - 380f);
                animator1.setDuration((long) (animationDuration * 1000));
                animator1.setRepeatCount(0);
                animator1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animator2.isRunning()) {
                            onRaceEnd(true);
                            stopButtonVisibilityCycle();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
                animator1.start();

                float racer2timer = 16.00f;

                if ("Race1".equals(race))
                {
                    racer2timer = racer1;
                }
                else if ("Race2".equals(race))
                {
                    racer2timer = racer2;
                }
                else if ("Race3".equals(race))
                {
                    racer2timer = racer3;
                }
                Toast.makeText(RaceActivity.this, "Racer2time: " + racer2timer, Toast.LENGTH_SHORT).show();
                animator2 = ObjectAnimator.ofFloat(imageView2, "translationX", 0f, screenWidth - 380f);
                animator2.setDuration((long) (racer2timer * 1000));
                animator2.setRepeatCount(0);
                animator2.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (animator1.isRunning()) {
                            onRaceEnd(false);
                            stopButtonVisibilityCycle();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {}

                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
                animator2.start();
                startButtonVisibilityCycle();
            }
        }.start();
    }

    private void stopButtonVisibilityCycle() {
        handler.removeCallbacksAndMessages(null);
    }

    private void startButtonVisibilityCycle() {
        final long interval = (long) (animationDuration * 1000 * 0.2); // 20% of animation duration in milliseconds

        final Runnable visibilityRunnable = new Runnable() {
            @Override
            public void run() {
                button.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        button.setVisibility(View.GONE);
                    }
                }, 500); // Show button for 0.5 seconds

                // Schedule the next cycle of button visibility
                if (animator1.isRunning() || animator2.isRunning()) {
                    handler.postDelayed(this, interval);
                }
            }
        };

        // Start the first cycle
        handler.postDelayed(visibilityRunnable, interval);
    }

    private void reduceAnimationDuration() {
        animationDuration -= 0.5f;
        animator1.setDuration((long) (animationDuration * 1000));
    }

    private void onRaceEnd(boolean playerWon) {
        button.setVisibility(View.GONE);
        raceMapButton.setVisibility(View.VISIBLE);
        countdownTextView.setVisibility(View.VISIBLE);

        if (playerWon) {
            countdownTextView.setText("WIN");
            SharedPreferences prefs = getSharedPreferences("Race1Results", MODE_PRIVATE);
            if ("Race1".equals(race))
            {
                prefs = getSharedPreferences("Race1Results", MODE_PRIVATE);
            }
            else if ("Race2".equals(race))
            {
                prefs = getSharedPreferences("Race2Results", MODE_PRIVATE);
            }
            else if ("Race3".equals(race))
            {
                prefs = getSharedPreferences("Race3Results", MODE_PRIVATE);
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(PREFS_KEY, 1);
            editor.apply();
            Toast.makeText(RaceActivity.this, "Race " + race + " saved", Toast.LENGTH_SHORT).show();
        } else {
            countdownTextView.setText("LOSS");
        }
    }
}