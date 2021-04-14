package com.example.room_firestore_application.MyActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.room_firestore_application.Local_Tables.Sport;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

public class SportActivity extends AppCompatActivity {

    private EditText name, gender;
    private Button button;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);
        setComponents();
        if (getIntent().hasExtra("object"))
            setEditAction();
        else
            setInsertAction();
    }


    private void setComponents() {
        name = findViewById(R.id.sport_name);
        gender = findViewById(R.id.gender);
        radioGroup = findViewById(R.id.radio_type);
        button = findViewById(R.id.sport_button);
    }

    private void setInsertAction() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sportName = name.getText().toString();
                String sportType = getRadioText().toString();
                String sportGender = gender.getText().toString();
                Sport sport = new Sport(sportName,sportType,sportGender);
                MainActivity.localDatabase.basicDao().insert(sport);
                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                resetForm();
            }

            private void resetForm() {
                name.setText("");
                gender.setText("");
                radioGroup.clearCheck();
            }
        });
    }

    private void loadObjectToForm(Sport sport) {
        name.setText(sport.getName());
        gender.setText(sport.getGender());
        if(sport.getIndividual().equals("Individual"))
            radioGroup.check(R.id.radioIndividual);
        if(sport.getIndividual().equals("Team"))
            radioGroup.check(R.id.radioTeam);
    }

    private void setEditAction() {
        Sport sport = getIntent().getParcelableExtra("object");
        loadObjectToForm(sport);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sportName = name.getText().toString();
                String sportType = getRadioText().toString();
                String sportGender = gender.getText().toString();

                sport.setName(sportName);
                sport.setIndividual(sportType);
                sport.setGender(sportGender);
                MainActivity.localDatabase.basicDao().update(sport);
                finish();
            }
        });
    }


    private Object getRadioText() {
        int id = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(id);
        return radioButton.getText();
    }
}