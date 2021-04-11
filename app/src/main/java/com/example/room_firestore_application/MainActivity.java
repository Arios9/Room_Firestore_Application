package com.example.room_firestore_application;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.room_firestore_application.Adapter.CustomExpandableListAdapter;
import com.example.room_firestore_application.ui.athlete.AthleteFragment;
import com.example.room_firestore_application.ui.sport.SportFragment;
import com.example.room_firestore_application.ui.team.TeamFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    public static LocalDatabase localDatabase;
    // public static FragmentManager outerFragmentManager; //to use outside MainActivity

    private Button button;
    private Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    private String[] items;
    private List<String> lstTitle;
    private Map<String,List<String>> lstChild;
    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    public static FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        localDatabase = Room.databaseBuilder(getApplicationContext(), LocalDatabase.class, "localDB").allowMainThreadQueries().build();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        expandableListView = findViewById(R.id.navList); //initializing the expandable List view

        initItems();

        View listHeaderView = getLayoutInflater().inflate(R.layout.nav_header_main, null, false);
        expandableListView.addHeaderView(listHeaderView);

        genData();
        addDrawersItem();
        setupDrawer();
        //------------OLD CODE HERE------------
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_sport, R.id.nav_athlete, R.id.nav_team, R.id.nav_match)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
      //----------------------------------------



    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void initItems(){
        items = new String[]{"1.Sport", "2.Athlete", "3.Team"};
    }

    private void genData(){
        List<String> title = Arrays.asList("1.Sport", "2.Athlete", "3.Team");
        List<String> childSport = Arrays.asList("Football","Basketball","Handball","Volleyball");
        List<String> childAthlete = Arrays.asList("Male","Female","Other"); //Edw xtypaei an exei mono 2 items...
        List<String> childTeam = Arrays.asList("Football Teams","Basketball Teams", "Handball Teams");
        lstChild = new TreeMap<>();
        lstChild.put(title.get(0),childSport);
        lstChild.put(title.get(1),childAthlete);
        lstChild.put(title.get(2),childTeam);

        lstTitle = new ArrayList<>(lstChild.keySet());
    }

    private void addDrawersItem() {
        expandableListAdapter = new CustomExpandableListAdapter(this, lstTitle, lstChild);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(lstTitle.get(groupPosition).toString()); //Set Title For Toolbar when menu opens
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("fmavroud");
            }
        });


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Change fragment on item click

                fragmentManager = getSupportFragmentManager();
                String selectedItem = ((List)(lstChild.get(lstTitle.get(groupPosition))))
                        .get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);

                switch(selectedItem){
                    case "Football":
                    case "Basketball":
                    case "Handball":
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new SportFragment()).addToBackStack(null).commit();
                        SportFragment.textSettingTest(selectedItem +" Database Fragment");
                        break;
                    case "Male":
                    case "Female":
                    case "Other":
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new AthleteFragment()).addToBackStack(null).commit();
                        break;
                    case "Football Teams":
                    case "Basketball Teams":
                    case "Handball Teams":
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new TeamFragment()).addToBackStack(null).commit();
                        break;
                    default: throw new IllegalArgumentException("Not supported fragment");
                }

//                if (selectedItem.equals(getSupportActionBar().getTitle().toString())
//                        /*items[0].equals(lstTitle.get(childPosition))*/) {
//
//
//                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new SportFragment()).addToBackStack(null).commit();
//                    SportFragment.textSettingTest(selectedItem);
//                }
//                else if (selectedItem.equals(getSupportActionBar().getTitle().toString())
//                    /*items[0].equals(lstTitle.get(childPosition))*/){
//
//                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new AthleteFragment()).addToBackStack(null).commit();
//
//                }
//                else if (selectedItem.equals(getSupportActionBar().getTitle().toString())
//                    /*items[0].equals(lstTitle.get(childPosition))*/){
//
//                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new TeamFragment()).addToBackStack(null).commit();
//
//                }
//                else
//                    throw new IllegalArgumentException("Not supported fragment");

               drawer.closeDrawer(GravityCompat.START);
               return false;

          }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // getSupportActionBar().setTitle("fmavroud");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(mDrawerToggle);
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