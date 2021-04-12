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
        setButtonListener();
    }

    private void setComponents() {
        name = findViewById(R.id.sport_name);
        gender = findViewById(R.id.gender);
        radioGroup = findViewById(R.id.radio_type);
        button = findViewById(R.id.sport_button);
    }

    private void setButtonListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sportName = name.getText().toString();
                String sportType = getRadioText().toString();
                String sportGender = gender.getText().toString();

                Sport sport = new Sport(sportName,sportType,sportGender);
                MainActivity.localDatabase.basicDao().insert(sport);
                Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_SHORT).show();
                resetForm();
            }

            private Object getRadioText() {
                int id = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(id);
                return radioButton.getText();
            }

            private void resetForm() {
                name.setText("");
                gender.setText("");
                radioGroup.clearCheck();
            }
        });
    }
}