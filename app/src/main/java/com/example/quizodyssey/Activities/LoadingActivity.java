package com.example.quizodyssey.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quizodyssey.Models.DialogModel;
import com.example.quizodyssey.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoadingActivity extends AppCompatActivity {

    String url, message, code, email, password;
    TextView message_holder;



    /*
    code - 100 = Register request through email
    code - 101 = Register request through gmail
    code - 300 = Continue as a guest
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        url = getIntent().getStringExtra("url");
        message = getIntent().getStringExtra("message");
        code = getIntent().getStringExtra("code");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        message_holder = findViewById(R.id.message);
        message_holder.setText(message);

        if (!url.equals(" ")) {
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Parsse the Json to Java Object using the model classes
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    intent.putExtra("response", response);
                    startActivity(intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoadingActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            queue.add(request);
        } else {
            if (code.equals("100")) {
                registerWithEmail();
            } else if (code.equals("200")) {
                loginWithEmail();
            } else if (code.equals("300")) {
                continueAsGuest();
            }
        }
    }


    private DialogModel showDialog() {
        Dialog dialog = new Dialog(LoadingActivity.this);
        dialog.setContentView(R.layout.name_dialog_bg);
        dialog.setCancelable(false);
        dialog.show();

        Button submit = dialog.findViewById(R.id.submit);
        EditText username = dialog.findViewById(R.id.username);

        return new DialogModel(submit, username, dialog);
    }


    private void continueAsGuest() {
        DialogModel model = showDialog();

        model.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_txt = model.username.getText().toString().trim();
                if (username_txt.length() < 4) {
                    Toast.makeText(LoadingActivity.this, "Username has to be a minimum of 4 characters", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String uid = auth.getCurrentUser().getUid();
                            setUid(uid);
                            sendRequest(model, auth, username_txt);
                        }
                    });

                }
            }
        });
    }

    private void sendRequest(DialogModel model, FirebaseAuth auth, String username_txt) {
        String url = "http://10.0.2.2/quiz_odessy_api/registerUser.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("100")) {
                    // Query was executed successfully
                    model.dialog.dismiss();
                    setUid(auth.getCurrentUser().getUid());

                    startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoadingActivity.this, "Internal Server Error, Please try again later", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoadingActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                finish();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> data = new HashMap<>();
                data.put("userId", auth.getCurrentUser().getUid());
                data.put("name", username_txt);
                return data;
            }
        };
        // call api
        RequestQueue queue = Volley.newRequestQueue(LoadingActivity.this);
        queue.add(stringRequest);
    }
    private void loginWithEmail() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Logged in Successfully;
                    setUid(auth.getCurrentUser().getUid());
                    startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoadingActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoadingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    private void registerWithEmail() {
        DialogModel model = showDialog();

        model.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_txt = model.username.getText().toString().trim();
                if (username_txt.length() < 4) {
                    Toast.makeText(LoadingActivity.this, "Username has to be a minimum of 4 characters", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    if (code.equals("100")) {
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    sendRequest(model, auth, username_txt);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoadingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1000);
                            }
                        });
                    }
                    model.dialog.dismiss();
                }
            }
        });
    }

    private void setUid(String uid) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("uid", uid);
        editor.commit();
    }


}