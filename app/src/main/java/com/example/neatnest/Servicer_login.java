package com.example.neatnest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Servicer_login extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword;
    private Button signupButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicer_login);
        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.editTextTextEmailAddress_ser);
        signupPassword = findViewById(R.id.editTextTextPassword_ser);
        signupButton=findViewById(R.id.proceed);

        signupButton.setOnClickListener(v-> {
            // Get user input
            Animation animation = AnimationUtils.loadAnimation(Servicer_login.this, R.anim.button_animation);
            // Apply animation to button
            v.startAnimation(animation);
            String user = signupEmail.getText().toString().trim();
            String pass = signupPassword.getText().toString().trim();

            // Validate user input
            if (user.isEmpty()){
                signupEmail.setError("Email cannot be empty");
                return;
            }
            if (pass.isEmpty()){
                signupPassword.setError("Password cannot be empty");
                return;
            }

            // Create user with email and password using Firebase Authentication
            auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign-up successful, display a toast message

                        Toast.makeText(Servicer_login.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Servicer_details.class));
                        // Redirect to the login activity
                    } else {
                        // Sign-up failed, display error message
                        Toast.makeText(Servicer_login.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });


    }
}