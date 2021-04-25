package com.example.room_firestore_application.MyActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.Local_Tables.Team;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.ui.AthleteFragment;
import com.example.room_firestore_application.ui.MatchFragment;
import com.example.room_firestore_application.ui.MatchIndividualFragment;
import com.example.room_firestore_application.ui.MatchTeamFragment;
import com.example.room_firestore_application.ui.SportFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchActivity extends AppCompatActivity implements MatchTeamFragment.OnDataPass, MatchIndividualFragment.OnDataPassIndi {

    MatchTeamFragment fragment = new MatchTeamFragment();
    MatchIndividualFragment fragmentIndividual = new MatchIndividualFragment();
    FragmentTransaction ft, ft1;

    //Variables to contain data passed from the fragment;
    static String teamA,teamB,scoreTeamA,scoreTeamB;

    static String athleteA,scoreAthlete, athleteId;


    private EditText matchID,matchDate, matchCity, matchCountry, scoreA, scoreB;
    private Spinner sItems;
    private Button buttonToResults, buttonSubmit, buttonClear;
    private TextView implemented;



    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        setComponents();
        setCheckSportTypeAction();

        setSubmitAction();

        setClearAction();

        //an to activity exei anoiksei gia edit
        if (getIntent().hasExtra("id")){

            String sid = getIntent().getStringExtra("id");
            String city = getIntent().getStringExtra("city");
            String country = getIntent().getStringExtra("country");
            String date = getIntent().getStringExtra("date");

            matchID.setText(sid);
            matchDate.setText(date);
            matchCity.setText(city);
            matchCountry.setText(country);
        }

    }


    private void setComponents() {

        //Fetching list of sports and teams from Room Local Database
        List<Sport> sport = MainActivity.localDatabase.basicDao().getSport();
        List<String> spinnerArray = new ArrayList<String>();
//        List<Team> teams = MainActivity.localDatabase.basicDao().getTeam();
//        List<String> spinnerArrayTeams = new ArrayList<String>();

        //Giving the names of the sports to the newly created String Array for the Spinner
        for (Sport i: sport) {
            String name = i.getName();
            spinnerArray.add(name);
        }


        //Giving the String list that contains the Sports' Names to the Spinner Dropdown Selector
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);


        //Adjusting the adapters
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = findViewById(R.id.spinnerSports);
        sItems.setAdapter(adapter);


        matchID = findViewById(R.id.matchID);
        matchDate = findViewById(R.id.matchDate);
        matchCity = findViewById(R.id.matchCity);
        matchCountry = findViewById(R.id.matchCountry);
        buttonSubmit = findViewById(R.id.match_button);
        buttonToResults = findViewById(R.id.match_buttonToResults);

    }

    private void setCheckSportTypeAction() {
        buttonToResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check the sport type (individual/team)
                String sportType = MainActivity.localDatabase.basicDao().getSportType(String.valueOf(sItems.getSelectedItem()));

                //Switch to MatchActivityTeam or MatchActivityIndividual depending on the Sport

                if(sportType.equals("Team")){

                    Bundle bundle = new Bundle();
                    String sport = String.valueOf(sItems.getSelectedItem());
                    bundle.putString("sport", sport);

                    fragment.setArguments(bundle);
                    ft = getSupportFragmentManager().beginTransaction().replace(R.id.sportTypeContainer, fragment, null);
                    //ft.commit();
                    ft.detach(fragment);
                    ft.attach(fragment);
                    ft.commit();
                    ft.addToBackStack(null);
                }

                else{
                    Bundle bundle = new Bundle();
                    String sport = String.valueOf(sItems.getSelectedItem());
                    bundle.putString("sport", sport);

                    fragmentIndividual.setArguments(bundle);
                    ft = getSupportFragmentManager().beginTransaction().replace(R.id.sportTypeContainer,fragmentIndividual,null);
                    //ft.commit();
                    ft.detach(fragmentIndividual);
                    ft.attach(fragmentIndividual);
                    ft.commit();
                    ft.addToBackStack(null);
                }
            }

        });
    }


    @Override
    public void onDataPass(String teamAi, String teamBi, String scoreteamA, String scoreteamB){
        scoreTeamA = scoreteamA;
        scoreTeamB = scoreteamB;
        teamA = teamAi;
        teamB = teamBi;
    }
    @Override
    public void onDataPassIndi(String AthleteA, String scoreAthleteA, String athleteID){
        athleteA = AthleteA;
        scoreAthlete = scoreAthleteA;
        athleteId = athleteID;
    }



    private void setSubmitAction(){
//        if(teamA.equals("1")){
//            buttonSubmit.setVisibility(View.VISIBLE);
//        }
        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(matchID.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(),"Match ID cannot be Empty",Toast.LENGTH_SHORT).show();
                    resetForm();
                }
                else {
//                String sportType = MainActivity.localDatabase.basicDao().getSportType(String.valueOf(sItems.getSelectedItem()));

                    String match_id = matchID.getText().toString();
                    String match_sport = String.valueOf(sItems.getSelectedItem());
                    String match_date = matchDate.getText().toString();
                    String match_city = matchCity.getText().toString();
                    String match_country = matchCountry.getText().toString();
//                String team_a_score = scoreA.getText().toString();
//                String team_b_score = scoreB.getText().toString();
//                String team_a = String.valueOf(sTeamA.getSelectedItem());
//                String team_b = String.valueOf(sTeamB.getSelectedItem());

                    Map<String, Object> match = new HashMap<>();
                    match.put("ID" , match_id);
                    match.put("City", match_city);
                    match.put("Country", match_country);
                    match.put("Date", match_date);
                    match.put("Sport", match_sport);

                    String sportType = MainActivity.localDatabase.basicDao().getSportType(match_sport);//ne  mallon

                    match.put("SportType", sportType);

                    String identification="";

                    Map<String, Object> results = new HashMap<>();
                    if(sportType.equals("Team")) {
                        identification = teamA.concat(" Match");
                        results.put("Team A", teamA);
                        results.put("Team A Score", scoreTeamA);
                        results.put("Team B", teamB);
                        results.put("Team B Score", scoreTeamB);
                    }
                    else{
                        results.put("Athlete" , athleteA);
                        results.put("Score Athlete", scoreAthlete);
                    }

                    CollectionReference theResultsCollection = db.collection("Matches")
                            .document("" + match_id).collection("Results");

//                    db.collection("Matches")
//                            .document("" + match_id).collection("Results").document(match_id + " results").set(results);

                       // theResultsCollection.add(results);
                    if(sportType.equals("Team")) {
                        theResultsCollection.document(match_id + " " + identification + " results").set(results);
                    }
                    else {
                        theResultsCollection.document(match_id + " " + athleteId + " results").set(results);
                    }
                    db.collection("Matches").document("" + match_id).set(match);
//                db.collection("Matches").add(match).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(getApplicationContext(), "Match Added", Toast.LENGTH_LONG).show();
//                    }
//                })
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(getApplicationContext(), "Match Added", Toast.LENGTH_LONG).show();
//                            }
//
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(getApplicationContext(), "Failure to add Match", Toast.LENGTH_LONG).show();
//                            }
//                        });

                    Toast.makeText(getApplicationContext(), "Submit", Toast.LENGTH_SHORT).show();
                    if(match_sport=="Team")
                    resetForm();


                    ((MatchFragment)MainActivity.CurrentFragment).createList();

                    //an to activity exei anoiksei gia edit kleinei meta to submit
                    if (getIntent().hasExtra("id")){
                        finish();
                    }

                }

            }



        });

    }
    private void resetForm() {
        matchID.setText("");
        matchDate.setText("");
        matchCity.setText("");
        matchCountry.setText("");
        if (fragment.isVisible()) {
            ft = getSupportFragmentManager().beginTransaction().remove(fragment);
            ft.commit();
            ft.addToBackStack(null);
        }
        if(fragmentIndividual.isVisible()) {
            ft = getSupportFragmentManager().beginTransaction().remove(fragmentIndividual);
            ft.commit();
            ft.addToBackStack(null);
        }
    }
    private void setClearAction(){
        buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForm();
            }
        });
    }
}