package com.example.quizodyssey.Activities;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizodyssey.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ScoreActivity extends AppCompatActivity {
    HashMap<Integer, String> scoreMessages=new HashMap<>();
    int score, points;
    TextView score_holder, message_holder, points_holder;
    Button homeRedirector;
    int retryCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_score);

        initialiseScoreMessages();

        score= parseInt(Objects.requireNonNull(getIntent().getStringExtra("score")));
        points=parseInt(Objects.requireNonNull(getIntent().getStringExtra("points")));

        score_holder=findViewById(R.id.score);
        message_holder=findViewById(R.id.message);
        homeRedirector=findViewById(R.id.home);
        points_holder=findViewById(R.id.points);


        //This should not work untill data has been sent to the db
        homeRedirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ScoreActivity.this, "Please wait while we update the score on database", Toast.LENGTH_SHORT).show();
            }
        });
        score_holder.setText(String.valueOf(score)+" /10");
        message_holder.setText("You increased your score by "+String.valueOf(points));
        points_holder.setText(scoreMessages.get(score));
        sendRequest();




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
    private void sendRequest(){
        String url="http://quizodessy.mooo.com/quiz_odessy_api/updatePoints.php";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("100")){
                    homeRedirector.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(ScoreActivity.this, HomeActivity.class));
                            finish();
                        }
                    });
                }else{
                    retryCount++;
                    Toast.makeText(ScoreActivity.this, "Something went wrong while updating your score. We are trying again", Toast.LENGTH_SHORT).show();

                    if (retryCount<=3){
                        sendRequest();
                    }else{
                        Toast.makeText(ScoreActivity.this, "This game's data will not be saved. We are sorry for the inconvenience.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ScoreActivity.this, HomeActivity.class));
                        finish();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ScoreActivity.this, "Something went wrong while updating your score. We are trying again", Toast.LENGTH_SHORT).show();
                retryCount++;
                if (retryCount<=3){
                    sendRequest();
                }else{
                    Toast.makeText(ScoreActivity.this, "This game's data will not be saved. We are sorry for the inconvenience.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ScoreActivity.this, HomeActivity.class));
                    finish();
                }
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences sp=getSharedPreferences("data", MODE_PRIVATE);
                String uid=sp.getString("uid", " ");
                HashMap<String, String> data=new HashMap<>();
                data.put("userId", uid);
                data.put("points", String.valueOf(points));
                return data;
            }
        };
        Volley.newRequestQueue(this).add(request);

    }
}