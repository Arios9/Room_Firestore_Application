package com.example.room_firestore_application.myArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.Local_Tables.Team;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

import java.util.List;

public class TeamAdapter extends ArrayAdapter {


    public TeamAdapter(Context context, List<Team> teams){
        super(context, 0, teams);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Team team = (Team) getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_team, parent, false);
        }


        TextView tvName = (TextView) convertView.findViewById(R.id.tvTeamName);
        TextView tvID = (TextView) convertView.findViewById(R.id.tvTeamID);
        TextView tvSportName = (TextView) convertView.findViewById(R.id.tvNameSport);
        TextView tvCountry = (TextView) convertView.findViewById(R.id.tvTeamCountry);
        TextView tvCity = (TextView) convertView.findViewById(R.id.tvTeamCity);
        TextView tvStadium = (TextView) convertView.findViewById(R.id.tvStadium);
        TextView tvYoB = (TextView) convertView.findViewById(R.id.textBuildYear);

        int sportId,teamId,yob;
        sportId = team.getSport_id();
        teamId = team.getId();
        String name = team.getName();
        String country = team.getCountry();
        String city = team.getCity();
        String stadium = team.getStadium();
        yob = team.getBirth_year();

        String sport = String.valueOf(MainActivity.localDatabase.basicDao().getTeamSport(sportId));


        tvName.setText(name+" / ");
        tvID.setText(String.valueOf(teamId));
        tvSportName.setText(sport);
        tvCity.setText(city+", ");
        tvCountry.setText(country);
        tvStadium.setText(stadium);
        tvYoB.setText(String.valueOf(yob));

        return convertView;
    }
}
