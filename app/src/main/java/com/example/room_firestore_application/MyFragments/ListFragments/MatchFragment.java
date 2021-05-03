package com.example.room_firestore_application.MyFragments.ListFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.room_firestore_application.MyActivities.MatchActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.helpClasses.GeoPointArrayList;
import com.example.room_firestore_application.MyFragments.SubcollectionFragment.IndiMatchFragment;
import com.example.room_firestore_application.MyFragments.SubcollectionFragment.TeamMatchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.room_firestore_application.helpClasses.GeoPointArrayList.TodaysGeoPointArrayList;

public class MatchFragment extends ParentFragment {

    private final Class ActivityClass = MatchActivity.class;
    private final int FRAGMENT_ID = R.layout.fragment_match;
    private final int LIST_ID = R.id.match_list;

    CollectionReference collectionReference;

    Activity mainContext;

    List<String> list ;
    ArrayList<String> myIds ;
    ArrayList<String> sportTypeAr ;
    ArrayList<String> sportCityAr ;
    ArrayList<String> sportCountryAr ;
    ArrayList<String> sportDateAr ;
    ArrayList<String> sportNameAr ;
    ArrayList<GeoPoint> sportGeopointAr ;


    @Override
    public void createList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Matches");
            collectionReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                createNewArrayLists();

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    String city = document.getString("City");
                                    String country = document.getString("Country");
                                    String date = document.getString("Date");
                                    String sport = document.getString("Sport");
                                    String match_id = document.getString("ID");
                                    String match_sportType = document.getString("SportType");
                                    GeoPoint geoPoint = document.getGeoPoint("location");

                                    list.add("Match ID : " + match_id + "\n" + " City : " + city + "\n Country : " + country + "\n Date : " + date + "\n Sport : " + sport);
                                    myIds.add(match_id);
                                    sportCityAr.add(city);
                                    sportCountryAr.add(country);
                                    sportNameAr.add(sport);
                                    sportDateAr.add(date);
                                    sportTypeAr.add(match_sportType);
                                    sportGeopointAr.add(geoPoint);

                                    TodaysGeoPointArrayList.checkDateAndGeopoint(date, geoPoint);
                                }
                                TodaysGeoPointArrayList.checkForNotification(mainContext);

                                ArrayAdapter adapter = new ArrayAdapter(mainContext, android.R.layout.simple_list_item_1, list);
                                listView.setAdapter(adapter);


                            } else {
                                Toast.makeText(mainContext, "Document doesnt Exist", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        addOnClickListener();
    }

    private void createNewArrayLists() {
        list = new ArrayList<>();
        myIds = new ArrayList<>();
        sportTypeAr = new ArrayList<>();
        sportCityAr = new ArrayList<>();
        sportCountryAr = new ArrayList<>();
        sportDateAr = new ArrayList<>();
        sportNameAr = new ArrayList<>();
        sportGeopointAr = new ArrayList<>();
        TodaysGeoPointArrayList = new GeoPointArrayList();
    }

    private void addOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String myId = myIds.get(position);
                String sportType = sportTypeAr.get(position);
                String sportName = sportNameAr.get(position);
                String sportCity = sportCityAr.get(position);
                String sportCountry = sportCountryAr.get(position);
                String sportDate = sportDateAr.get(position);

                Bundle bundle = new Bundle();
                bundle.putString("ID", myId);

                if(sportType.equals("Team")) {
                    bundle.putString("City",sportCity);
                    bundle.putString("Country", sportCountry);
                    bundle.putString("Date", sportDate);
                    bundle.putString("Name", sportName);

                    Fragment teamMatchFrag = new TeamMatchFragment();
                    openFragment(teamMatchFrag, bundle);
                }
                else{
                    Fragment indiMatchFrag = new IndiMatchFragment();
                    openFragment(indiMatchFrag, bundle);
                }
            }
            private void openFragment(Fragment fragment, Bundle bundle) {
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, null);
                ft.detach(fragment);
                ft.attach(fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }


    @Override
    void EditAction() {
        int position = getItemPosition();
        String sid = myIds.get(position);
        String city = sportCityAr.get(position);
        String date = sportDateAr.get(position);
        GeoPoint geoPoint = sportGeopointAr.get(position);

        Intent intent = new Intent(getActivity(), MatchActivity.class);
        intent.putExtra("id",sid);
        intent.putExtra("date",date);
        intent.putExtra("city",city);
        if(geoPoint!=null){
            intent.putExtra("latitude",geoPoint.getLatitude());
            intent.putExtra("longitude",geoPoint.getLongitude());
        }
        startActivity(intent);
    }

    @Override
    void DeleteAction() {
        Toast.makeText(getActivity(), "Document Deleted", Toast.LENGTH_SHORT).show();
        int position = getItemPosition();
        String documentId = myIds.get(position);
        collectionReference.document(documentId).delete();
        createList();
    }

    @Override
    int getFragmentId() {
        return FRAGMENT_ID;
    }

    @Override
    int getListId() {
        return LIST_ID;
    }

    @Override
    public Class getActivityClass() {
        return ActivityClass;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainContext = (Activity) context;
    }
}

