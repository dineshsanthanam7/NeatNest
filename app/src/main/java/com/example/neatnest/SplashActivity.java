package com.example.neatnest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {
    TextView appname;
    LottieAnimationView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appname=findViewById(R.id.appname);
        logo=findViewById(R.id.animation_view);
        appname.animate().translationY(-1100).setDuration(2700).setStartDelay(0);
        logo.animate().translationX(2000).setDuration(2000).setStartDelay(2100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if(Firebaseutil.isloggedin()){
//                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
//                    startActivity(i);
//
//                }
               // else {
                    Intent i = new Intent(getApplicationContext(), user_choice.class);
                    startActivity(i);
               // }
                finish();
            }
        },5000);


    }
}