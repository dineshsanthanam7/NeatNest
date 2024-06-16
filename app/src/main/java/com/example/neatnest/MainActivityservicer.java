package com.example.neatnest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.neatnest.adaptor.ServicerBookingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import User.BookingRoom;

public class MainActivityservicer extends AppCompatActivity implements ServicerBookingAdapter.OnBookingActionListener {

    private RecyclerView recyclerView;
    private List<BookingRoom> bookingsList;
    private List<String> documentIds; // List to store Firestore document IDs
    private ServicerBookingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityservicer);

        recyclerView = findViewById(R.id.bookingsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingsList = new ArrayList<>();
        documentIds = new ArrayList<>(); // Initialize documentIds list
        adapter = new ServicerBookingAdapter(bookingsList, documentIds, this); // Pass this as the listener
        recyclerView.setAdapter(adapter);

        // Get current user ID from Firebase Auth
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String servicerUserId = currentUser.getUid();

            // Query Firestore for bookings of the current servicer
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("bookingRooms")
                    .whereEqualTo("servicerUserId", servicerUserId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        bookingsList.clear();
                        documentIds.clear(); // Clear existing documentIds
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            BookingRoom bookingRoom = documentSnapshot.toObject(BookingRoom.class);
                            String documentId = documentSnapshot.getId(); // Get document ID
                            bookingsList.add(bookingRoom);
                            documentIds.add(documentId); // Store document ID
                        }
                        adapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("MainActivityservicer", "Error retrieving bookings", e);
                        Toast.makeText(MainActivityservicer.this, "Failed to retrieve bookings", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    @Override
    public void onAcceptBooking(BookingRoom booking, String documentId) {
        // Handle booking acceptance logic here
        // Example: Update Firestore with accepted status using documentId
        acceptBookingInFirestore(booking, documentId);
    }

    private void acceptBookingInFirestore(BookingRoom booking, String documentId) {
        // Update status in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("bookingRooms")
                .document(documentId)
                .update("status", "accepted")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainActivityservicer.this, "Booking accepted successfully", Toast.LENGTH_SHORT).show();
                    sendNotificationToClient(booking.getClientUserId());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivityservicer.this, "Failed to accept booking: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void sendNotificationToClient(String clientUserId) {
        // Get client's FCM token from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(clientUserId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String clientFcmToken = documentSnapshot.getString("fcmToken");
                        if (clientFcmToken != null) {
                            // Send notification to client using FCM token
                            sendFcmNotification(clientFcmToken, "Booking Accepted", "Your booking has been accepted.");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("MainActivityservicer", "Failed to get client FCM token", e);
                });
    }

    private void sendFcmNotification(String fcmToken, String title, String message) {
        // Example payload for FCM notification
        JSONObject payload = new JSONObject();
        JSONObject notification = new JSONObject();
        try {
            notification.put("title", title);
            notification.put("body", message);
            payload.put("to", fcmToken);
            payload.put("notification", notification);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send notification using HTTP POST request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send", payload,
                response -> Log.d("FCM", "Notification sent: " + response),
                error -> Log.e("FCM", "Failed to send notification", error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "key=YOUR_SERVER_KEY");
                return headers;
            }
        };

        requestQueue.add(request);
    }
}
