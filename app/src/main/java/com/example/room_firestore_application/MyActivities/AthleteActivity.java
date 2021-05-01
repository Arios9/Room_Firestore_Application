package com.example.room_firestore_application.MyActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.MyFragments.ListFragments.AthleteFragment;
import com.hbb20.CountryCodePicker;

import java.util.List;

public class AthleteActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText birth_year, name, surname, city;
    private CountryCodePicker country;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete);
        setComponents();
        if (getIntent().hasExtra("object"))
            setEditAction();
        else
            setInsertAction();
    }

    private void setComponents() {
        spinner = findViewById(R.id.athlete_sport_id);
        setSpinner();
        name = findViewById(R.id.athlete_name);
        surname = findViewById(R.id.athlete_surname);
        country = findViewById(R.id.athlete_country);
        city = findViewById(R.id.athlete_city);
        birth_year = findViewById(R.id.athlete_birth_year);
        button = findViewById(R.id.athlete_button);
    }

    private void setSpinner() {
        List<Sport> list = MainActivity.localDatabase.basicDao().getIndividualSports();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }


    private void setInsertAction() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int athleteSportId = ((Sport) spinner.getSelectedItem()).getId();
                String athlete_name = name.getText().toString();
                String athlete_surname = surname.getText().toString();
                String athlete_country = country.getSelectedCountryName();
                String athlete_city = city.getText().toString();
                int athlete_birth_year = Integer.parseInt(birth_year.getText().toString());

                Athlete athlete = new Athlete(
                    athleteSportId,
                    athlete_name,
                    athlete_surname,
                    athlete_country,
                    athlete_city,
                    athlete_birth_year
                );
                MainActivity.localDatabase.basicDao().insert(athlete);
                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                resetForm();
                ((AthleteFragment)MainActivity.CurrentFragment).createList();
            }

            private void resetForm() {
                spinner.setSelection(0);
                name.setText("");
                surname.setText("");
                city.setText("");
                birth_year.setText("");
            }
        });
    }

    private void setEditAction() {
        Athlete athlete = getIntent().getParcelableExtra("object");
        loadObjectToForm(athlete);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int athleteSportId = ((Sport) spinner.getSelectedItem()).getId();
                String athleteName = name.getText().toString();
                String athleteSurname = surname.getText().toString();
                String athleteCountry = country.getSelectedCountryName();
                String athleteCity = city.getText().toString();
                int athleteBirthYear = Integer.parseInt(birth_year.getText().toString());

                athlete.setSport_id(athleteSportId);
                athlete.setName(athleteName);
                athlete.setSurname(athleteSurname);
                athlete.setCountry(athleteCountry);
                athlete.setCity(athleteCity);
                athlete.setBirth_year(athleteBirthYear);
                MainActivity.localDatabase.basicDao().update(athlete);

                ((AthleteFragment)MainActivity.CurrentFragment).createList();
                finish();
            }
        });
    }

    private void loadObjectToForm(Athlete athlete) {
        name.setText(athlete.getName());
        surname.setText(athlete.getSurname());
        city.setText(athlete.getCity());
        birth_year.setText(String.valueOf(athlete.getBirth_year()));
    }
}
