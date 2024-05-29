package com.example.neatnest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Firebaseutilc.Firebaseutil;
import User.Usermodel;

public class Customer_login extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, usernameclient, phonenumber_client;
    private Button signupButton, letin;
    Usermodel usermodel;
    TextView splashText;
    private boolean saved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        splashText = findViewById(R.id.splashText);
        Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
        splashText.startAnimation(alphaAnimation);
        signupEmail = findViewById(R.id.editTextTextEmailAddress);
        signupPassword = findViewById(R.id.editTextTextPassword);
        usernameclient = findViewById(R.id.username);
        phonenumber_client = findViewById(R.id.mobilenumber);
        letin = findViewById(R.id.let_in);
        signupButton = findViewById(R.id.save);

        signupButton.setOnClickListener(v -> {
            // Apply animation to button
            Animation animation = AnimationUtils.loadAnimation(Customer_login.this, R.anim.button_animation);
            v.startAnimation(animation);

            // Get user input
            String user = signupEmail.getText().toString().trim();
            String pass = signupPassword.getText().toString().trim();

            // Validate user input
            if (user.isEmpty()) {
                signupEmail.setError("Email cannot be empty");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                signupEmail.setError("Invalid email format");
                return;
            }
            if (pass.isEmpty()) {
                signupPassword.setError("Password cannot be empty");
                return;
            }
            if (pass.length() < 6) {
                signupPassword.setError("Password should be at least 6 characters");
                return;
            }

            // Create user with email and password using Firebase Authentication
            auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Send verification email
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        saved = true;
                                        Toast.makeText(Customer_login.this, "SignUp Successful. Please check your email for verification", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Customer_login.this, "Failed to send verification email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else {
                        // Sign-up failed, display error message
                        Toast.makeText(Customer_login.this, "SignUp Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        letin.setOnClickListener(v -> {
            // Apply animation to button
            Animation animation = AnimationUtils.loadAnimation(Customer_login.this, R.anim.button_animation);
            v.startAnimation(animation);

            if (!saved) {
                Toast.makeText(Customer_login.this, "Fill the email and password details and click save", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseUser firebaseUser = auth.getCurrentUser();
            if (firebaseUser != null) {
                firebaseUser.reload().addOnCompleteListener(task -> {
                    if (firebaseUser.isEmailVerified()) {
                        setUsername();
                    } else {
                        Toast.makeText(Customer_login.this, "Please verify your email before logging in", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    void setUsername() {
        String phonenumber = phonenumber_client.getText().toString().trim();
        String username = usernameclient.getText().toString().trim();

        if (username.isEmpty() || username.length() < 3) {
            usernameclient.setError("Username should be at least 3 characters");
            return;
        }

        if (usermodel == null) {
            usermodel = new Usermodel(username, phonenumber, Timestamp.now(), Firebaseutil.currentUserId());
        } else {
            usermodel.setUsername(username);
        }

        Firebaseutil.currentUserDetails().set(usermodel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(Customer_login.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(Customer_login.this, "Failed to save user details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
