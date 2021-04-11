package com.example.room_firestore_application.ui.athlete;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;


public class AthleteInsertFragment extends Fragment {

    EditText editTextID,editTextName,editTextSurname,editTextCountry,editTextCity,
                editTextDoB;
    Button buttonInsert;

    public AthleteInsertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_athlete_insert, container, false);
        editTextID = view.findViewById(R.id.sportID);
        editTextName = view.findViewById(R.id.athleteName);
        editTextSurname = view.findViewById(R.id.athleteSurname);
        editTextCountry = view.findViewById(R.id.athleteCountry);
        editTextCity = view.findViewById(R.id.athleteCity);
        editTextDoB = view.findViewById(R.id.athleteDoB);

        buttonInsert = view.findViewById(R.id.btnIns);
        buttonInsert.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                int var_userid = 0;
                try{
                    var_userid = Integer.parseInt(editTextID.getText().toString());
                }
                catch (NumberFormatException ex){
                    System.out.println("Could not parse...");
                }

                int var_yob = 0;
                try{
                    var_yob = Integer.parseInt(editTextDoB.getText().toString());
                }
                catch (NumberFormatException ex){
                    System.out.println("Could not parse...");
                }

                String var_name = editTextName.getText().toString();
                String var_surname = editTextSurname.getText().toString();
                String var_country = editTextCountry.getText().toString();
                String var_city = editTextCity.getText().toString();
                String var_dob = editTextDoB.getText().toString();

                Athlete athlete = new Athlete();
                athlete.setSport_id(var_userid);
                athlete.setName(var_name);
                athlete.setSurname(var_surname);
                athlete.setCountry(var_country);
                athlete.setCity(var_city);
                athlete.setBirth_year(var_yob);
                MainActivity.localDatabase.basicDao().insert(athlete);
                Toast.makeText(getActivity(),"Athlete Created", Toast.LENGTH_LONG).show();
                editTextDoB.setText("");
                editTextCity.setText("");
                editTextCountry.setText("");
                editTextID.setText("");
                editTextName.setText("");
                editTextSurname.setText("");
            }
        });
        return view;
    }
}