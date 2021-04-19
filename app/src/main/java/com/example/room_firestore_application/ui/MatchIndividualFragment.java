package com.example.room_firestore_application.ui;

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

import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

import java.util.ArrayList;
import java.util.List;

public class MatchIndividualFragment extends Fragment {

    Spinner sItems,sAthletes;
    String sport;
    LinearLayout ll;
    EditText editTextScore,editTextId;
    Button buttonClear, buttonSetAthlete;
    TextView tvTester;


    OnDataPass dataPasser;

    public interface OnDataPass{
        public void onDataPass(String data,String dataB, String dataC);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        dataPasser = (MatchIndividualFragment.OnDataPass) context;
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

//        List<String> list= new ArrayList<>();
//        list.add("Giannis");
//        list.add("Vaggelis");
//ArrayAdapter<String> testAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,list);


        List<String> namesOfAthletes = new ArrayList<>();
        namesOfAthletes= MainActivity.localDatabase.basicDao().getAthleteBySport(sport);

        ArrayAdapter<String> adapterAthletesSport = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, namesOfAthletes);


                    editTextScore = view.findViewById(R.id.editTextAthleteScore);
                    editTextId = view.findViewById(R.id.editTextAthleteID);
                    sItems = view.findViewById(R.id.spinner2);
                    sItems.setAdapter(adapterAthletesSport);
                    buttonSetAthlete = new Button(getActivity());
                    buttonSetAthlete.setText("Submit");
                    buttonSetAthlete.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    buttonSetAthlete.setTextColor(getResources().getColor(R.color.white));
                    ll.addView(buttonSetAthlete);
                    buttonSetAthlete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            passData(String.valueOf(sItems.getSelectedItem()),String.valueOf(editTextScore.getText()), String.valueOf(editTextId.getText()));
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
        dataPasser.onDataPass(dataA,dataB,dataC);
    }
    private void resetForm() {
        editTextScore.setText("");
        editTextId.setText("");
    }
}