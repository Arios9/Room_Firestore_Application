package com.example.room_firestore_application.MyFragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

import java.util.ArrayList;
import java.util.List;

public class MatchIndividualFragment extends Fragment {

    Spinner sItems,sIds;
    String sport;
    LinearLayout ll;
    EditText editTextScore,editTextId;
    Button buttonClear, buttonSetAthlete;
    TextView tvTester;


    OnDataPassIndi dataPasser;

    public interface OnDataPassIndi{
        public void onDataPassIndi(String data,String dataB, String dataC);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dataPasser = (MatchIndividualFragment.OnDataPassIndi) context;
    }
    public static MatchIndividualFragment newInstance(String sport) {

        MatchIndividualFragment frag = new MatchIndividualFragment();
        Bundle args = new Bundle();
        args.putString("sport", sport);
        frag.setArguments(args);
        return frag;
    }

    public MatchIndividualFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_individual, container, false);

        Bundle args = getArguments();
        sport = args.getString("sport");

        ll = (LinearLayout) view.findViewById(R.id.myLinearLayout);

        List<String> athleteIds = new ArrayList<>();
        athleteIds.add("A");
        athleteIds.add("B");
        athleteIds.add("C");
        athleteIds.add("D");
        athleteIds.add("E");
        athleteIds.add("F");
        athleteIds.add("G");
        athleteIds.add("H");
        List<String> namesOfAthletes = new ArrayList<>();
        namesOfAthletes= MainActivity.localDatabase.basicDao().getAthleteBySport(sport);

        ArrayAdapter<String> adapterAthletesSport = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, namesOfAthletes);
        ArrayAdapter<String> adapterAthletesID = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, athleteIds);



                    editTextScore = view.findViewById(R.id.editTextAthleteScore);
                   // editTextId = view.findViewById(R.id.editTextAthleteID);
                    sIds = view.findViewById(R.id.spinnerAthleteID);
                    sIds.setAdapter(adapterAthletesID);
                    sItems = view.findViewById(R.id.spinner2);
                    sItems.setAdapter(adapterAthletesSport);
                    buttonSetAthlete = new Button(getActivity());
                    buttonSetAthlete.setText("Add Result");
                    buttonSetAthlete.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    buttonSetAthlete.setTextColor(getResources().getColor(R.color.white));
                    ll.addView(buttonSetAthlete);
                    buttonSetAthlete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (editTextScore.getText().toString().isEmpty()){
                                Toast.makeText(getContext(),"Score cannot be left Empty",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                passData(String.valueOf(sItems.getSelectedItem()), String.valueOf(editTextScore.getText()), String.valueOf(sIds.getSelectedItem()));
                            }
                        }
                    });

                    buttonClear = new Button(getActivity());
                    buttonClear.setText("Clear");
                    buttonClear.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    buttonClear.setTextColor(getResources().getColor(R.color.white));
                    ll.addView(buttonClear);
                    buttonClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetForm();
                    }});


//
//        adapterAthletesSport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sAthletes = view.findViewById(R.id.spinnerAthleteNameA);
//        sAthletes.setAdapter(adapterAthletesSport);

        return view;
    }

    public void passData(String dataA, String dataB, String dataC){
        dataPasser.onDataPassIndi(dataA,dataB,dataC);
    }
    private void resetForm() {
        editTextScore.setText("");
        editTextId.setText("");
    }
}