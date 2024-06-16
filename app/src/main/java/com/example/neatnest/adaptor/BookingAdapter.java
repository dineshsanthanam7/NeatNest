//package com.example.neatnest.adaptor;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.neatnest.R;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.List;
//
//import Firebaseutilc.FIrebaseutils;
//import User.BookingRoom;
//
//public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
//
//    private List<BookingRoom> bookingList;
//
//    public BookingAdapter(List<BookingRoom> bookingList) {
//        this.bookingList = bookingList;
//    }
//
//    @NonNull
//    @Override
//    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking2, parent, false);
//        return new BookingViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
//        BookingRoom booking = bookingList.get(position);
//        holder.bind(booking);
//    }
//
//    @Override
//    public int getItemCount() {
//        return bookingList.size();
//    }
//
//    // ViewHolder class
//    static class BookingViewHolder extends RecyclerView.ViewHolder {
//        TextView clientNameTextView;
//        TextView bookingDateTimeTextView;
//        Button acceptBookingButton;
//
//        BookingViewHolder(@NonNull View itemView) {
//            super(itemView);
//            clientNameTextView = itemView.findViewById(R.id.clientNameTextView);
//            bookingDateTimeTextView = itemView.findViewById(R.id.bookingDateTimeTextView);
//            acceptBookingButton = itemView.findViewById(R.id.acceptBookingButton);
//        }
//
//        void bind(BookingRoom booking) {
//            clientNameTextView.setText(booking.getClientUserName());
//            bookingDateTimeTextView.setText(booking.getSelectedDate() + " " + booking.getSelectedTime());
//
//            acceptBookingButton.setOnClickListener(v -> acceptBooking(booking, itemView));
//        }
//
//        private void acceptBooking(BookingRoom booking, View itemView) {
//            // Derive bookingId from clientUserId and servicerUserId
//            String bookingId = booking.getClientUserId() + "_" + booking.getServicerUserId();
//
//            DocumentReference bookingRef = FIrebaseutils.allCollectionReference()
//                    .document(FIrebaseutils.currentUserId())
//                    .collection("bookingRooms")
//                    .document(bookingId);
//
//            bookingRef.update("status", "accepted")
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(itemView.getContext(), "Booking accepted successfully", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(itemView.getContext(), "Failed to accept booking: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
//    }
//}
