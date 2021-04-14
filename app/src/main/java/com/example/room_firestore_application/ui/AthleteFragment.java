package com.example.room_firestore_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.MyActivities.AthleteActivity;
import com.example.room_firestore_application.R;
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

    private void createList() {
        list = MainActivity.localDatabase.basicDao().getAthlete();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
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
                return true;
            }
        });
    }
}