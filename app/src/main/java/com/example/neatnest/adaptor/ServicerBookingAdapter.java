package com.example.neatnest.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neatnest.R;

import java.util.List;

import User.BookingRoom;

public class ServicerBookingAdapter extends RecyclerView.Adapter<ServicerBookingAdapter.BookingsViewHolder> {

    private List<BookingRoom> bookingsList;
    private List<String> documentIds; // List to store document IDs
    private OnBookingActionListener listener;

    public ServicerBookingAdapter(List<BookingRoom> bookingsList, List<String> documentIds, OnBookingActionListener listener) {
        this.bookingsList = bookingsList;
        this.documentIds = documentIds;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking2, parent, false);
        return new BookingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsViewHolder holder, int position) {
        BookingRoom booking = bookingsList.get(position);
        holder.clientUserName.setText("Client Name: " + booking.getClientUserName());
        holder.servicerUserName.setText("Servicer Name: " + booking.getServicerUserName());
        holder.serviceType.setText("Service Type: " + booking.getServiceType());
        holder.selectedDate.setText("Date: " + booking.getSelectedDate());
        holder.selectedTime.setText("Time: " + booking.getSelectedTime());
        holder.status.setText("Status: " + booking.getStatus());
        holder.servicerphonenumber.setText("Servicer Phone Number: " + booking.getServicerPhoneNumber());
        holder.timestamp.setText("Timestamp: " + booking.getTimestamp().toDate().toString());

        // Set the visibility of the accept button based on the booking status
        if ("accepted".equals(booking.getStatus())) {
            holder.acceptButton.setVisibility(View.GONE); // Hide the button if the booking is already accepted
        } else {
            holder.acceptButton.setVisibility(View.VISIBLE); // Show the button if the booking is not accepted
        }

        // Set click listener for accept button
        holder.acceptButton.setOnClickListener(v -> {
            // Notify listener that accept button is clicked
            listener.onAcceptBooking(booking, documentIds.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return bookingsList.size();
    }

    public static class BookingsViewHolder extends RecyclerView.ViewHolder {
        TextView clientUserName, servicerUserName, serviceType, selectedDate, selectedTime, status, servicerphonenumber, timestamp;
        Button acceptButton;

        public BookingsViewHolder(@NonNull View itemView) {
            super(itemView);
            clientUserName = itemView.findViewById(R.id.clientUserName);
            servicerUserName = itemView.findViewById(R.id.servicerUserName);
            serviceType = itemView.findViewById(R.id.serviceType);
            selectedDate = itemView.findViewById(R.id.selectedDate);
            selectedTime = itemView.findViewById(R.id.selectedTime);
            status = itemView.findViewById(R.id.status);
            servicerphonenumber = itemView.findViewById(R.id.servicerphonenumber);
            timestamp = itemView.findViewById(R.id.timestamp);
            acceptButton = itemView.findViewById(R.id.acceptButton); // Accept button
        }
    }

    public interface OnBookingActionListener {
        void onAcceptBooking(BookingRoom booking, String documentId);
    }
}
