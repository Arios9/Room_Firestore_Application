package com.example.room_firestore_application.MyActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.room_firestore_application.Local_Tables.Athlete;
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
        setButtonListener();
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

    private void setButtonListener() {
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
}
