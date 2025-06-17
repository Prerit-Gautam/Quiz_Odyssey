package com.example.quizodyssey.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Grid;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizodyssey.Adapters.TriviaModeAdapter;
import com.example.quizodyssey.Models.TriviaModeModel;
import com.example.quizodyssey.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<TriviaModeModel> data=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);


        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        initialiseModes();

        TriviaModeAdapter adapter=new TriviaModeAdapter(this, data);
        recyclerView.setAdapter(adapter);
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