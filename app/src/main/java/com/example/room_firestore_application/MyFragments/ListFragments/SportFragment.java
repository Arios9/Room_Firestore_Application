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
import com.example.room_firestore_application.MyActivities.TeamActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.myArrayAdapter.SportsAdapter;

import java.util.List;

public class SportFragment extends ParentFragment {

    private final static Class ActivityClass = SportActivity.class;
    private final static int FRAGMENT_ID = R.layout.fragment_sport;
    private final static int LIST_ID = R.id.sport_list;

    @Override
    public void createList() {
        List<Sport> list = MainActivity.localDatabase.basicDao().getSport();
        SportsAdapter sportsAdapter = new SportsAdapter(getActivity(),list);
        listView.setAdapter(sportsAdapter);
    }

    @Override
    void EditAction() {
        int position = getItemPosition();
        AdapterView parent = get_parent();
        Sport sport = (Sport) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), SportActivity.class);
        intent.putExtra("object",sport);
        startActivity(intent);
    }

    @Override
    void DeleteAction() {
        int position = getItemPosition();
        AdapterView parent = get_parent();
        Sport sport = (Sport) parent.getItemAtPosition(position);
        MainActivity.localDatabase.basicDao().delete(sport);
        Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
        createList();
    }

    @Override
    int getFragmentId() {
        return FRAGMENT_ID;
    }

    @Override
    int getListId() {
        return LIST_ID;
    }

    @Override
    public Class getActivityClass() {
        return ActivityClass;
    }

}