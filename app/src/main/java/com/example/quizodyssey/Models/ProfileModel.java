package com.example.quizodyssey.Models;

public class ProfileModel {
    public String name;
    public String totalScore;
    public String monthlyScore;


    public ProfileModel(String name, String totalScore, String monthlyScore){
        this.name=name;
        this.totalScore=totalScore;
        this.monthlyScore=monthlyScore;
    }
}
