package com.example.room_firestore_application.MyActivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.helpClasses.DatePickerFragment;
import com.example.room_firestore_application.MyFragments.MatchIndividualFragment;
import com.example.room_firestore_application.MyFragments.MatchTeamFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;
import com.hbb20.CountryCodePicker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchActivity extends AppCompatActivity implements MatchTeamFragment.OnDataPass, MatchIndividualFragment.OnDataPassIndi {

    MatchTeamFragment fragmentTeam = new MatchTeamFragment();
    MatchIndividualFragment fragmentIndividual = new MatchIndividualFragment();
    FragmentTransaction ft;

    //Variables to contain data passed from the fragment;
    static String teamA,teamB,scoreTeamA,scoreTeamB;

    static String athleteA,scoreAthlete, athleteId;

    private EditText matchID, matchCity;
    private CountryCodePicker matchCountry;
    public static EditText matchDate;
    private Spinner sItems;
    private Button buttonToResults, buttonSubmit, buttonClear;
    private ImageButton imageButton;
    private GeoPoint geoPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);


        setComponents();
        setCheckSportTypeAction();

        setSubmitAction();

        setClearAction();
        setDatePicker();
        setmapTextViewAction();
        //an to activity exei anoiksei gia edit

        if (getIntent().hasExtra("id")){

            String sid = getIntent().getStringExtra("id");
            String city = getIntent().getStringExtra("city");
            String date = getIntent().getStringExtra("date");

            if(getIntent().hasExtra("latitude")&&getIntent().hasExtra("longitude")){
                double latitude = getIntent().getDoubleExtra("latitude",0);
                double longitude = getIntent().getDoubleExtra("longitude",0);
                geoPoint = new GeoPoint(latitude,longitude);
            }

            matchID.setText(sid);
            matchDate.setText(date);
            matchCity.setText(city);
        }

    }

    private void setComponents() {

        //Fetching list of sports and teams from Room Local Database
        List<Sport> sport = MainActivity.localDatabase.basicDao().getSport();
        List<String> spinnerArray = new ArrayList<String>();


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
        buttonClear = findViewById(R.id.buttonClear);
        imageButton = findViewById(R.id.imageButton);

    }
    private void setCheckSportTypeAction() {
        buttonToResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Check the sport type (individual/team)
                String sportType = MainActivity.localDatabase.basicDao().getSportType(String.valueOf(sItems.getSelectedItem()));

                //Switch to MatchActivityTeam or MatchActivityIndividual depending on the Sport
                if(sportType.equals("Team")){
                    openFragment(fragmentTeam);
                }else{
                    openFragment(fragmentIndividual);
                }
            }

            private void openFragment(Fragment fragment) {
                Bundle bundle = new Bundle();
                String sport = String.valueOf(sItems.getSelectedItem());
                bundle.putString("sport", sport);

                fragment.setArguments(bundle);
                ft = getSupportFragmentManager().beginTransaction().replace(R.id.sportTypeContainer, fragment, null);
                ft.detach(fragment);
                ft.attach(fragment);
                ft.commit();
                ft.addToBackStack(null);
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

        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(matchID.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(),"Match ID cannot be Empty",Toast.LENGTH_SHORT).show();
                    resetForm();
                }
                else {
                    String match_id = matchID.getText().toString();
                    String match_sport = String.valueOf(sItems.getSelectedItem());
                    String match_date = matchDate.getText().toString();
                    String match_city = matchCity.getText().toString();
                    String match_country = matchCountry.getSelectedCountryName();

                    String sportType = MainActivity.localDatabase.basicDao().getSportType(match_sport);

                    Map<String, Object> match = new HashMap<>();
                    match.put("ID" , match_id);
                    match.put("City", match_city);
                    match.put("Country", match_country);
                    match.put("Date", match_date);
                    match.put("Sport", match_sport);
                    match.put("SportType", sportType);
                    //--------------
                    match.put("location", geoPoint);

                    Map<String, Object> results = new HashMap<>();
                    if(sportType.equals("Team")) {
                        results.put("Team A", teamA);
                        results.put("Team A Score", scoreTeamA);
                        results.put("Team B", teamB);
                        results.put("Team B Score", scoreTeamB);
                    }
                    else{
                        results.put("Athlete" , athleteA);
                        results.put("Score Athlete", scoreAthlete);
                    }

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference theResultsCollection = db.collection("Matches")
                            .document("" + match_id).collection("Results");

                    if(sportType.equals("Team")) {
                        theResultsCollection.document(match_id + " results").set(results);
                    }
                    else {
                        theResultsCollection.document(match_id +  " " + athleteId + " results").set(results);
                    }
                    db.collection("Matches").document("" + match_id).set(match);

                    Toast.makeText(getApplicationContext(), "Submit", Toast.LENGTH_SHORT).show();
                    resetForm();


                    MainActivity.CurrentFragment.createList();

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
        if (fragmentTeam.isVisible()) {
            ft = getSupportFragmentManager().beginTransaction().remove(fragmentTeam);
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
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForm();
            }
        });
    }

    private void setDatePicker() {
        matchDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            }
        });
    }
    
    private static final int REQUEST_CODE = 0;

    private void setmapTextViewAction() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchActivity.this, InputMapsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            geoPoint = new GeoPoint(latitude,longitude);
            Toast.makeText(getApplicationContext(),"Location selected",Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),geoPoint.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}