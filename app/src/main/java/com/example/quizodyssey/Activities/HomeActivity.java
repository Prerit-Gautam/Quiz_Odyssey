package com.example.quizodyssey.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.quizodyssey.Fragments.LeaderboardFragment;
import com.example.quizodyssey.Fragments.ProfileFragment;
import com.example.quizodyssey.Fragments.TriviaModesFragment;
import com.example.quizodyssey.Models.TriviaModeModel;
import com.example.quizodyssey.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView btmnv;
    FrameLayout parent;
    TriviaModesFragment triviaModesFragment=new TriviaModesFragment(HomeActivity.this);
    LeaderboardFragment leaderBoardFragment=new LeaderboardFragment(HomeActivity.this);
    ProfileFragment profileFragment=new ProfileFragment(HomeActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Assuming activity_home.xml is the main layout

        // This line makes your app draw behind system bars
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        
        btmnv=findViewById(R.id.btmnv);
        parent=findViewById(R.id.container);

        changeFragment(new TriviaModesFragment(HomeActivity.this));

        btmnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String title=item.getTitle().toString();
                if (title.equals("HOME")){
                    changeFragment(triviaModesFragment);
                }else if (title.equals("LEADERBOARD")){
                    changeFragment(leaderBoardFragment);
                }else{
                    changeFragment(profileFragment);
                }
                return true;
            }
        });
    }

    public void changeFragment(Fragment fragment){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }


}