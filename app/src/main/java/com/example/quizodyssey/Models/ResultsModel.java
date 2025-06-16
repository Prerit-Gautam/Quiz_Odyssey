package com.example.quizodyssey.Models;

public class ResultsModel {
    public String type;
    public String difficulty;
    public String category;
    public String question;
    public String correct_answer;
    public String[] incorrect_answers;

    public ResultsModel(String type, String difficulty, String category, String question, String correct_answer, String[] incorrect_answers) {
        this.type = type;
        this.difficulty = difficulty;
        this.category = category;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
    }
}
