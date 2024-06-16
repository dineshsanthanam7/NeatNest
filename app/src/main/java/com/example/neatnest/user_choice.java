package com.example.neatnest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import Firebaseutilc.Firebaseutil;

public class user_choice extends AppCompatActivity {
    TextView splashText;
    Button customer;
    Button servicer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choice);

       splashText = findViewById(R.id.splashText);
        Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
        splashText.startAnimation(alphaAnimation);

        customer=findViewById(R.id.customer);
        servicer=findViewById(R.id.servicer);
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load animation
                Animation animation = AnimationUtils.loadAnimation(user_choice.this, R.anim.button_animation);
                // Apply animation to button
                v.startAnimation(animation);

                Intent i = new Intent(getApplicationContext(), ClientSignInActivity.class);
                 startActivity(i);
                ;
//                if(Firebaseutil.isLoggedIn()){
//                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(i);
//
//                }
//                else {
//                    startActivity(new Intent(getApplicationContext(), Customer_login.class));
//                }
                // You can also add any onClick functionality here
            }
        });
        servicer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load animation
                Animation animation = AnimationUtils.loadAnimation(user_choice.this, R.anim.button_animation);
                // Apply animation to button
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), Servicer_login_Activity.class));
                // You can also add any onClick functionality here
            }
        });



    }
}