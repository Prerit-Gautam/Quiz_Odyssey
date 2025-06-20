package com.example.quizodyssey.Models;

import android.app.Dialog;
import android.widget.Button;
import android.widget.EditText;

public class DialogModel {
    public Button submit;
    public EditText username;
    public Dialog dialog;

    public  DialogModel(Button submit, EditText username, Dialog dialog) {
        this.submit=submit;
        this.username=username;
        this.dialog=dialog;
    }
}
