package com.example.neatnest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neatnest.adaptor.BookingsAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import User.BookingRoom;

public class ClientBookingsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<BookingRoom> bookingsList;
    private BookingsAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_bookings_list);

        recyclerView = findViewById(R.id.recyclerViewBookings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingsList = new ArrayList<>();
        adapter = new BookingsAdapter(bookingsList);
        recyclerView.setAdapter(adapter);

        // Get current user ID from Firebase Auth
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String clientUserId = currentUser.getUid();

            // Query Firestore for bookings of the current client
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("bookingRooms")
                    .whereEqualTo("clientUserId", clientUserId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        bookingsList.clear();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            BookingRoom bookingRoom = documentSnapshot.toObject(BookingRoom.class);
                            bookingsList.add(bookingRoom);
                        }
                        adapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("ClientBookingsListActivity", "Error retrieving bookings", e);
                        Toast.makeText(ClientBookingsListActivity.this, "Failed to retrieve bookings", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
