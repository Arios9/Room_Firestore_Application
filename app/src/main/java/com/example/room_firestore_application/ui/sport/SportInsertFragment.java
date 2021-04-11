package com.example.room_firestore_application.ui.sport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

public class SportInsertFragment extends Fragment {

    EditText editTextID, editTextName;
    RadioGroup radioGroup;
    Switch aSwitch;
    Button buttonInsert;


    public SportInsertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sport_insert, container, false);
        editTextID = view.findViewById(R.id.sportID);
        editTextName = view.findViewById(R.id.sportName);
        aSwitch = view.findViewById(R.id.switchIndividual);
        radioGroup = view.findViewById(R.id.radioGroup);
        buttonInsert = view.findViewById(R.id.btnIns);
        buttonInsert.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                int var_sportid = 0;
                try{
                    var_sportid = Integer.parseInt(editTextID.getText().toString());
                }
                catch (NumberFormatException ex){
                    System.out.println("Could not parse...");
                }



                String var_name = editTextName.getText().toString();
                Boolean switchResult = aSwitch.isChecked();
                String var_gender = String.valueOf(radioGroup.getCheckedRadioButtonId());

                Sport sport = new Sport();
                sport.setId(var_sportid);
                sport.setName(var_name);
                sport.setGender(var_gender);
                sport.setIndividual(switchResult);

                MainActivity.localDatabase.basicDao().insert(sport);
                Toast.makeText(getActivity(),"Sport Created", Toast.LENGTH_LONG).show();
                editTextID.setText("");
                editTextName.setText("");
                aSwitch.setChecked(false);
                radioGroup.clearCheck();
            }
        });
        return view;
    }
}