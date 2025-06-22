package com.example.quizodyssey.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizodyssey.Activities.HomeActivity;
import com.example.quizodyssey.Adapters.TriviaModeAdapter;
import com.example.quizodyssey.Models.TriviaModeModel;
import com.example.quizodyssey.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TriviaModesFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<TriviaModeModel> data=new ArrayList<>();
    String appVersion="1.0.0";
    public View view;
    String updateCheckerUrl="http://quizodessy.mooo.com/quiz_odessy_api/update.php";


    Context context;
    public TriviaModesFragment(Context context) {
        // Required empty public constructor
        this.context=context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_trivia_modes, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));

        StringRequest request=new StringRequest(Request.Method.POST, updateCheckerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("100")){
                    //The app is uptodate
                    initialiseModes();
                    TriviaModeAdapter adapter=new TriviaModeAdapter(context, data);
                    recyclerView.setAdapter(adapter);
                }else {
                    Dialog dialog=new Dialog(context);

                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentView(R.layout.update_dialog_bg);
                    dialog.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(request);
        return view;
    }
}