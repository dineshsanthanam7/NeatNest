package com.example.neatnest;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ServiceDetailFragment extends Fragment {

    private static final String ARG_SERVICE_TITLE = "service_title";
    private static final String ARG_SERVICE_TYPE = "service_type";
    private static final String ARG_SERVICE_DESCRIPTION = "service_description";

    public ServiceDetailFragment() {
        // Required empty public constructor
    }

    public static ServiceDetailFragment newInstance(String title, String type, String description) {
        ServiceDetailFragment fragment = new ServiceDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SERVICE_TITLE, title);
        args.putString(ARG_SERVICE_TYPE, type);
        args.putString(ARG_SERVICE_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_detail, container, false);

        TextView titleTextView = view.findViewById(R.id.serviceTitleTextView);
        TextView typeTextView = view.findViewById(R.id.serviceTypeTextView);
        TextView descriptionTextView = view.findViewById(R.id.serviceDescriptionTextView);

        if (getArguments() != null) {
            titleTextView.setText(getArguments().getString(ARG_SERVICE_TITLE));
            typeTextView.setText(getArguments().getString(ARG_SERVICE_TYPE));
            descriptionTextView.setText(getArguments().getString(ARG_SERVICE_DESCRIPTION));
        }

        return view;
    }
}
