package com.example.neatnest.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.neatnest.R;

import java.util.List;

import Firebaseutilc.Service;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<Service> services;
    private OnServiceClickListener listener;

    public ServiceAdapter(List<Service> services, OnServiceClickListener listener) {
        this.services = services;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = services.get(position);
        holder.bind(service);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public interface OnServiceClickListener {
        void onServiceClick(Service service);
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView serviceNameTextView;
        TextView serviceDescriptionTextView;

        ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);
            serviceDescriptionTextView = itemView.findViewById(R.id.serviceDescriptionTextView);
            itemView.setOnClickListener(this);
        }

        void bind(Service service) {
            serviceNameTextView.setText(service.getServiceName());
            serviceDescriptionTextView.setText(service.getServiceDescription());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Service service = services.get(position);
                listener.onServiceClick(service);
            }
        }
    }
}
