package com.example.neatnest.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neatnest.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import User.BookingRoom;
import User.Usermodelser;

public class ServicerAdapter extends RecyclerView.Adapter<ServicerAdapter.ServicerViewHolder> {

    private List<Usermodelser> servicerList;
    private String clientUserId;
    private String clientUserName;
    private String clientPhoneNumber; // Added client phone number
    private String serviceType;
    private String selectedDate;
    private String selectedTime;

    public ServicerAdapter(List<Usermodelser> servicerList, String clientUserId, String clientUserName, String clientPhoneNumber, String serviceType, String selectedDate, String selectedTime) {
        this.servicerList = servicerList;
        this.clientUserId = clientUserId;
        this.clientUserName = clientUserName;
        this.clientPhoneNumber = clientPhoneNumber;
        this.serviceType = serviceType;
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;
    }

    @NonNull
    @Override
    public ServicerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicer, parent, false);
        return new ServicerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicerViewHolder holder, int position) {
        Usermodelser servicer = servicerList.get(position);
        holder.servicerName.setText(servicer.getUsername());
        holder.servicerPhone.setText(servicer.getPhonenumber());
        holder.servicerAddress.setText(servicer.getAddress());
        holder.servicerExperience.setText(servicer.getWorkexperience());

        holder.bookButton.setOnClickListener(v -> {
            // Create the booking room with client and servicer details
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            String roomId = clientUserId + "_" + servicer.getUserID();
            BookingRoom bookingRoom = new BookingRoom(clientUserId, clientUserName, clientPhoneNumber,
                    servicer.getUserID(), servicer.getUsername(), servicer.getPhonenumber(),
                    serviceType, selectedDate, selectedTime, "pending");

            firestore.collection("bookingRooms").document(roomId).set(bookingRoom)
                    .addOnSuccessListener(aVoid -> {
                        // Handle success, e.g., show a confirmation message
                        Toast.makeText(
                                holder.itemView.getContext(),
                                "Booking Successful",
                                Toast.LENGTH_SHORT
                        ).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(
                                holder.itemView.getContext(),
                                "Booking Failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return servicerList.size();
    }

    public static class ServicerViewHolder extends RecyclerView.ViewHolder {
        TextView servicerName, servicerPhone, servicerAddress, servicerExperience;
        Button bookButton;

        public ServicerViewHolder(@NonNull View itemView) {
            super(itemView);
            servicerName = itemView.findViewById(R.id.servicerName);
            servicerPhone = itemView.findViewById(R.id.servicerPhone);
            servicerAddress = itemView.findViewById(R.id.servicerAddress);
            servicerExperience = itemView.findViewById(R.id.servicerExperience);
            bookButton = itemView.findViewById(R.id.bookButton);
        }
    }
}
