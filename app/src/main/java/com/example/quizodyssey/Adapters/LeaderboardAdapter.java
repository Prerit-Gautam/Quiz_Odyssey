package com.example.quizodyssey.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizodyssey.Fragments.LeaderboardFragment;
import com.example.quizodyssey.Models.LeaderboardModel;
import com.example.quizodyssey.R;

import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderBoardViewHolder>{
    Context context;
    LeaderboardModel data[];
    String uid;
    public LeaderboardAdapter(LeaderboardModel data[], Context context, String uid){
        this.data=data;
        this.context=context;
        this.uid=uid;
    }

    @NonNull
    @Override
    public LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LeaderBoardViewHolder(LayoutInflater.from(context).inflate(R.layout.leaderboard_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {
        holder.rank.setText(String.valueOf(position+1));
        holder.name.setText(data[position].name);
        holder.points.setText(String.valueOf(data[position].TotalScore));
        if (data[position].userId.equals(uid)){
            holder.layout.setBackgroundResource(R.drawable.option_selcted);
        }
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class LeaderBoardViewHolder extends RecyclerView.ViewHolder{
        TextView rank;
        TextView name;
        TextView points;
        LinearLayout layout;

        public LeaderBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            rank=itemView.findViewById(R.id.rank);
            name=itemView.findViewById(R.id.name);
            points=itemView.findViewById(R.id.points);
            layout=itemView.findViewById(R.id.layout);
        }
    }

}
