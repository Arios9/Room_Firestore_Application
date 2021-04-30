package com.example.room_firestore_application.ui.SubcollectionFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.room_firestore_application.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class IndiMatchFragment extends Fragment {

    TextView textAnnounce , textAnnounceScore;
    ListView listView;
    List<String> list = new ArrayList<String>();
    List<String> listMax = new ArrayList<String>();
    CollectionReference cref;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String tag;
    public IndiMatchFragment() {
        // Required empty public constructor
    }

    public static IndiMatchFragment newInstance(String tag) {
        IndiMatchFragment fragment = new IndiMatchFragment();
        Bundle args = new Bundle();
        args.putString("ID", tag);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indi_match, container, false);
        Bundle args = getArguments();
        tag = args.getString("ID");



        listView = view.findViewById(R.id.lvIndiAthlete);
        textAnnounce = view.findViewById(R.id.textAnn);
        textAnnounceScore = view.findViewById(R.id.textAnnScore);
        cref = db.collection("Matches")
                .document(""+tag).collection("Results");


        cref.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        double max = 0;
                        String maxAthlete = "";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String athlete = document.getString("Athlete");
                                String athleteScore = document.getString("Score Athlete");

                                list.add(" Athlete : " + athlete
                                        + "\n Score : " + athleteScore);

                                if (getContext() != null) {
                                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
                                    listView.setAdapter(adapter);
                                }
                                if (athleteScore != null) {
                                    if (Double.parseDouble(athleteScore) > max) {
                                        max = Double.parseDouble(athleteScore);
                                        maxAthlete = athlete;
                                        //  listMax.add(athlete);
                                    }

                                    textAnnounce.setText(" " + maxAthlete);
                                    textAnnounceScore.setText(" " + max);

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