package com.example.room_firestore_application.ui.athlete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.ui.sport.SportFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class AthleteFragment extends Fragment {

//    private com.example.room_firestore_application.ui.athlete.AthleteViewModel athleteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_athlete, container, false);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_containerAthlete, new AthleteQueryFragment()).addToBackStack(null).commit();
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Athlete Fragment Insert Button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_containerInsertAthlete, new AthleteInsertFragment()).addToBackStack(null).commit();
            }
        });
        FloatingActionButton fabEdit, fabDelete;
        fabEdit = (FloatingActionButton) view.findViewById(R.id.fabEdit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Athlete Fragment Edit Button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fabDelete = (FloatingActionButton) view.findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Athlete Fragment Delete Button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        FloatingActionButton fabRefresh;
        fabRefresh = (FloatingActionButton) view.findViewById(R.id.fabRefresh);
        fabRefresh.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_containerAthlete);
                    if (currentFragment instanceof AthleteQueryFragment) {
                        FragmentTransaction fragTransaction =   (getActivity()).getSupportFragmentManager().beginTransaction();
                        fragTransaction.detach(currentFragment);
                        fragTransaction.attach(currentFragment);
                        fragTransaction.commit();}
        }
        });


        super.onViewCreated(view, savedInstanceState);
    }
}