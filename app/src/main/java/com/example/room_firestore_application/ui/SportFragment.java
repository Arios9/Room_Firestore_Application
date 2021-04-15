package com.example.room_firestore_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.MyActivities.SportActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.myArrayAdapter.SportsAdapter;

import java.util.List;

public class SportFragment extends Fragment {

    private ListView listView;
    private List<Sport> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentFragment = this;

        View root = inflater.inflate(R.layout.fragment_sport, container, false);
        listView = root.findViewById(R.id.sport_list);

        createList();
        add_edit_listener();
        add_delete_listener();

        return root;
    }

    public void createList() {
        list = MainActivity.localDatabase.basicDao().getSport();
        SportsAdapter sportsAdapter = new SportsAdapter(getActivity(),list);
        listView.setAdapter(sportsAdapter);
    }

    private void add_edit_listener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sport sport = (Sport) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), SportActivity.class);
                intent.putExtra("object",sport);
                startActivity(intent);
            }
        });
    }

    private void add_delete_listener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Sport sport = (Sport) parent.getItemAtPosition(position);
                MainActivity.localDatabase.basicDao().delete(sport);
                Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                createList();
                return true;
            }
        });
    }

}