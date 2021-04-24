package com.example.room_firestore_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.MyActivities.AthleteActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.ui.SubcollectionFragment.IndiMatchFragment;
import com.example.room_firestore_application.ui.SubcollectionFragment.TeamMatchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends Fragment {

    FragmentTransaction ft;
    Fragment teamMatchFrag = new TeamMatchFragment();
    Fragment indiMatchFrag = new IndiMatchFragment();

    ListView listView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    CollectionReference collectionReference, collectionReferenceInside;


    List<String> list ;
    ArrayList<String> myIds ;
    ArrayList<String> sportTypeAr ;
    ArrayList<String> sportCityAr ;
    ArrayList<String> sportCountryAr ;
    ArrayList<String> sportDateAr ;
    ArrayList<String> sportNameAr ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentFragment = this;

        View root = inflater.inflate(R.layout.fragment_match, container, false);
//        documentReference = db.collection("Matches")
//                .document("RUN1");

        listView = (ListView) root.findViewById(R.id.match_list);
        addOnClickListener();
        collectionReference = db.collection("Matches");
        createList();

         return root;
    }

    public void createList() {
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            list = new ArrayList<String>();
                            myIds = new ArrayList<String>();
                            sportTypeAr = new ArrayList<String>();
                            sportCityAr = new ArrayList<String>();
                            sportCountryAr = new ArrayList<String>();
                            sportDateAr = new ArrayList<String>();
                            sportNameAr = new ArrayList<String>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String city = document.getString("City");
                                String country = document.getString("Country");
                                String date = document.getString("Date");
                                String sport = document.getString("Sport");
                                String match_id = document.getString("ID");
                                
                                list.add("Match ID : " + match_id + "\n" + " City : " + city + "\n Country : " + country + "\n Date : " + date + "\n Sport : " + sport);
                                myIds.add(match_id);
                                sportCityAr.add(city);
                                sportCountryAr.add(country);
                                sportNameAr.add(sport);
                                sportDateAr.add(date);
                                if(MainActivity.localDatabase.basicDao().getSportType(sport).equals("Team")) {
                                    sportTypeAr.add("Team");
                                }
                                else{
                                    sportTypeAr.add("Individual");
                                }
                                if (getContext() != null) {
                                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
                                    listView.setAdapter(adapter);

                                }
                            }

                        } else {
                            Toast.makeText(getActivity(), "Document doesnt Exist", Toast.LENGTH_LONG).show();
                        }
                    }
                });


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


                if(sportType.equals("Team")) {
                    // String tag = String.valueOf(view.getTag());

                    bundle.putString("ID", myId);
                    // bundle.putString("sportype", sportType);

                    bundle.putString("City",sportCity);
                    bundle.putString("Country", sportCountry);
                    bundle.putString("Date", sportDate);
                    bundle.putString("Name", sportName);
                    teamMatchFrag.setArguments(bundle);

                    ft = getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, teamMatchFrag, null);
                    //ft.commit();
                    ft.detach(teamMatchFrag);
                    ft.attach(teamMatchFrag);
                    ft.commit();
                    ft.addToBackStack(null);

                }
                else{
                    //Toast.makeText(getActivity(),"Not a team match", Toast.LENGTH_LONG).show();

                    bundle.putString("ID", myId);

                    indiMatchFrag.setArguments(bundle);

                    ft = getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, indiMatchFrag, null);
                    //ft.commit();
                    ft.detach(indiMatchFrag);
                    ft.attach(indiMatchFrag);
                    ft.commit();
                    ft.addToBackStack(null);
                }
            }
        });
    }
}