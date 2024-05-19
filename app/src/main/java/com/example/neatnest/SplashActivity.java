package com.example.neatnest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {
    TextView appname;
    private static final String NOTIFICATION_CHANNEL_ID = "location_notification_channel";
    private NotificationManager notificationManager;
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
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                createNotificationChannel();
                showNotification("Welcome .. to NeatNest");


                Intent i = new Intent(getApplicationContext(), user_choice.class);
                    startActivity(i);

               // }
                finish();
            }
        },5000);


    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Welcome Message", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(String message) {
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("Welcome Message")
                    .setContentText(message)
                    .setSmallIcon(android.R.drawable.ic_dialog_info) // Use a default system icon
                    .build();
        }
        notificationManager.notify(0, notification);
    }

}