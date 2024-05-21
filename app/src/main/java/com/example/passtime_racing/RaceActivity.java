package com.example.passtime_racing;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RaceActivity extends AppCompatActivity {

    private ImageView imageView1, imageView2;
    private Button button;
    private float animationDuration = 14.55f;
    private ObjectAnimator animator1, animator2;
    private TextView countdownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        button = findViewById(R.id.button);
        countdownTextView = findViewById(R.id.countdownTextView);

        startAnimations();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reduceAnimationDuration();
            }
        });
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
                button.setVisibility(View.VISIBLE);
                float screenWidth = getResources().getDisplayMetrics().widthPixels;

                animator1 = ObjectAnimator.ofFloat(imageView1, "translationX", 0f, screenWidth - 350f);
                animator1.setDuration((long) (animationDuration * 1000));
                animator1.setRepeatCount(0);
                animator1.start();

                animator2 = ObjectAnimator.ofFloat(imageView2, "translationX", 0f, screenWidth - 350f);
                animator2.setDuration((long) (animationDuration * 1000));
                animator2.setRepeatCount(0);
                animator2.start();
            }
        }.start();
    }

    private void reduceAnimationDuration() {
        animationDuration -= 0.5f;
        animator1.setDuration((long) (animationDuration * 1000));
    }
}