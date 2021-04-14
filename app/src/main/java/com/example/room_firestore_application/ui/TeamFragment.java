package com.example.room_firestore_application.ui;

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
import com.example.room_firestore_application.Local_Tables.Team;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;
import java.util.List;

public class TeamFragment extends Fragment {

    private ListView listView;
    private List<Team> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentFragment = this;

        View root = inflater.inflate(R.layout.fragment_team, container, false);
        listView = (ListView) root.findViewById(R.id.team_list);

        createList();
        add_delete_listener();

        return root;
    }

    private void createList() {
        list = MainActivity.localDatabase.basicDao().getTeam();
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
    }

    private void add_delete_listener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Team team = (Team) parent.getItemAtPosition(position);
                MainActivity.localDatabase.basicDao().delete(team);
                Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}