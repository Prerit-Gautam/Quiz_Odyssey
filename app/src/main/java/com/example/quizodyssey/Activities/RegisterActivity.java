package com.example.quizodyssey.Activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizodyssey.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    AnimationDrawable animationDrawable;
    ConstraintLayout main;

    EditText email, password;
    Button register, guest;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        main=findViewById(R.id.main);
        animationDrawable= (AnimationDrawable) main.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();



        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        login=findViewById(R.id.login);
        guest=findViewById(R.id.guest);



        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, LoadingActivity.class);
                intent.putExtra("url", " ");
                intent.putExtra("message", "Your authentication request is being processed. Please wait ");
                intent.putExtra("code", "300");
                startActivity(intent);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt=email.getText().toString().trim();
                String password_txt=password.getText().toString().trim();

                if(TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(password_txt)){
                    Toast.makeText(RegisterActivity.this, "Field(s) cannot be empty", Toast.LENGTH_SHORT).show();
                }else if(password_txt.length()<7){
                    Toast.makeText(RegisterActivity.this, "Password has to be a minimum of 8 characters", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(RegisterActivity.this, LoadingActivity.class);
                    intent.putExtra("url", " ");
                    intent.putExtra("email", email_txt);
                    intent.putExtra("password", password_txt);
                    intent.putExtra("message", "Your authentication request is being processed. Please wait ");
                    intent.putExtra("code", "100");
                    startActivity(intent);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });




    }
}