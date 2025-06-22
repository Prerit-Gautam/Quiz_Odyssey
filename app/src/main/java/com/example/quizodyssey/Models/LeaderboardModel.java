package com.example.quizodyssey.Models;

public class LeaderboardModel {
    public String name;
    public int TotalScore;
    public String userId;

    public LeaderboardModel(String name, int TotalScore, String userId){
        this.name=name;
        this.TotalScore=TotalScore;
        this.userId=userId;
    }
}
