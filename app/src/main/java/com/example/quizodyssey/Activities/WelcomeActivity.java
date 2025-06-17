package com.example.quizodyssey.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizodyssey.R;

public class WelcomeActivity extends AppCompatActivity {

    AnimationDrawable animDrawable;
    ConstraintLayout main;
    Button getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences=getSharedPreferences("data", MODE_PRIVATE);
        boolean data=preferences.getBoolean("firstTime", true);
        if(!data){
            startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
            finish();
        }


        setContentView(R.layout.activity_welcome);

        main=findViewById(R.id.main);
        getStarted=findViewById(R.id.getStarted);

        animDrawable = (AnimationDrawable) main.getBackground();
        animDrawable.setEnterFadeDuration(2500);
        animDrawable.setExitFadeDuration(5000);
        animDrawable.start();

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("firstTime", false);
                editor.apply();


                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}