package com.example.neatnest;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.neatnest.adaptor.BookingAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import User.Booking;

public class MainActivity2 extends AppCompatActivity implements BookingAdapter.OnBookingClickListener {

    private FirebaseFirestore firestore;
    private List<Booking> bookingList;
    private BookingAdapter bookingAdapter;
    private RecyclerView bookingsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        firestore = FirebaseFirestore.getInstance();
        bookingList = new ArrayList<>();
        bookingAdapter = new BookingAdapter(bookingList, this);
        bookingsRecyclerView = findViewById(R.id.bookingsRecyclerView);
        bookingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingsRecyclerView.setAdapter(bookingAdapter);

        loadBookings();
    }

    private void loadBookings() {
        firestore.collection("bookings")
                .whereEqualTo("status", "pending")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Booking booking = document.toObject(Booking.class);
                        bookingList.add(booking);
                    }
                    bookingAdapter.notifyDataSetChanged();
                });
    }

    @Override
    public void onBookingClick(Booking booking) {
        Intent intent = new Intent(this, ServicerBookingActivity.class);
        intent.putExtra("bookingId", booking.getBookingId());
        startActivity(intent);
    }
}
