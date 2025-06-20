package com.example.quizodyssey.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizodyssey.Activities.LoadingActivity;
import com.example.quizodyssey.Activities.MainActivity;
import com.example.quizodyssey.Models.ResponseCodeAndResultsModel;
import com.example.quizodyssey.Models.ResultsModel;
import com.example.quizodyssey.Models.TriviaModeModel;
import com.example.quizodyssey.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class TriviaModeAdapter extends RecyclerView.Adapter<TriviaModeAdapter.TriviaModeViewHolder>{
    Context context;
    ArrayList<TriviaModeModel> data;
    public TriviaModeAdapter(Context context, ArrayList<TriviaModeModel> data){
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public TriviaModeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TriviaModeViewHolder(LayoutInflater.from(context).inflate(R.layout.triviamode_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TriviaModeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code to start activity
                Intent intent=new Intent(context, LoadingActivity.class);
                intent.putExtra("url", data.get(position).url);
                intent.putExtra("message","Please wait while we load the questions");
                intent.putExtra("code", "");
                context.startActivity(intent);
            }
        });

        holder.title.setText(data.get(position).text);
        holder.icon.setImageResource(data.get(position).image);
    }

    @Override
    public int getItemCount() {
        return data.toArray().length;
    }

    public class TriviaModeViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        CardView card;
        TextView title;
        public TriviaModeViewHolder(@NonNull View itemView) {
            super(itemView);
            card=itemView.findViewById(R.id.cardView);
            icon=itemView.findViewById(R.id.icon);
            title=itemView.findViewById(R.id.title);
        }
    }
}
