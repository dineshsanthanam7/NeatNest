package com.example.neatnest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neatnest.adaptor.ServicerAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Firebaseutilc.Firebaseutil;
import User.Usermodelser;

public class ClientBookingActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private RecyclerView servicerRecyclerView;
    private ServicerAdapter servicerAdapter;
    private String clientUserId;
    private String clientUserName;
    private String clientPhoneNumber; // Added client phone number
    private String serviceType;
    private EditText dateEditText;
    private EditText timeEditText;
    private String selectedDate;
    private String selectedTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_booking);

        firestore = FirebaseFirestore.getInstance();
        servicerRecyclerView = findViewById(R.id.servicerRecyclerView);
        servicerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        serviceType = getIntent().getStringExtra("serviceName");
        clientPhoneNumber = getIntent().getStringExtra("clientPhoneNumber"); // Get client phone number
        Toast.makeText(this, serviceType, Toast.LENGTH_SHORT).show();

        // Get service type from intent
        clientUserId = Firebaseutil.currentUserId();
        Firebaseutil.getCurrentUserName(userName -> {
            clientUserName = userName;

            // Load servicers after retrieving the client user name
            loadServicers();
        });

        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);

        dateEditText.setOnClickListener(v -> showDatePickerDialog());
        timeEditText.setOnClickListener(v -> showTimePickerDialog());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            selectedDate = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
            dateEditText.setText(selectedDate);
            // Reload servicers with the new date
            loadServicers();
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            selectedTime = hourOfDay + ":" + minute1;
            timeEditText.setText(selectedTime);
            // Reload servicers with the new time
            loadServicers();
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void loadServicers() {
        firestore.collection("Servicer")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Usermodelser> servicers = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Usermodelser servicer = document.toObject(Usermodelser.class);
                        servicers.add(servicer);
                    }
                    if (!servicers.isEmpty()) {
                        servicerAdapter = new ServicerAdapter(servicers, clientUserId, clientUserName, clientPhoneNumber, serviceType, selectedDate, selectedTime);
                        servicerRecyclerView.setAdapter(servicerAdapter);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                });
    }
}
