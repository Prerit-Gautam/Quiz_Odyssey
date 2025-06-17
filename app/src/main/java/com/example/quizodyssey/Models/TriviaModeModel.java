package com.example.quizodyssey.Models;

import com.example.quizodyssey.Adapters.TriviaModeAdapter;

public class TriviaModeModel {
    public int image;
    public String text;
    public String url;


    public TriviaModeModel(int image, String text, String url){
        this.image=image;
        this.text=text;
        this.url=url;
    }
}
