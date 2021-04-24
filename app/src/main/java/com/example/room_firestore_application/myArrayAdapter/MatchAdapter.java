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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchAdapter extends ArrayAdapter {
    public MatchAdapter(Context context, List<String> matches){
        super(context, 0, matches);
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



//       // Athlete athlete = (Athlete) getItem(position);
//        if(convertView==null){
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_match, parent, false);
//        }
//
//
        TextView tvTeamAName = (TextView) convertView.findViewById(R.id.tvTeamAName);
        TextView tvTeamBName = (TextView) convertView.findViewById(R.id.tvTeamBName);
        TextView tvTeamAScore = (TextView) convertView.findViewById(R.id.tvTeamAScore);
        TextView tvTeamBScore = (TextView) convertView.findViewById(R.id.tvTeamBScore);
        TextView tvResult = (TextView) convertView.findViewById(R.id.tvResult);

//
//        int athleteId,sportId,yearOfBirth;
//        athleteId = athlete.getId();
//        sportId = athlete.getSport_id();
//        yearOfBirth = athlete.getBirth_year();
//        String athleteID = String.valueOf(athleteId);
//        //String sportID = String.valueOf(sportId);
//        String yob = String.valueOf(yearOfBirth);
//
//        String sport = String.valueOf(MainActivity.localDatabase.basicDao().getAthleteSport(sportId));
//
//        tvName.setText(athlete.getName());
//        tvSurname.setText(athlete.getSurname());
//        tvID.setText(athleteID);
//        //tvSportID.setText(sportID);
//        tvSportID.setText(sport);
//        tvCountry.setText(athlete.getCountry());
//        tvCity.setText(athlete.getCity()+", ");
//        tvYoB.setText(yob);

        return convertView;
    }
}
