package com.example.quizodyssey.Models;

public class ResponseCodeAndResultsModel {
    public int response_code;
    public ResultsModel[] results;

    public ResponseCodeAndResultsModel(int response_code, ResultsModel[] results){
        this.response_code=response_code;
        this.results=results;
    }
}
