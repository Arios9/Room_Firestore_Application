package com.example.room_firestore_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.MyActivities.AthleteActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.myArrayAdapter.AthletesAdapter;

import java.util.ArrayList;
import java.util.List;

public class AthleteFragment extends Fragment {

    private ListView listView;
    private List<Athlete> list;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentFragment = this;

        View root = inflater.inflate(R.layout.fragment_athlete, container, false);
        listView = (ListView) root.findViewById(R.id.athlete_list);

        createList();
        add_edit_listener();
        add_delete_listener();

        return root;
    }


    public void createList() {

        list = MainActivity.localDatabase.basicDao().getAthlete();

        AthletesAdapter athletesAdapter = new AthletesAdapter(getActivity(), list);
        listView.setAdapter(athletesAdapter);
    }

    private void add_edit_listener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Athlete athlete = (Athlete) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), AthleteActivity.class);
                intent.putExtra("object",athlete);
                startActivity(intent);
            }
        });
    }

    private void add_delete_listener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Athlete athlete = (Athlete) parent.getItemAtPosition(position);
                MainActivity.localDatabase.basicDao().delete(athlete);
                Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                createList();
                return true;

            }
        });
    }
}