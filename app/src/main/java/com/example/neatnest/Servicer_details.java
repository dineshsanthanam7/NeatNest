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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import Firebaseutilc.FIrebaseutils;
import Firebaseutilc.Firebaseutil;
import User.Usermodelser;

public class Servicer_details extends AppCompatActivity {
    private FirebaseAuth auth;
    Usermodelser usermodel;
    private EditText usernameServicer,phonenumber_servicer,address_ser,workexp_ser;
    private Button servicer_signup;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicer_details);
        usernameServicer=findViewById(R.id.username_ser);
        phonenumber_servicer=findViewById(R.id.mobilenumber_ser);
        address_ser=findViewById(R.id.address_ser);
        workexp_ser=findViewById(R.id.expirence_ser);
        servicer_signup=findViewById(R.id.servicer);
servicer_signup.setOnClickListener(v -> {


    Animation animation = AnimationUtils.loadAnimation(Servicer_details.this, R.anim.button_animation);
    // Apply animation to button
    v.startAnimation(animation);
    setUsername();
});




    }
    void setUsername(){
        String phonenumber=phonenumber_servicer.getText().toString();
        String username=usernameServicer.getText().toString();
        String address= address_ser.getText().toString();
        String workexp=workexp_ser.getText().toString();
        if(username.isEmpty() || username.length()<3){
            usernameServicer.setError("username should be atleast 3 char");
            return;
        }

        if(usermodel!=null){
            usermodel.setUsername(username);
        }
        else{
            usermodel= new Usermodelser(username,phonenumber, Timestamp.now(), FIrebaseutils.currentUserId(),address,workexp);

        }
        FIrebaseutils.currentUserDetails().set(usermodel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(Servicer_details.this, ServicerDashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        });
    }
}