package com.example.neatnest;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neatnest.adaptor.ServicerBookingAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import Firebaseutilc.FIrebaseutils;
import User.Usermodel;

public class ServicerDashboardActivity extends AppCompatActivity {

    private static final String TAG = "ServicerDashboardActivity";

    private TextView servicerNameTextView;
    private TextView servicerPhoneNumberTextView;
    private TextView servicerAddressTextView;

    private RecyclerView bookingsRecyclerView;
    private ServicerBookingAdapter bookingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicer_dashboard);


    }

    private void fetchAndDisplayServicerDetails() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String servicerUserId = currentUser.getUid();
            FirebaseFirestore.getInstance().collection("Servicer")
                    .document(servicerUserId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String username = documentSnapshot.getString("username");
                                String phoneNumber = documentSnapshot.getString("phonenumber");
                                String address = documentSnapshot.getString("address");

                                servicerNameTextView.setText(username);
                                servicerPhoneNumberTextView.setText(phoneNumber);
                                servicerAddressTextView.setText(address);
                            } else {
                                Log.e(TAG, "No such document");
                                Toast.makeText(ServicerDashboardActivity.this, "No servicer details found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error fetching servicer details", e);
                            Toast.makeText(ServicerDashboardActivity.this, "Failed to fetch servicer details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Log.e(TAG, "Current user is null");
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchServicerBookings() {
        // Implement your logic to fetch bookings for the servicer
        // This part depends on your Ficrestore schema and how you store booking data
    }



}
