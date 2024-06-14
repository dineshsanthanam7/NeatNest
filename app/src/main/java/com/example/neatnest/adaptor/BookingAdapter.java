package com.example.neatnest.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.neatnest.R;
import java.util.List;
import User.Booking;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private List<Booking> bookingList;
    private OnBookingClickListener listener;

    public interface OnBookingClickListener {
        void onBookingClick(Booking booking);
    }

    public BookingAdapter(List<Booking> bookingList, OnBookingClickListener listener) {
        this.bookingList = bookingList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.serviceNameTextView.setText(booking.getServiceName());
        holder.customerNameTextView.setText(booking.getCustomerName());
        holder.timestampTextView.setText(booking.getTimestamp().toDate().toString());
        holder.itemView.setOnClickListener(v -> listener.onBookingClick(booking));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView serviceNameTextView;
        TextView customerNameTextView;
        TextView timestampTextView;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);
            customerNameTextView = itemView.findViewById(R.id.customerNameTextView);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
        }
    }
}
