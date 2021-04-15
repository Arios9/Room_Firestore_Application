package com.example.room_firestore_application.myArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

import java.util.List;

public class SportsAdapter extends ArrayAdapter {

    public SportsAdapter(Context context, List<Sport> sports){
        super(context, 0, sports);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Sport sport = (Sport) getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sport, parent, false);
        }


        TextView tvName = (TextView) convertView.findViewById(R.id.tvSportName);
        TextView tvSportID = (TextView) convertView.findViewById(R.id.tvIDsport);
        TextView tvSportGender = (TextView) convertView.findViewById(R.id.tvSportGender);
        TextView tvSportType = (TextView) convertView.findViewById(R.id.tvSportType);

        int sportId;
        sportId = sport.getId();
        String name = sport.getName();
        String gender = sport.getGender();
        String sportType = sport.getIndividual();

        tvName.setText(name);
        tvSportID.setText(String.valueOf(sportId));
        tvSportGender.setText(gender);
        tvSportType.setText(sportType);

        return convertView;
    }
}
