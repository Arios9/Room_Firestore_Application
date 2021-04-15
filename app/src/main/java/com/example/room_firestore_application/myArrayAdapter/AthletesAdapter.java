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
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

import java.util.ArrayList;
import java.util.List;

public class AthletesAdapter extends ArrayAdapter {
    public AthletesAdapter(Context context, List<Athlete> athletes){
        super(context, 0, athletes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Athlete athlete = (Athlete) getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_athlete, parent, false);
        }


        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvSurname = (TextView) convertView.findViewById(R.id.tvSurname);
        TextView tvID = (TextView) convertView.findViewById(R.id.tvID);
        TextView tvSportID = (TextView) convertView.findViewById(R.id.tvSportID);
        TextView tvCountry = (TextView) convertView.findViewById(R.id.tvCountry);
        TextView tvCity = (TextView) convertView.findViewById(R.id.tvCity);
        TextView tvYoB = (TextView) convertView.findViewById(R.id.tvBirthYear);

        int athleteId,sportId,yearOfBirth;
        athleteId = athlete.getId();
        sportId = athlete.getSport_id();
        yearOfBirth = athlete.getBirth_year();
        String athleteID = String.valueOf(athleteId);
        //String sportID = String.valueOf(sportId);
        String yob = String.valueOf(yearOfBirth);

        String sport = String.valueOf(MainActivity.localDatabase.basicDao().getAthleteSport(sportId));

        tvName.setText(athlete.getName());
        tvSurname.setText(athlete.getSurname());
        tvID.setText(athleteID);
        //tvSportID.setText(sportID);
        tvSportID.setText(sport);
        tvCountry.setText(athlete.getCountry());
        tvCity.setText(athlete.getCity()+", ");
        tvYoB.setText(yob);

        return convertView;
    }
}
