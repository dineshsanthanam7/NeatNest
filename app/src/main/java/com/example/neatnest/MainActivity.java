package com.example.neatnest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.neatnest.adaptor.ServiceAdapter;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

import Firebaseutilc.Service;

public class MainActivity extends AppCompatActivity implements ServiceAdapter.OnServiceClickListener {

    private Button bookingDetails;
    private List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        services = new ArrayList<>();
        services.add(new Service("House Cleaning", "Comprehensive house cleaning service."));
        services.add(new Service("Carpet Cleaning", "Professional carpet cleaning."));
        services.add(new Service("Window Cleaning", "Streak-free window cleaning."));

        RecyclerView recyclerView = findViewById(R.id.servicesRecyclerView);
        ServiceAdapter adapter = new ServiceAdapter(services, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        bookingDetails = findViewById(R.id.bookingDetails);
        bookingDetails.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ClientBookingsListActivity.class);
            startActivity(i);
        });
    }

    @Override
    public void onServiceClick(Service service) {
        // Pass the selected service name to ClientBookingActivity
        Intent intent = new Intent(this, ClientBookingActivity.class);
        intent.putExtra("serviceName", service.getServiceName());
        startActivity(intent);
    }
}
