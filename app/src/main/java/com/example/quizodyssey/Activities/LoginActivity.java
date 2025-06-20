package com.example.quizodyssey.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizodyssey.R;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button login,  guest;
    TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        guest=findViewById(R.id.guest);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, LoadingActivity.class);
                intent.putExtra("url", " ");
                intent.putExtra("message", "Your authentication request is being processed. Please wait ");
                intent.putExtra("code", "300");
                startActivity(intent);;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_txt=email.getText().toString().trim();
                String password_txt=password.getText().toString().trim();
                if(TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(password_txt)){
                    Toast.makeText(LoginActivity.this, "Field(s) cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(LoginActivity.this, LoadingActivity.class);
                    intent.putExtra("url", " ");
                    intent.putExtra("email", email_txt);
                    intent.putExtra("password", password_txt);
                    intent.putExtra("message", "Your authentication request is being processed. Please wait ");
                    intent.putExtra("code", "200");
                    startActivity(intent);
                }
            }
        });
    }
}