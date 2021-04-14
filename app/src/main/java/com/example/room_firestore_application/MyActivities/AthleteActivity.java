package com.example.room_firestore_application.MyActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

public class AthleteActivity extends AppCompatActivity {

    private EditText sportID, birth_year,
    name, surname, country, city;
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
        sportID = findViewById(R.id.athlete_sport_id);
        name = findViewById(R.id.athlete_name);
        surname = findViewById(R.id.athlete_surname);
        country = findViewById(R.id.athlete_country);
        city = findViewById(R.id.athlete_city);
        birth_year = findViewById(R.id.athlete_birth_year);
        button = findViewById(R.id.athlete_button);
    }

    private void setInsertAction() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sid = Integer.parseInt(sportID.getText().toString());
                String athlete_name = name.getText().toString();
                String athlete_surname = surname.getText().toString();
                String athlete_country = country.getText().toString();
                String athlete_city = city.getText().toString();
                int athlete_birth_year = Integer.parseInt(birth_year.getText().toString());

                Athlete athlete = new Athlete(
                    sid,
                    athlete_name,
                    athlete_surname,
                    athlete_country,
                    athlete_city,
                    athlete_birth_year
                );
                MainActivity.localDatabase.basicDao().insert(athlete);
                Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_SHORT).show();
                resetForm();
            }

            private void resetForm() {
                sportID.setText("");
                name.setText("");
                surname.setText("");
                country.setText("");
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
                int athleteSportId = Integer.parseInt(sportID.getText().toString());
                String athleteName = name.getText().toString();
                String athleteSurname = surname.getText().toString();
                String athleteCountry = country.getText().toString();
                String athleteCity = city.getText().toString();
                int athleteBirthYear = Integer.parseInt(birth_year.getText().toString());

                athlete.setId(athleteSportId);
                athlete.setName(athleteName);
                athlete.setSurname(athleteSurname);
                athlete.setCountry(athleteCountry);
                athlete.setCity(athleteCity);
                athlete.setBirth_year(athleteBirthYear);
                MainActivity.localDatabase.basicDao().update(athlete);
                finish();
            }
        });
    }

    private void loadObjectToForm(Athlete athlete) {
        sportID.setText(String.valueOf(athlete.getId()));
        name.setText(athlete.getName());
        surname.setText(athlete.getSurname());
        country.setText(athlete.getCountry());
        city.setText(athlete.getCity());
        birth_year.setText(String.valueOf(athlete.getBirth_year()));
    }
}
