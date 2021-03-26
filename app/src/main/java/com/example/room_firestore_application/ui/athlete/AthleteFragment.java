package com.example.room_firestore_application.ui.athlete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.room_firestore_application.R;

public class AthleteFragment extends Fragment {

    private com.example.room_firestore_application.ui.athlete.AthleteViewModel athleteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        athleteViewModel =
                new ViewModelProvider(this).get(com.example.room_firestore_application.ui.athlete.AthleteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_athlete, container, false);
        final TextView textView = root.findViewById(R.id.text_athlete);
        athleteViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}