package com.example.room_firestore_application.MyActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.room_firestore_application.Local_Tables.Team;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MatchTypeActivity extends AppCompatActivity {

    EditText teamAScore,teamBScore;
    Spinner teamA,teamB;
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_type);

        setRestOfComponents();


    }


    private void setRestOfComponents(){
        teamAScore = findViewById(R.id.etTeamAScore);
        teamBScore = findViewById(R.id.etTeamBScore);
        btnReturn = findViewById(R.id.button_return);

        List<Team> teams = MainActivity.localDatabase.basicDao().getTeam();
        List<String> spinnerArrayTeams = new ArrayList<String>();

        for(Team i: teams){
            String name = i.getName();
            spinnerArrayTeams.add(name);
        }
        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArrayTeams);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamA= findViewById(R.id.spinnerTeamAt);
        teamB = findViewById(R.id.spinnerTeamBt);
        teamA.setAdapter(adapter1);
        teamB.setAdapter(adapter1);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MatchActivity.class);
                intent.putExtra("TeamA", String.valueOf(teamA));
                intent.putExtra("TeamB",String.valueOf(teamB));
                intent.putExtra("TeamAScore",String.valueOf(teamAScore));
                intent.putExtra("TeamBScore",String.valueOf(teamBScore));
                startActivity(intent);
            }
        });

    }
}