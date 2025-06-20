package com.example.quizodyssey.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizodyssey.Adapters.TriviaModeAdapter;
import com.example.quizodyssey.Models.TriviaModeModel;
import com.example.quizodyssey.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<TriviaModeModel> data=new ArrayList<>();
    String appVersion="1.0.0";
    String updateCheckerUrl="http://10.0.2.2/quiz_odessy_api/update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);


        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        StringRequest request=new StringRequest(Request.Method.POST, updateCheckerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("100")){
                    //The app is uptodate
                    initialiseModes();
                    TriviaModeAdapter adapter=new TriviaModeAdapter(HomeActivity.this, data);
                    recyclerView.setAdapter(adapter);
                }else {
                    Dialog dialog=new Dialog(HomeActivity.this);

                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentView(R.layout.update_dialog_bg);
                    dialog.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data=new HashMap<>();
                data.put("version", appVersion);
                return data;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(HomeActivity.this);
        queue.add(request);
    }


    private void initialiseModes() {
        data.add(new TriviaModeModel(R.drawable.knowledge, "General Knowledge", "https://opentdb.com/api.php?amount=10&category=9&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.books, "Books", "https://opentdb.com/api.php?amount=10&category=10&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.film, "Films", "https://opentdb.com/api.php?amount=10&category=11&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.music, "Music", "https://opentdb.com/api.php?amount=10&category=12&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.videogame, "Video Games", "https://opentdb.com/api.php?amount=10&category=15&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.boardgame, "Board Games", "https://opentdb.com/api.php?amount=10&category=16&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.mathematics, "Science: Mathematics", "https://opentdb.com/api.php?amount=10&category=19&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.science_and_nature, "Science and Nature", "https://opentdb.com/api.php?amount=10&category=17&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.science_and_computers, "Science: Computers", "https://opentdb.com/api.php?amount=10&category=18&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.sports, "Sports", "https://opentdb.com/api.php?amount=10&category=21&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.earth, "Geography", "https://opentdb.com/api.php?amount=10&category=22&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.history, "Histroy", "https://opentdb.com/api.php?amount=10&category=23&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.mythology, "Mythology", "https://opentdb.com/api.php?amount=10&category=20&type=multiple"));
        data.add(new TriviaModeModel(R.drawable.politics, "Politics", "https://opentdb.com/api.php?amount=10&category=24&type=multiple"));
    }


}