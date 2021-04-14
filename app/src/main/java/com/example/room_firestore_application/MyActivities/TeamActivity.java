package com.example.room_firestore_application.MyActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.room_firestore_application.Local_Tables.Team;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

public class TeamActivity extends AppCompatActivity {

    private EditText sportID, birth_year,
    name, stadium, country, city;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        setComponents();
        if (getIntent().hasExtra("object"))
            setEditAction();
        else
            setInsertAction();
    }

    private void setComponents() {
        sportID = findViewById(R.id.team_sport_id);
        name = findViewById(R.id.team_name);
        stadium = findViewById(R.id.team_stadium);
        country = findViewById(R.id.team_country);
        city = findViewById(R.id.team_city);
        birth_year = findViewById(R.id.team_birth_year);
        button = findViewById(R.id.team_button);
    }

    private void setInsertAction() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sid = Integer.parseInt(sportID.getText().toString());
                String team_name = name.getText().toString();
                String team_stadium = stadium.getText().toString();
                String team_country = country.getText().toString();
                String team_city = city.getText().toString();
                int team_birth_year = Integer.parseInt(birth_year.getText().toString());

                Team team = new Team(
                    sid,
                    team_name,
                    team_stadium,
                    team_country,
                    team_city,
                    team_birth_year
                );

                MainActivity.localDatabase.basicDao().insert(team);
                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                resetForm();
            }

            private void resetForm() {
                sportID.setText("");
                name.setText("");
                stadium.setText("");
                country.setText("");
                city.setText("");
                birth_year.setText("");
            }
        });
    }

    private void setEditAction() {
        Team team = getIntent().getParcelableExtra("object");
        loadObjectToForm(team);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int teamSportId = Integer.parseInt(sportID.getText().toString());
                String teamName = name.getText().toString();
                String teamStadium = stadium.getText().toString();
                String teamCountry = country.getText().toString();
                String teamCity = city.getText().toString();
                int teamBirthYear = Integer.parseInt(birth_year.getText().toString());

                team.setId(teamSportId);
                team.setName(teamName);
                team.setStadium(teamStadium);
                team.setCountry(teamCountry);
                team.setCity(teamCity);
                team.setBirth_year(teamBirthYear);
                MainActivity.localDatabase.basicDao().update(team);
                finish();
            }
        });
    }

    private void loadObjectToForm(Team team) {
        sportID.setText(String.valueOf(team.getId()));
        name.setText(team.getName());
        stadium.setText(team.getStadium());
        country.setText(team.getCountry());
        city.setText(team.getCity());
        birth_year.setText(String.valueOf(team.getBirth_year()));
    }
}