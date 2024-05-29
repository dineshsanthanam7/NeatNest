package com.example.neatnest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neatnest.adaptor.ServicesAdapter;

import java.util.ArrayList;
import java.util.List;

import Firebaseutilc.Service;

public class MainActivity extends AppCompatActivity {

    private RecyclerView servicesRecyclerView;
    private ServicesAdapter servicesAdapter;
    private List<
            Service> serviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        servicesRecyclerView = findViewById(R.id.servicesRecyclerView);
        servicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Mock data for services
        serviceList = new ArrayList<>();
        serviceList.add(new Service("Home Cleaning", "Household", "Thorough cleaning of your home including living rooms, bedrooms, bathrooms, and kitchen."));
        serviceList.add(new Service("Office Cleaning", "Commercial", "Professional cleaning for your office including workspaces, conference rooms, and restrooms."));
        serviceList.add(new Service("Carpet Cleaning", "Specialty", "Deep cleaning for carpets, removing stains and odors."));

        servicesAdapter = new ServicesAdapter(serviceList);
        servicesRecyclerView.setAdapter(servicesAdapter);
    }
}
