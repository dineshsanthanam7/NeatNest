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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Firebaseutilc.Firebaseutil;
import User.Usermodel;

public class Customer_login extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword,usernameclient,phonenumber_client;
    private Button signupButton,letin;
    Usermodel usermodel;
    private boolean saved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.editTextTextEmailAddress);
        signupPassword = findViewById(R.id.editTextTextPassword);
        usernameclient=findViewById(R.id.username);
        phonenumber_client=findViewById(R.id.mobilenumber);
        letin=findViewById(R.id.let_in);
        signupButton = findViewById(R.id.save);
        signupButton.setOnClickListener(v-> {
            // Get user input
            Animation animation = AnimationUtils.loadAnimation(Customer_login.this, R.anim.button_animation);
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
                        saved=true;
                        Toast.makeText(Customer_login.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                        // Redirect to the login activity
                    } else {
                        // Sign-up failed, display error message
                        Toast.makeText(Customer_login.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
        letin.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(Customer_login.this, R.anim.button_animation);
            // Apply animation to button
            v.startAnimation(animation);
            if(saved==false){
                Toast.makeText(Customer_login.this, "fill the email and password details and click save", Toast.LENGTH_SHORT).show();
            }
            else {


                setUsername();
            }

        });


    }
    void setUsername(){
       String phonenumber=phonenumber_client.getText().toString();
        String username=usernameclient.getText().toString();
        if(username.isEmpty() || username.length()<3){
            usernameclient.setError("username should be atleast 3 char");
            return;
        }

        if(usermodel!=null){
            usermodel.setUsername(username);
        }
        else{
            usermodel= new Usermodel(username,phonenumber, Timestamp.now(), Firebaseutil.currentUserId());

        }
        Firebaseutil.currentUserDetails().set(usermodel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(Customer_login.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        });
    }
}
