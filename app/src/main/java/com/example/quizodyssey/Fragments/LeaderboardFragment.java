package com.example.quizodyssey.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.service.chooser.AdditionalContentContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizodyssey.Adapters.LeaderboardAdapter;
import com.example.quizodyssey.Models.LeaderboardModel;
import com.example.quizodyssey.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class LeaderboardFragment extends Fragment {
    int retryCounts=0;
    Context context;
    public LeaderboardFragment(Context context) {
        this.context=context;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_leaderboard, container, false);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        sendRequest(recyclerView);


        return view;
    }
    public void sendRequest(RecyclerView recyclerView){
        String url="http://quizodessy.mooo.com/quiz_odessy_api/leaderboard.php";
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LOG", "response "+response );
                Gson gson=new GsonBuilder().create();
                LeaderboardModel data[]=gson.fromJson(response, LeaderboardModel[].class);
                String uid=getActivity().getSharedPreferences("data", MODE_PRIVATE).getString("uid", " ");
                LeaderboardAdapter adapter=new LeaderboardAdapter(data, context, uid);

                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Handler handler=new Handler();
                retryCounts++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // retry the request
                        if (retryCounts<4) {
                            sendRequest(recyclerView);
                        }else{
                            Toast.makeText(context, "Something is wrong with the server or your internet at the moment. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 5000);

            }
        });
        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(request);
    }
}