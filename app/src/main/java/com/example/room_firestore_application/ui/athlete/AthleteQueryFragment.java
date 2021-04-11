package com.example.room_firestore_application.ui.athlete;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

import java.util.List;

public class AthleteQueryFragment extends Fragment {

    TextView textView;




    public AthleteQueryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_athlete_query, container, false);

        textView = view.findViewById(R.id.textView4);
        List<Athlete> athletes = MainActivity.localDatabase.basicDao().getAthlete();
        String result = "";

        for (Athlete i: athletes){
            String sportName = "";
            int code = i.getId();
            int sportCode = i.getSport_id();
            switch (sportCode){
                case 1: sportName = "Football";
                        break;
            }
            String name = i.getName();
            String surname = i.getSurname();
            String country = i.getCountry();
            String city = i.getCity();
            int yob = i.getBirth_year();
            result = result + "\n Id: " + code
                    + "\n Sport : " + sportName
                    +"\n Name: " + name
                    + "\n Surname: " + surname
                    + "\n Country: " +country
                    +"\n City: "+city
                    +"\n Year of Birth: " + yob
                    + "\n";

        }

        textView.setText(result);

        return view;
    }
}