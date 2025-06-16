package com.example.quizodyssey.Activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Html;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizodyssey.Adapters.OptionAdapter;
import com.example.quizodyssey.Models.ResponseCodeAndResultsModel;
import com.example.quizodyssey.Models.ResultsModel;
import com.example.quizodyssey.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    HashMap<Integer, LinearLayout> progress=new HashMap<Integer, LinearLayout>();
    TextView difficulty, genre, question, correctAns;
    RecyclerView recyclerView;
    Button submit, next;
    boolean flag=false;

    String url="https://opentdb.com/api.php?amount=10&type=multiple";
    public static int selected=-1;
    int questionNumber=0;
    OptionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initialiseMap();

        difficulty=findViewById(R.id.difficulty);
        genre=findViewById(R.id.genre);
        question=findViewById(R.id.question);
        correctAns=findViewById(R.id.correctAnswer);
        recyclerView=findViewById(R.id.recyclerView);
        submit=findViewById(R.id.submit);
        next=findViewById(R.id.next);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Parsse the Json to Java Object using the model classes
                GsonBuilder gsonBuilder=new GsonBuilder();
                Gson gson=gsonBuilder.create();

                ResponseCodeAndResultsModel data=gson.fromJson(response, ResponseCodeAndResultsModel.class);
                if (data.response_code==0){
                    //Request and response were both successful

                    ResultsModel[] results=data.results;
                    initialiseQuestions(results, 0, true);

                }else{
                    //Something went wrong
                    Toast.makeText(MainActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    @SuppressLint("SetTextI18n")
    public void initialiseQuestions(ResultsModel[] results, int questionNumber, boolean isMultiple){

        correctAns.setText("");
        progress.get(questionNumber+1).setBackgroundColor(Color.parseColor("#2196F3"));
        next.setVisibility(GONE);

        difficulty.setText("Difficulty : "+Html.fromHtml(results[questionNumber].difficulty.toUpperCase(), Html.FROM_HTML_MODE_LEGACY));
        genre.setText("Genre: "+ String.valueOf(Html.fromHtml(results[questionNumber].category, Html.FROM_HTML_MODE_LEGACY)).toUpperCase());
        question.setText(Html.fromHtml(results[questionNumber].question, Html.FROM_HTML_MODE_LEGACY));
        if(isMultiple) {
            adapter = new OptionAdapter(getApplicationContext(), optionsMultiple(results[questionNumber].correct_answer, results[questionNumber].incorrect_answers));
            recyclerView.setAdapter(adapter);
        }
        //Checks if answer is correct and sets paramenters to different values
        submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                next.setVisibility(VISIBLE);
                if(MainActivity.selected==-1){
                    Toast.makeText(MainActivity.this, "Please select an option before submiting your answer!", Toast.LENGTH_SHORT).show();
                }else{
                    if(flag==false) {
                        //Check if ans is correct
                        correctAns.setText("Correct Answer: " + results[questionNumber].correct_answer);
                        if (results[questionNumber].correct_answer == adapter.selectedText()) {
                            Objects.requireNonNull(progress.get(questionNumber+1)).setBackgroundColor(Color.parseColor("#4CAF50"));
                            Toast.makeText(MainActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                        } else {
                            Objects.requireNonNull(progress.get(questionNumber+1)).setBackgroundColor(Color.parseColor("#F44336"));
                            Toast.makeText(MainActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                        }
                        selected = -1;
                        adapter.notifyDataSetChanged();
                        flag=true;
                    }else{
                        Toast.makeText(MainActivity.this, "Please move on to the next Question", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Sets up variables to load the next question
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=false;
                if(questionNumber==9){
                    //All questions have been displayed
                    Toast.makeText(MainActivity.this, "The Trivia has been completed", Toast.LENGTH_SHORT).show();
                }else{
                    initialiseQuestions(results, questionNumber+1, true);
                }
            }
        });
    }


    public void initialiseMap(){
        progress.put(1, findViewById(R.id.first));
        progress.put(2, findViewById(R.id.second));
        progress.put(3, findViewById(R.id.third));
        progress.put(4, findViewById(R.id.fourth));
        progress.put(5, findViewById(R.id.fifth));
        progress.put(6, findViewById(R.id.sixth));
        progress.put(7, findViewById(R.id.seventh));
        progress.put(8, findViewById(R.id.eighth));
        progress.put(9, findViewById(R.id.nineth));
        progress.put(10, findViewById(R.id.tenth));
    }


    //Randomises the options by shifting them in an array
    public String[] optionsMultiple(String first, String[] incorrect){
        String[] toReturn={first, incorrect[0], incorrect[1], incorrect[2]};
        Random random=new Random();
        int shift=random.nextInt(4);
        int counter=0;
        String temp;
        while(counter!=shift){
            counter++;
            temp=toReturn[0];
            toReturn[0]=toReturn[1];
            toReturn[1]=toReturn[2];
            toReturn[2]=toReturn[3];
            toReturn[3]=temp;
        }
        return toReturn;
    }
}