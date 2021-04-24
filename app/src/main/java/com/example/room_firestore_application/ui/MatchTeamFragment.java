package com.example.room_firestore_application.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.room_firestore_application.Local_Tables.Team;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.MyActivities.MatchActivity;
import com.example.room_firestore_application.R;

import java.util.ArrayList;
import java.util.List;

public class MatchTeamFragment extends Fragment {

    OnDataPass dataPasser;

    EditText etTeamA,etTeamB;
    Spinner sTeamA,sTeamB;
    Button button;


    String sport;

    public static MatchTeamFragment newInstance(String sport){
        MatchTeamFragment frag = new MatchTeamFragment();
        Bundle args = new Bundle();
        args.putString("sport", sport);
        frag.setArguments(args);
        return frag;
    }

    public MatchTeamFragment() {
        // Required empty public constructor
    }

    public interface OnDataPass{
        public void onDataPass(String data,String dataB,String dataC, String dataD);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_match_team, container, false);

        Bundle args = getArguments();
        sport = args.getString("sport");

        etTeamA = view.findViewById(R.id.etTeamASc);
        etTeamB = view.findViewById(R.id.etTeamBSc);
        button = view.findViewById(R.id.button_confirm_results);

        List<String> spinnerArrayTeams = new ArrayList<String>();
        spinnerArrayTeams = MainActivity.localDatabase.basicDao().getTeamsBySport(sport);

        //Giving the names of the teams to the newly created String Array for the Spinner
//        for(Team i: teams){
//            String name = i.getName();
//            spinnerArrayTeams.add(name);
//        }

        //Giving the String list that contains the Teams' Names to the Spinner Dropdown Selector
        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, spinnerArrayTeams);

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sTeamA = view.findViewById(R.id.spinnerTeamAf);
        sTeamB = view.findViewById(R.id.spinnerTeamBf);
        sTeamA.setAdapter(adapter1);
        sTeamB.setAdapter(adapter1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  ((MatchActivity) getActivity()).getTeamAScore(String.valueOf(sTeamA));
                passData(String.valueOf(sTeamA.getSelectedItem()),String.valueOf(sTeamB.getSelectedItem()),String.valueOf(etTeamA.getText()),String.valueOf(etTeamB.getText()));

                resetForm();
            }
        });



        return view;
    }

    public void passData(String dataA, String dataB, String dataC, String dataD){
        dataPasser.onDataPass(dataA,dataB,dataC,dataD);
    }

    private void resetForm() {
        etTeamA.setText("");
        etTeamB.setText("");
    }

}