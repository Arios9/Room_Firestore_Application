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

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.MyActivities.AthleteActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.myArrayAdapter.AthletesAdapter;

import java.util.List;

public class AthleteFragment extends ParentFragment {

    private List<Athlete> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentFragment = this;

        View root = inflater.inflate(R.layout.fragment_athlete, container, false);
        listView = (ListView) root.findViewById(R.id.athlete_list);

        createList();
        add_context();

        return root;
    }


    public void createList() {

        list = MainActivity.localDatabase.basicDao().getAthlete();

        AthletesAdapter athletesAdapter = new AthletesAdapter(getActivity(), list);
        listView.setAdapter(athletesAdapter);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch(item.getItemId()){
            case 1:{
                int position = getItemPosition();
                AdapterView parent = get_parent();
                Athlete athlete = (Athlete) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), AthleteActivity.class);
                intent.putExtra("object",athlete);
                startActivity(intent);

            }
            break;
            case 2:{
                int position = getItemPosition();
                AdapterView parent = get_parent();
                Athlete athlete = (Athlete) parent.getItemAtPosition(position);
                MainActivity.localDatabase.basicDao().delete(athlete);
                Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                createList();
            }
            break;
        }
        return true;
    }

}