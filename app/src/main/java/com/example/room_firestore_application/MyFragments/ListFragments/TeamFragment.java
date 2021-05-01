package com.example.room_firestore_application.MyFragments.ListFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.room_firestore_application.Local_Tables.Team;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.MyActivities.TeamActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.myArrayAdapter.TeamAdapter;

import java.util.List;

public class TeamFragment extends ParentFragment {

    private List<Team> list;
    public final Class ActivityClass = TeamActivity.class;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentActivityClass = ActivityClass;

        View root = inflater.inflate(R.layout.fragment_team, container, false);
        listView = (ListView) root.findViewById(R.id.team_list);

        createList();
        add_context();

        return root;
    }

    public void createList() {
        list = MainActivity.localDatabase.basicDao().getTeam();
        TeamAdapter teamAdapter = new TeamAdapter(getActivity(), list);
        listView.setAdapter(teamAdapter);
    }

    @Override
    void EditAction() {
        int position = getItemPosition();
        AdapterView parent = get_parent();
        Team team = (Team) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), TeamActivity.class);
        intent.putExtra("object",team);
        startActivity(intent);
    }

    @Override
    void DeleteAction() {
        int position = getItemPosition();
        AdapterView parent = get_parent();
        Team team = (Team) parent.getItemAtPosition(position);
        MainActivity.localDatabase.basicDao().delete(team);
        Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
        createList();
    }

}