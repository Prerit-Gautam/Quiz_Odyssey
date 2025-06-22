package com.example.quizodyssey.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizodyssey.Activities.LoginActivity;
import com.example.quizodyssey.Activities.WelcomeActivity;
import com.example.quizodyssey.Models.ProfileModel;
import com.example.quizodyssey.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

    FloatingActionButton signOut;
    TextView username, totalScore, monthlyScore;
    Context context;
    public ProfileFragment(Context context) {
        // Required empty public constructor
        this.context=context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        signOut=view.findViewById(R.id.signout);
        username=view.findViewById(R.id.username);
        totalScore=view.findViewById(R.id.totalScore);
        monthlyScore=view.findViewById(R.id.monthlyScore);

        SharedPreferences sp= getActivity().getSharedPreferences("data", MODE_PRIVATE);
        String uid=sp.getString("uid", " ");

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code to singout the user
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.signout_dialog_bg);
                dialog.show();

                Button cancel=dialog.findViewById(R.id.cancel);
                Button signOut=dialog.findViewById(R.id.signout);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                signOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth auth=FirebaseAuth.getInstance();
                        auth.signOut();
                        SharedPreferences sp= getContext().getSharedPreferences("data", MODE_PRIVATE);
                        sp.edit().clear();
                        dialog.dismiss();
                        Toast.makeText(context, "Logged out sucessfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, LoginActivity.class));
                        requireActivity().finish();
                    }
                });

            }
        });
        String url="http://quizodessy.mooo.com/quiz_odessy_api/getScoreAndUsername.php";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("PROFILE_RESPONSE", "response "+response);
                Gson gson=new GsonBuilder().create();
                ProfileModel data = gson.fromJson(response, ProfileModel.class);
                if (data != null && data.name != null) {
                    username.setText(data.name);
                    totalScore.setText(data.totalScore);
                    monthlyScore.setText(data.monthlyScore);
                } else {
                    Toast.makeText(context, "Failed to load user data", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> data=new HashMap<>();
                data.put("userId", uid);
                return data;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(context);
        queue.add(request);

        return view;
    }
}