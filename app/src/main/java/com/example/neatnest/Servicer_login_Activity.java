package com.example.neatnest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Servicer_login_Activity extends AppCompatActivity {
TextView signup_servicer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicer_login2);
        signup_servicer=findViewById(R.id.new_servicer);
        signup_servicer.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),Servicer_login.class));
        });
    }
}