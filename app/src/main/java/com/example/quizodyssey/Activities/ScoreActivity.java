package com.example.quizodyssey.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizodyssey.R;

import java.util.HashMap;

public class ScoreActivity extends AppCompatActivity {
    HashMap<Integer, String> scoreMessages=new HashMap<>();
    int score;
    TextView score_holder, message_holder;
    Button homeRedirector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score);

        initialiseScoreMessages();

        score=Integer.parseInt(getIntent().getStringExtra("score"));

        score_holder=findViewById(R.id.score);
        message_holder=findViewById(R.id.message);
        homeRedirector=findViewById(R.id.home);

        homeRedirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScoreActivity.this, HomeActivity.class));
                finish();
            }
        });

        score_holder.setText(String.valueOf(score)+" /10");
        message_holder.setText(scoreMessages.get(score));



    }

    private void initialiseScoreMessages() {
        scoreMessages.put(0, "Don't worry, keep practicing! You'll get there.");
        scoreMessages.put(1, "A good start! Keep learning and trying.");
        scoreMessages.put(2, "You're building knowledge. Every answer counts!");
        scoreMessages.put(3, "Nice effort! You're on your way to understanding more.");
        scoreMessages.put(4, "Good job! You're getting better with each question.");
        scoreMessages.put(5, "Halfway there! You've got a solid foundation.");
        scoreMessages.put(6, "Well done! You're showing good knowledge.");
        scoreMessages.put(7, "Great score! You're doing very well.");
        scoreMessages.put(8, "Excellent! You're almost a master of this topic!");
        scoreMessages.put(9, "Outstanding! You're incredibly close to perfection!");
        scoreMessages.put(10, "Perfect score! Absolutely brilliant!");
    }
}