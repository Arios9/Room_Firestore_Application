package com.example.room_firestore_application.ui.sport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SportFragment extends Fragment {

    private SportViewModel sportViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentFragment = this;
//        sportViewModel =
//                new ViewModelProvider(this).get(SportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sport, container, false);
//        final TextView textView = root.findViewById(R.id.text_sport);
//        sportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        ListView lv = root.findViewById(R.id.sport_list);
        List<Sport> list = MainActivity.localDatabase.basicDao().getSport();
        List<String> strings = new ArrayList<>();
        for(Sport sport: list) {
            strings.add(sport.toString());
        }


//        String []strings ={"C","C++","Java","Python","Go"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, strings);
        lv.setAdapter(arrayAdapter);
        return root;
    }
}