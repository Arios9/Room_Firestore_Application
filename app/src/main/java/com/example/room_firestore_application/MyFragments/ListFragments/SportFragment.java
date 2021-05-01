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

import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.MyActivities.SportActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.myArrayAdapter.SportsAdapter;

import java.util.List;

public class SportFragment extends ParentFragment {

    private List<Sport> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentFragment = this;

        View root = inflater.inflate(R.layout.fragment_sport, container, false);
        listView = root.findViewById(R.id.sport_list);

        createList();
        add_context();

        return root;
    }

    public void createList() {
        list = MainActivity.localDatabase.basicDao().getSport();
        SportsAdapter sportsAdapter = new SportsAdapter(getActivity(),list);
        listView.setAdapter(sportsAdapter);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch(item.getItemId()){
            case 1:{
                int position = getItemPosition();
                AdapterView parent = get_parent();
                Sport sport = (Sport) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), SportActivity.class);
                intent.putExtra("object",sport);
                startActivity(intent);

            }
            break;
            case 2:{
                int position = getItemPosition();
                AdapterView parent = get_parent();
                Sport sport = (Sport) parent.getItemAtPosition(position);
                MainActivity.localDatabase.basicDao().delete(sport);
                Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                createList();
            }
            break;
        }
        return true;
    }

}