package com.example.neatnest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.neatnest.adaptor.ServicerAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import Firebaseutilc.Firebaseutil;
import User.Usermodelser;

public class ServiceDetailFragment extends Fragment {

    private FirebaseFirestore firestore;
    private RecyclerView servicerRecyclerView;
    private ServicerAdapter servicerAdapter;
    private String clientUserId;
    private String clientUserName;
    private String serviceType;
    private String selectedDate;
    private String selectedTime;

    public static ServiceDetailFragment newInstance(String serviceType) {
        ServiceDetailFragment fragment = new ServiceDetailFragment();
        Bundle args = new Bundle();
        args.putString("serviceType", serviceType);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_detail, container, false);

        firestore = FirebaseFirestore.getInstance();
        servicerRecyclerView = view.findViewById(R.id.servicerRecyclerView);
        servicerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        clientUserId = Firebaseutil.currentUserId();

        // Load servicers
        loadServicers();

        return view;
    }

    private void loadServicers() {
        firestore.collection("Servicer")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Usermodelser> servicers = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Usermodelser servicer = document.toObject(Usermodelser.class);
                        servicers.add(servicer);
                    }
                    if (!servicers.isEmpty()) {
                        servicerAdapter = new ServicerAdapter(servicers, clientUserId, clientUserName, getArguments().getString("serviceType"), selectedDate, selectedTime);
                        servicerRecyclerView.setAdapter(servicerAdapter);
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                });
    }
}
