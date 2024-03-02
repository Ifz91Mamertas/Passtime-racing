package com.example.passtime_racing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

public class TempGame extends AppCompatActivity {
    Button backbuton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // backbuton = (Button) findViewById(R.id.button2);
        setContentView(R.layout.activity_temp_game);
       // backbuton.setOnClickListener(new View.OnClickListener()
       // {
          //  @Override
          //  public void onClick(View view)
            //{
             //   Intent intent = new Intent(getBaseContext(), MainMenu.class);
                //intent.putExtra("Data", "Laikinas žaidimo meniu");
            //    startActivity(intent);
          //  }
      //  });
        TextView textView = findViewById(R.id.text);
        String data = getIntent().getStringExtra("Data");
        textView.setText(data);
    }
}