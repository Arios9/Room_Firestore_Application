package com.example.room_firestore_application.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.room_firestore_application.Local_Tables.Team;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;
import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentFragment = this;

        View root = inflater.inflate(R.layout.fragment_team, container, false);

        ListView lv = root.findViewById(R.id.team_list);
        List<Team> list = MainActivity.localDatabase.basicDao().getTeam();
        List<String> strings = new ArrayList<>();
        for(Team team: list) {
            strings.add(team.toString());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, strings);
        lv.setAdapter(arrayAdapter);

        return root;
    }
}