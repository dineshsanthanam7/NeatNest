package com.example.neatnest;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import User.Booking;

public class ServicerBookingActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private DocumentReference bookingRef;
    private TextView bookingDetailsTextView;
    private Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicer_booking);

        firestore = FirebaseFirestore.getInstance();

        bookingDetailsTextView = findViewById(R.id.bookingDetailsTextView);
        acceptButton = findViewById(R.id.acceptButton);

        // Retrieve booking ID from intent
        String bookingId = getIntent().getStringExtra("bookingId");
        bookingRef = firestore.collection("bookings").document(bookingId);

        // Fetch booking details and show them in UI
        bookingRef.get().addOnSuccessListener(documentSnapshot -> {
            Booking booking = documentSnapshot.toObject(Booking.class);
            if (booking != null) {
                bookingDetailsTextView.setText("Service: " + booking.getServiceName() + "\nCustomer: " + booking.getCustomerName());
            }
        });

        // Accept button click listener
        acceptButton.setOnClickListener(v -> {
            // Update the booking status and servicer name in Firestore
            bookingRef.update("status", "accepted")
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Booking accepted", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(this, "Failed to accept booking", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
