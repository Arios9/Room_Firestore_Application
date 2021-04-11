package com.example.room_firestore_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.room_firestore_application.MyActivities.AthleteActivity;
import com.example.room_firestore_application.MyActivities.MatchActivity;
import com.example.room_firestore_application.MyActivities.SportActivity;
import com.example.room_firestore_application.MyActivities.TeamActivity;
import com.example.room_firestore_application.ui.athlete.AthleteFragment;
import com.example.room_firestore_application.ui.match.MatchFragment;
import com.example.room_firestore_application.ui.sport.SportFragment;
import com.example.room_firestore_application.ui.team.TeamFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static Fragment CurrentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRightActivity();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_sport, R.id.nav_athlete, R.id.nav_team, R.id.nav_match)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void openRightActivity() {
        Class currentClass = null;

        if (CurrentFragment instanceof SportFragment)
            currentClass= SportActivity.class;
        else if (CurrentFragment instanceof AthleteFragment)
            currentClass= AthleteActivity.class;
        else if (CurrentFragment instanceof TeamFragment)
            currentClass= TeamActivity.class;
        else if(CurrentFragment instanceof MatchFragment)
            currentClass= MatchActivity.class;

        Intent intent = new Intent(this, currentClass);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}