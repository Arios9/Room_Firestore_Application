package com.example.room_firestore_application.ui.SubcollectionFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.room_firestore_application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TeamMatchFragment extends Fragment {

    TextView tvTeamA,tvTeamB,tvResult,tvScoreA,tvScoreB,tvName, tvDate, tvCity, tvCountry, tvRes;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
   // DocumentReference dref;
    CollectionReference cref;

    String tag ,city,country,date, name;

    public TeamMatchFragment() {
        // Required empty public constructor
    }

    public static TeamMatchFragment newInstance(String tag, String name, String date, String city, String country) {
        TeamMatchFragment fragment = new TeamMatchFragment();
        Bundle args = new Bundle();
        args.putString("ID", tag);
        args.putString("Name", name);
        args.putString("Date", date);
        args.putString("City",city);
        args.putString("Country", country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_team_match, container, false);

        tvTeamA = view.findViewById(R.id.tvTeamAName);
        tvTeamB = view.findViewById(R.id.tvTeamBName);
        tvResult = view.findViewById(R.id.tvResult);
        tvScoreA = view.findViewById(R.id.tvTeamAScore);
        tvScoreB = view.findViewById(R.id.tvTeamBScore);

        tvName = view.findViewById(R.id.textSportName);
        tvDate = view.findViewById(R.id.textDate);
        tvCity = view.findViewById(R.id.textCity);
        tvCountry = view.findViewById(R.id.textCountry);
        tvRes = view.findViewById(R.id.textViewWon);


        Bundle args = getArguments();
        tag = args.getString("ID");
        city = args.getString("City");
        country = args.getString("Country");
        date = args.getString("Date");
        name = args.getString("Name");


        tvName.setText(name);
        tvDate.setText(date);
        tvCity.setText(city);
        tvCountry.setText(country);
        cref = db.collection("Matches")
                .document(""+tag).collection("Results");


        cref.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String teamA = document.getString("Team A");
                                String teamB = document.getString("Team B");
                                String teamAscore = document.getString("Team A Score");
                                String teamBscore = document.getString("Team B Score");

                                String wholeTeamA =teamA;
                                String wholeTeamB =teamB;
                                if(teamA.length()>4){
                                    teamA = teamA.substring(0,4);
                                    teamA = teamA.toUpperCase();
                                }
                                if(teamB.length()>4){
                                    teamB = teamB.substring(0,4);
                                    teamB = teamB.toUpperCase();
                                }
                                tvTeamA.setText(teamA);
                                tvTeamB.setText(teamB);
                                tvScoreA.setText(teamAscore);
                                tvScoreB.setText(teamBscore);


                                if (getContext() != null) {
                                    
                                    if (Integer.parseInt(teamAscore) > Integer.parseInt(teamBscore)) {
                                        tvScoreA.setTextColor(getResources().getColor(R.color.green));
                                        tvScoreB.setTextColor(getResources().getColor(R.color.red));
                                        tvResult.setText(wholeTeamA);
                                        tvRes.setText("Won");
                                    } else if(Integer.parseInt(teamBscore) > Integer.parseInt(teamAscore)){
                                        tvScoreB.setTextColor(getResources().getColor(R.color.green));
                                        tvScoreA.setTextColor(getResources().getColor(R.color.red));
                                        tvResult.setText(wholeTeamB);
                                        tvRes.setText("Won");
                                    }
                                    else{
                                        tvScoreA.setTextColor(getResources().getColor(R.color.orange));
                                        tvScoreB.setTextColor(getResources().getColor(R.color.orange));
                                        tvResult.setText("");
                                        tvRes.setText("Match Draw");
                                    }
                                }
                            }
                        }
                        else{
                            Toast.makeText(getActivity(), "Document doesnt Exist", Toast.LENGTH_LONG).show();
                        }
                    }
                });


        return view;
    }
}